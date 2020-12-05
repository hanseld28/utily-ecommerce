package br.com.utily.ecommerce.controller.application.auth;

import br.com.utily.ecommerce.dto.domain.user.customer.CustomerSignUpDTO;
import br.com.utily.ecommerce.entity.domain.user.EUserRole;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import br.com.utily.ecommerce.util.mapper.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(path = AuthController.AUTH_URL)
public class                                                                                                                                                                                AuthController {

    public static final String AUTH_URL = "/auth";
    public static final String LOGIN_URL = "/login";
    public static final String SIGN_UP_URL = "/sign-up";

    private final IDomainService<Customer> customerDomainService;

    private final ModelAndViewHelper modelAndViewHelper;
    private final Customer mockCustomer;
    private final CustomerSignUpDTO mockCustomerSignUpDTO;

    @Autowired
    public AuthController(@Qualifier("domainService") IDomainService<Customer> customerDomainService,
                          Customer mockCustomer,
                          CustomerSignUpDTO mockCustomerSignUpDTO,
                          ModelAndViewHelper modelAndViewHelper) {
        this.customerDomainService = customerDomainService;
        this.mockCustomer = mockCustomer;
        this.mockCustomerSignUpDTO = mockCustomerSignUpDTO;
        this.modelAndViewHelper = modelAndViewHelper;
    }

    @GetMapping(path = LOGIN_URL)
    public ModelAndView showLogin() {
        return ModelAndViewHelper.configure(
                EViewType.AUTH_APPLICATION,
                EView.LOGIN);
    }

    @GetMapping(path = SIGN_UP_URL)
    public ModelAndView showSignUp() {
        return ModelAndViewHelper.configure(
                EViewType.AUTH_APPLICATION,
                EView.SIGN_UP,
                mockCustomerSignUpDTO,
                EModelAttribute.CUSTOMER);
    }

    @PostMapping(path = SIGN_UP_URL)
    public ModelAndView doSignUp(@Validated
                                 @DTO(CustomerSignUpDTO.class) Customer customer,
                                 Errors errors,
                                 RedirectAttributes redirectAttributes) {

        if (errors.hasErrors()) {
            return ModelAndViewHelper.configure(EViewType.AUTH_APPLICATION, EView.SIGN_UP);
        }
        try {
            ModelAndView modelAndView = ModelAndViewHelper.configure(EViewType.REDIRECT_LOGIN_APPLICATION);

            User user = customer.getUser();
            user.setRole(EUserRole.CUSTOMER);
            customer.setUser(user);
            customerDomainService.save(customer);

            redirectAttributes.addFlashAttribute("message", "Conta criada com sucesso! Agora você pode fazer login.");

            return modelAndView;
        } catch (Exception exception) {
            return ModelAndViewHelper.configure(EViewType.AUTH_APPLICATION, EView.SIGN_UP);
        }
    }


}
