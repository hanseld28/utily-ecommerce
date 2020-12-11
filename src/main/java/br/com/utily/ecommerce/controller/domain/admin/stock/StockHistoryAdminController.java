package br.com.utily.ecommerce.controller.domain.admin.stock;

import br.com.utily.ecommerce.entity.domain.stock.StockHistory;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.alternative.IAlternativeDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static br.com.utily.ecommerce.controller.domain.admin.stock.StockHistoryAdminController.BASE_STOCK_HISTORY_ADMIN_URL;

@Controller
@RequestMapping(path = BASE_STOCK_HISTORY_ADMIN_URL)
public class StockHistoryAdminController {

    public static final String BASE_STOCK_HISTORY_ADMIN_URL = "/admin/stock/history";

    private final IAlternativeDomainService<StockHistory> stockHistoryAlternativeDomainService;

    private final StockHistory stockHistoryMock;

    public StockHistoryAdminController(@Qualifier("alternativeDomainService")
                                       IAlternativeDomainService<StockHistory> stockHistoryAlternativeDomainService,
                                       StockHistory stockHistoryMock) {
        this.stockHistoryAlternativeDomainService = stockHistoryAlternativeDomainService;
        this.stockHistoryMock = stockHistoryMock;
    }

    @GetMapping
    public ModelAndView show() {
        List<StockHistory> foundStockOperations = stockHistoryAlternativeDomainService
                .findAll(stockHistoryMock);

        return ModelAndViewHelper.configure(
                EViewType.STOCK_HISTORY_ADMIN,
                EView.LIST,
                foundStockOperations,
                EModelAttribute.STOCK_HISTORY);
    }

}
