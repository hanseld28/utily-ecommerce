package br.com.utily.ecommerce.controller.domain.shop.customer;

import br.com.utily.ecommerce.dto.domain.user.UserUpdateDTO;
import br.com.utily.ecommerce.dto.domain.user.customer.CustomerUpdateDTO;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.user.EUserRole;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.helper.security.LoggedUserHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.helper.view.ModelMapperHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
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
@RequestMapping(path = CustomerAccountShopController.BASE_CUSTOMER_ACCOUNT_SHOP_URI)
public class CustomerAccountShopController {

    public static final String BASE_CUSTOMER_ACCOUNT_SHOP_URI = "/customer/account";

    private final IDomainService<Customer> domainService;
    private final ShopCart shopCart;
    private final Customer mockCustomer;
    private final CustomerUpdateDTO mockCustomerUpdateDTO;

    private final ModelAndViewHelper<Customer> modelAndViewHelper;

    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public CustomerAccountShopController(@Qualifier("domainService") IDomainService<Customer> domainService,
                                         ShopCart shopCart,
                                         Customer mockCustomer,
                                         CustomerUpdateDTO mockCustomerUpdateDTO,
                                         ModelAndViewHelper<Customer> modelAndViewHelper,
                                         LoggedUserHelper loggedUserHelper) {
        this.domainService = domainService;
        this.shopCart = shopCart;
        this.mockCustomer = mockCustomer;
        this.mockCustomerUpdateDTO = mockCustomerUpdateDTO;
        this.modelAndViewHelper = modelAndViewHelper;
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping
    public ModelAndView showAccount() {
        ModelAndView modelAndView = ModelAndViewHelper
                .configure(
                        EViewType.CUSTOMER_ACCOUNT_SHOP,
                        EView.DETAILS,
                        shopCart,
                        EModelAttribute.SHOP_CART);

        Customer customer = loggedUserHelper.getLoggedCustomerUser();
        CustomerUpdateDTO customerUpdateDTO = ModelMapperHelper.fromEntityToDTO(customer, CustomerUpdateDTO.class);
        ModelAndViewHelper.addObjectTo(modelAndView, customerUpdateDTO, EModelAttribute.CUSTOMER);

        return modelAndView;
    }

    @PostMapping
    public ModelAndView saveAccount(@Validated CustomerUpdateDTO customerUpdateDTO,
                                    Errors errors,
                                    RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return ModelAndViewHelper.configure(EViewType.CUSTOMER_ACCOUNT_SHOP,
                            EView.DETAILS,
                            shopCart,
                            EModelAttribute.SHOP_CART);
        }
        try {
            User loggedUserUpdated = LoggedUserHelper.getLoggedUser();
            UserUpdateDTO loggedUserDTOUpdated = ModelMapperHelper.fromEntityToDTO(loggedUserUpdated, UserUpdateDTO.class);
            UserUpdateDTO updatedDTOUser = customerUpdateDTO.getUser();

            if (updatedDTOUser.getPassword() != null && !updatedDTOUser.getPassword().isEmpty()) {
                loggedUserDTOUpdated.setUsername(updatedDTOUser.getUsername());
                loggedUserDTOUpdated.setPassword(updatedDTOUser.getPassword());
            }

            // TODO: PLEASE, find error "Failed to convert value of type 'br.com.utily.ecommerce.dto.domain.user.customer.CustomerUpdateDTO' to required type 'java.lang.String'"

            customerUpdateDTO.setUser(loggedUserDTOUpdated);
            Customer customer = ModelMapperHelper.fromDTOToEntity(customerUpdateDTO, Customer.class);
            User user = customer.getUser();
            user.setRole(EUserRole.CUSTOMER);
            customer.setUser(user);
            Customer savedCustomer = domainService.save(customer);

            CustomerUpdateDTO savedCustomerUpdateDTO = ModelMapperHelper.fromEntityToDTO(savedCustomer, CustomerUpdateDTO.class);

            ModelAndView modelAndView = ModelAndViewHelper.configure(EViewType.REDIRECT_CUSTOMER_ACCOUNT_SHOP);
            ModelAndViewHelper.addObjectTo(modelAndView, savedCustomerUpdateDTO, EModelAttribute.CUSTOMER);
            redirectAttributes.addFlashAttribute("message", "Seus dados foram atualizados.");

            return modelAndView;

        } catch (Exception exception) {

            System.out.println(exception.getMessage());
            exception.printStackTrace();

            return ModelAndViewHelper.configure(EViewType.CUSTOMER_ACCOUNT_SHOP,
                            EView.DETAILS,
                            shopCart,
                            EModelAttribute.SHOP_CART);
        }
    }
}
