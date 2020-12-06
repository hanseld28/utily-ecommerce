package br.com.utily.ecommerce.controller.domain.shop.product;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = ProductShopController.BASE_PRODUCT_URI)
public class ProductShopController {

    public static final String BASE_PRODUCT_URI = "/shop/products";

    public static final String SHOP_CART_MODEL_ATTRIBUTE = "shopCart";
    public static final String PRODUCTS_MODEL_ATTRIBUTE = "products";

    private final IDomainService<Product> domainService;
    private final ShopCart shopCart;
    private final Product productMock;

    private final ModelAndViewHelper modelAndViewHelper;

    @Autowired
    public ProductShopController(@Qualifier("domainService") IDomainService<Product> domainService,
                                 ShopCart shopCart,
                                 Product productMock,
                                 ModelAndViewHelper modelAndViewHelper) {
        this.domainService = domainService;
        this.shopCart = shopCart;
        this.productMock = productMock;
        this.modelAndViewHelper = modelAndViewHelper;
    }

    @GetMapping
    public ModelAndView findAll() {
        return ModelAndViewHelper.configure(
                        EViewType.PRODUCT_SHOP,
                        EView.LIST);
    }

    @GetMapping(path = "/{id}")
    public ModelAndView findById(@PathVariable("id") Long id) {
        Optional<Product> productOptional = this.domainService.findById(id, productMock);
        Product product = productOptional.orElseThrow(NotFoundException::new);

        return ModelAndViewHelper.configure(
                        EViewType.PRODUCT_SHOP,
                        EView.DETAILS,
                        product,
                        EModelAttribute.PRODUCT);
    }

    @ModelAttribute(PRODUCTS_MODEL_ATTRIBUTE)
    public List<Product> products() {
        return domainService.findAllActivatedBy(productMock)
                .stream()
                .filter(Product::hasStock)
                .collect(Collectors.toList());
    }

    @ModelAttribute(SHOP_CART_MODEL_ATTRIBUTE)
    public ShopCart shopCart() {
        return shopCart;
    }
}
