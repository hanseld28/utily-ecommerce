package br.com.utily.ecommerce.controller.domain.shop.customer;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.dto.domain.user.UserUpdateDTO;
import br.com.utily.ecommerce.dto.domain.user.customer.CustomerUpdateDTO;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    public static final String ACCOUNT_URI = "/account";
    public static final String ORDERS_URI = "/orders";
    public static final String TRADES_URI = "/trades";
    public static final String ADRESSES_URI = "/adresses";
    public static final String CREDIT_CARDS_URI = "/credit-cards";
    public static final String CHANGE_PASSWORD_URI = "/change-password";

    private final IDomainService<Customer> customerDomainService;
    private final IDomainService<Sale> saleDomainService;

    private final ShopCart shopCart;
    private final Customer mockCustomer;
    private final Sale mockSale;
    private final CustomerUpdateDTO mockCustomerUpdateDTO;

    private final ModelAndViewHelper modelAndViewHelper;

    private final LoggedUserHelper loggedUserHelper;

    @Autowired
    public CustomerController(@Qualifier("domainService")
                                          IDomainService<Customer> customerDomainService,
                              @Qualifier("domainService")
                                          IDomainService<Sale> saleDomainService,
                              ShopCart shopCart,
                              Customer mockCustomer,
                              Sale mockSale, CustomerUpdateDTO mockCustomerUpdateDTO,
                              ModelAndViewHelper modelAndViewHelper,
                              LoggedUserHelper loggedUserHelper) {
        this.customerDomainService = customerDomainService;
        this.saleDomainService = saleDomainService;
        this.shopCart = shopCart;
        this.mockCustomer = mockCustomer;
        this.mockSale = mockSale;
        this.mockCustomerUpdateDTO = mockCustomerUpdateDTO;
        this.modelAndViewHelper = modelAndViewHelper;
        this.loggedUserHelper = loggedUserHelper;
    }

    @GetMapping(path = ACCOUNT_URI)
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

    @PostMapping(path = ACCOUNT_URI)
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
            Customer savedCustomer = customerDomainService.save(customer);

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

    @GetMapping(path = ORDERS_URI)
    public ModelAndView showOrders() {
        ModelAndView modelAndView = ModelAndViewHelper
                .configure(
                        EViewType.CUSTOMER_ORDER_SHOP,
                        EView.LIST,
                        shopCart,
                        EModelAttribute.SHOP_CART);

        Customer loggedCustomerUser = loggedUserHelper.getLoggedCustomerUser();

        List<Sale> customerOrders = saleDomainService.findAllBy(loggedCustomerUser, mockSale);

        Boolean thereAreOrders = !customerOrders.isEmpty();

        ModelAndViewHelper.addObjectTo(modelAndView, customerOrders, EModelAttribute.ORDERS);
        ModelAndViewHelper.addObjectTo(modelAndView, thereAreOrders, EModelAttribute.THERE_ARE_ORDERS);

        return modelAndView;
    }

    @GetMapping(path = ORDERS_URI + "/{id}")
    public ModelAndView viewOrderDetails(@PathVariable Long id) {
        ModelAndView modelAndView = ModelAndViewHelper
                .configure(
                        EViewType.CUSTOMER_ORDER_SHOP,
                        EView.DETAILS,
                        shopCart,
                        EModelAttribute.SHOP_CART);

        Optional<Sale> orderOptional = saleDomainService.findById(id, mockSale);

        Sale foundOrder = orderOptional.orElseThrow(NotFoundException::new);

        ModelAndViewHelper.addObjectTo(modelAndView, foundOrder, EModelAttribute.ORDER);

        return modelAndView;
    }
}
