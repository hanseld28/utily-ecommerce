package br.com.utily.ecommerce.controller.domain.shop.product;

import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.handler.exception.NotFoundException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping(path = ProductShopController.BASE_PRODUCT_URI)
public class ProductShopController {

    public static final String BASE_PRODUCT_URI = "/shop/products";

    private final IDomainService<Product> domainService;
    private final ShopCart shopCart;
    private final Product mockProduct;

    private final ModelAndViewHelper<Product> modelAndViewHelper;

    @Autowired
    public ProductShopController(@Qualifier("domainService") IDomainService<Product> domainService,
                                 ShopCart shopCart,
                                 Product mockProduct,
                                 ModelAndViewHelper<Product> modelAndViewHelper) {
        this.domainService = domainService;
        this.shopCart = shopCart;
        this.mockProduct = mockProduct;
        this.modelAndViewHelper = modelAndViewHelper;
    }

    @GetMapping
    public ModelAndView findAll() {
        ModelAndView modelAndView = ModelAndViewHelper
                .configure(
                        EViewType.PRODUCT_SHOP,
                        EView.LIST,
                        shopCart,
                        EModelAttribute.SHOP_CART);

        Collection<Product> products = this.domainService.findAll(mockProduct);
        modelAndViewHelper.addObjectTo(modelAndView, products, EModelAttribute.PRODUCTS);

        return modelAndView;
    }

    @GetMapping(path = "/{id}")
    public ModelAndView findById(@PathVariable("id") Long id) {
        ModelAndView modelAndView = ModelAndViewHelper
                .configure(
                        EViewType.PRODUCT_SHOP,
                        EView.DETAILS,
                        shopCart,
                        EModelAttribute.SHOP_CART);

        Optional<Product> productOptional = this.domainService.findById(id, mockProduct);
        Product product = productOptional.orElseThrow(NotFoundException::new);

        ModelAndViewHelper.addObjectTo(modelAndView, product, EModelAttribute.PRODUCT);

        return modelAndView;
    }
}
