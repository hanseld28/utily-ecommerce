package br.com.utily.ecommerce.controller.domain.shop.cart;

import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping(path = ShopCartController.BASE_SHOP_CART_URI)
public class ShopCartController {

    public static final String BASE_SHOP_CART_URI = "/shop/cart/items";
    public static final String CART_ITEM_UPDATE_URI = "/{id}/update";
    public static final String CART_ITEM_REMOVE_URI = "/{id}/remove";

    private final ShopCart shopCart;
    private final Product mockProduct;

    private final IDomainService<Product> domainService;

    @Autowired
    public ShopCartController(ShopCart shopCart,
                              Product mockProduct,
                              @Qualifier("domainService")
                              IDomainService<Product> domainService) {
        this.shopCart = shopCart;
        this.mockProduct = mockProduct;
        this.domainService = domainService;
    }

    @GetMapping
    public ModelAndView view() {
        return ModelAndViewHelper.configure(
                EViewType.CART_SHOP,
                EView.DETAILS,
                shopCart,
                EModelAttribute.SHOP_CART);
    }

    @PostMapping
    public ModelAndView addItem(@RequestParam("productId") Long productId,
                                @RequestParam("amount") Integer amount) {
        ModelAndView modelAndView = ModelAndViewHelper
                .configure(EViewType.REDIRECT_PRODUCT_SHOP);

        Optional<Product> optionalProduct = domainService.findById(productId, mockProduct);
        Product foundProduct = optionalProduct.orElseThrow(NotFoundException::new);

        CartItem cartItem = CartItem.from(foundProduct, amount);
        shopCart.addItem(cartItem);
        System.out.println(modelAndView.getViewName());

        ModelAndViewHelper.addObjectTo(modelAndView, shopCart, EModelAttribute.SHOP_CART);

        return modelAndView;
    }

    @PostMapping(path = CART_ITEM_UPDATE_URI)
    public ModelAndView updateItemAmount(@PathVariable("id") Long id, Integer amount) {
        shopCart.updateItem(id, amount);

        return ModelAndViewHelper.configure(
                EViewType.CART_SHOP,
                EView.DETAILS,
                shopCart,
                EModelAttribute.SHOP_CART);
    }

    @GetMapping(path = CART_ITEM_REMOVE_URI)
    public ModelAndView removeItem(@PathVariable("id") Long id) {
        shopCart.removeItem(id);

        return ModelAndViewHelper.configure(
                EViewType.CART_SHOP,
                EView.DETAILS,
                shopCart,
                EModelAttribute.SHOP_CART);
    }
}


