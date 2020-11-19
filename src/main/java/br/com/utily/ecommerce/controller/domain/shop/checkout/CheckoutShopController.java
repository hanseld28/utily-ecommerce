package br.com.utily.ecommerce.controller.domain.shop.checkout;

import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.helper.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/shop/checkout")
public class CheckoutShopController {

    private final IDomainService<Sale> domainService;

    @Autowired
    public CheckoutShopController(@Qualifier("domainService")
                                       IDomainService<Sale> domainService) {
        this.domainService = domainService;
    }

    @GetMapping(path = "/step/one")
    public ModelAndView stepOne(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = ModelAndViewHelper.extractConfiguredFrom(httpServletRequest);

        return modelAndView;
    }

}
