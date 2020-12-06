package br.com.utily.ecommerce.controller.domain.admin.stock;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.dto.domain.admin.stock.StockDTO;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.stock.Stock;
import br.com.utily.ecommerce.helper.stock.StockHistoryHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.helper.view.ModelMapperHelper;
import br.com.utily.ecommerce.helper.view.ViewMessageHelper;
import br.com.utily.ecommerce.service.alternative.IAlternativeDomainService;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static br.com.utily.ecommerce.controller.domain.admin.stock.StockAdminController.BASE_STOCKS_ADMIN_URL;

@Controller
@RequestMapping(path = BASE_STOCKS_ADMIN_URL)
public class StockAdminController {

    public static final String BASE_STOCKS_ADMIN_URL = "/admin/stocks";
    private static final String STOCK_ID_PATH_URL = "/{id}";
    private static final String NEW_STOCK_URL = "/new";

    private final IAlternativeDomainService<Stock> stockDomainService;
    private final IDomainService<Product> productDomainService;

    private Stock stockMock;
    private Product productMock;

    private StockDTO stockDTOmock;

    private StockHistoryHelper stockHistoryHelper;

    @Autowired
    public StockAdminController(@Qualifier("alternativeDomainService")
                                IAlternativeDomainService<Stock> stockDomainService,
                                @Qualifier("domainService")
                                IDomainService<Product> productDomainService) {
        this.stockDomainService = stockDomainService;
        this.productDomainService = productDomainService;
    }

    @Autowired
    private void setDependencyEntities(Stock stockMock,
                                       Product productMock) {
        this.stockMock = stockMock;
        this.productMock = productMock;
    }

    @Autowired
    private void setDependencyDTOs(StockDTO stockDTOmock) {
        this.stockDTOmock = stockDTOmock;
    }

    @Autowired
    private void setDependencyHelpers(StockHistoryHelper stockHistoryHelper) {
        this.stockHistoryHelper = stockHistoryHelper;
    }

    @GetMapping
    public ModelAndView findAll() {
        List<Stock> foundStocks = stockDomainService.findAll(stockMock);

        return ModelAndViewHelper.configure(
                EViewType.STOCK_ADMIN,
                EView.LIST,
                foundStocks,
                EModelAttribute.STOCKS);
    }

    @GetMapping(path = STOCK_ID_PATH_URL)
    public ModelAndView findById(@PathVariable Long id) {
        Optional<Stock> stockOptional = stockDomainService.findById(id, stockMock);
        Stock foundStock = stockOptional.orElseThrow(NotFoundException::new);

        return ModelAndViewHelper.configure(
                EViewType.STOCK_ADMIN,
                EView.NEW,
                foundStock,
                EModelAttribute.STOCK);
    }

    @GetMapping(path = NEW_STOCK_URL)
    public ModelAndView newStock() {
        return ModelAndViewHelper.configure(
                EViewType.STOCK_ADMIN,
                EView.NEW);
    }

    @PostMapping
    public ModelAndView save(@Valid StockDTO stockDTO,
                             Errors errors,
                             RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            ViewMessageHelper.configureRedirectMessageWith(errors,false, redirectAttributes);

            return ModelAndViewHelper.configure(EViewType.REDIRECT_NEW_STOCK_ADMIN);
        }

        Stock stock = ModelMapperHelper.fromDTOToEntity(stockDTO, Stock.class);

        Product foundProduct = productDomainService.findById(stock.getProduct().getId(), productMock)
                .orElseThrow(NotFoundException::new);

        stock.setProduct(foundProduct);

        Stock createdStock = stockDomainService.save(stock);

        if (createdStock.getId() != null) {
            stockHistoryHelper.registerStockOperationOnHistory(stock);

            String message = "Estoque com a quantidade inicial de " + createdStock.getAmount()
                    + " unidades criado com sucesso para o produto \""
                    + createdStock.getProduct().getTitle()
                    + "\".";

            ViewMessageHelper.configureRedirectMessageWith(message, true, redirectAttributes);
        }

        return ModelAndViewHelper.configure(EViewType.REDIRECT_STOCKS_ADMIN);
    }

    @ModelAttribute("stock")
    public StockDTO stockDTO() {
        return stockDTOmock;
    }

    @ModelAttribute("products")
    public List<Product> products() {
        return productDomainService.findAllActivatedBy(productMock)
                .stream()
                .filter(Product::hasNotStock)
                .collect(Collectors.toList());
    }
}