package br.com.utily.ecommerce.controller.domain.admin.product;

import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(path = ProductAdminController.BASE_PRODUCT_ADMIN_URL)
public class ProductAdminController {

    public static final String BASE_PRODUCT_ADMIN_URL = "/admin/products";

    private final IDomainService<Product> productDomainService;

    private final Product mockProduct;

    @Autowired
    public ProductAdminController(@Qualifier("domainService")
                                  IDomainService<Product> productDomainService,
                                  Product mockProduct) {
        this.productDomainService = productDomainService;
        this.mockProduct = mockProduct;
    }

    @GetMapping
    public ModelAndView findAll() {
        List<Product> products = productDomainService.findAll(mockProduct);

        return ModelAndViewHelper.configure(
                EViewType.PRODUCT_ADMIN,
                EView.LIST,
                products,
                EModelAttribute.PRODUCTS);
    }
}
