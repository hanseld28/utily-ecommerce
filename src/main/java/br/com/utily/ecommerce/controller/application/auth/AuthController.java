package br.com.utily.ecommerce.controller.application.auth;

import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = AuthController.AUTH_URL)
public class AuthController {

    public static final String AUTH_URL = "/auth";
    public static final String LOGIN_URL = "/login";
    public static final String SIGN_UP_URL = "/sign-up";

    public final ModelAndViewHelper<Customer> modelAndViewHelper;
    public final Customer mockCustomer;

    @Autowired
    public AuthController(Customer mockCustomer, ModelAndViewHelper<Customer> modelAndViewHelper) {
        this.mockCustomer = mockCustomer;
        this.modelAndViewHelper = modelAndViewHelper;
    }

    @GetMapping(path = LOGIN_URL)
    public ModelAndView login() {
        return ModelAndViewHelper.configure(
                EViewType.AUTH_APPLICATION,
                EView.LOGIN);
    }

    @GetMapping(path = SIGN_UP_URL)
    public ModelAndView signUp() {
        return ModelAndViewHelper.configure(
                EViewType.AUTH_APPLICATION,
                EView.SIGN_UP,
                mockCustomer,
                EModelAttribute.CUSTOMER);
    }
}
