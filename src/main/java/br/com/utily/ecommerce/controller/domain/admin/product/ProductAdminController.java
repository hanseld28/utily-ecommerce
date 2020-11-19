package br.com.utily.ecommerce.controller.domain.admin.product;

import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class ProductAdminController {

    private final IDomainService<Product> domainService;

    @Autowired
    public ProductAdminController(@Qualifier("domainService")
                                          IDomainService<Product> domainService) {
        this.domainService = domainService;
    }
}
