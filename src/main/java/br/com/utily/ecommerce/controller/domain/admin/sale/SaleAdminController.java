package br.com.utily.ecommerce.controller.domain.admin.sale;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.shop.sale.ESaleStatus;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = SaleAdminController.BASE_SALE_ADMIN_URL)
public class SaleAdminController {

    public static final String BASE_SALE_ADMIN_URL = "/admin/sales";
    public static final String UPDATE_STATUS_URL = "/{id}/update-status";

    private final IDomainService<Sale> saleDomainService;
    private final Sale mockSale;


    @Autowired
    public SaleAdminController(@Qualifier("domainService")
                               IDomainService<Sale> saleDomainService,
                               Sale mockSale) {
        this.saleDomainService = saleDomainService;
        this.mockSale = mockSale;
    }

    @GetMapping
    public ModelAndView findAll() {
        List<Sale> sales = saleDomainService.findAll(mockSale);

        return ModelAndViewHelper.configure(
                EViewType.SALE_ADMIN,
                EView.LIST,
                sales,
                EModelAttribute.SALES);
    }

    @PostMapping(path = UPDATE_STATUS_URL)
    public ModelAndView updateStatus(@PathVariable Long id,
                                     ESaleStatus newStatus,
                                     RedirectAttributes redirectAttributes) {
        Optional<Sale> foundSaleOptional = saleDomainService.findById(id, mockSale);
        Sale sale = foundSaleOptional.orElseThrow(NotFoundException::new);
        ESaleStatus oldStatus = sale.getStatus();

        sale.setStatus(newStatus);
        Sale updatedSale = saleDomainService.save(sale);

        boolean success = updatedSale.getStatus() == newStatus;

        String successMessage = "O status da venda foi alterado com sucesso de \""
                + oldStatus.getDisplayName() + "\" para \"" + newStatus.getDisplayName() + "\".";
        String errorMessage = "Ocorreu um erro ao tentar alterar o status da venda \""
                + oldStatus.getDisplayName() + "\" para \"" + newStatus.getDisplayName() + "\".";

        String message = success ? successMessage : errorMessage;
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("isError", !success);

        return ModelAndViewHelper.configure(EViewType.REDIRECT_SALES_ADMIN);
    }

}
