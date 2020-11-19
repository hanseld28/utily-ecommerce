package br.com.utily.ecommerce.controller.domain.admin.sale;

import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class SaleAdminController {

    private final IDomainService<Sale> domainService;

    @Autowired
    public SaleAdminController(@Qualifier("domainService")
                                       IDomainService<Sale> domainService) {
        this.domainService = domainService;
    }



}
