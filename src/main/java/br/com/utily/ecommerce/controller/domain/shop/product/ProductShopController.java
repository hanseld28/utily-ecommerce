package br.com.utily.ecommerce.controller.domain.shop.product;

import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.handler.exception.NotFoundException;
import br.com.utily.ecommerce.helper.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path = "/shop/product")
public class ProductShopController {

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

    @GetMapping(path = "/list")
    public ModelAndView listAll(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = modelAndViewHelper.extractConfiguredFrom(httpServletRequest, shopCart, "shopCart");

        Collection<Product> products = this.domainService.findAll(mockProduct);
        modelAndViewHelper.addObjectTo(modelAndView, products, "products");

        return modelAndView;
    }

    @GetMapping(path = "/details")
    public ModelAndView listOne(HttpServletRequest httpServletRequest, @PathParam("id") Long id) {
        ModelAndView modelAndView = modelAndViewHelper.extractConfiguredFrom(httpServletRequest, shopCart, "shopCart");

        Optional<Product> productOptional = this.domainService.findById(id, mockProduct);
        Product product = productOptional.orElseThrow(NotFoundException::new);

        modelAndViewHelper.addObjectTo(modelAndView, product, "product");

        return modelAndView;
    }
}
