package br.com.utily.ecommerce.controller.domain.shop.checkout;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.helper.entity.CheckoutHelper;
import br.com.utily.ecommerce.helper.security.LoggedUserHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.utily.ecommerce.helper.proxy.ProxyHelper;

import java.util.Optional;


@Controller
@RequestMapping(path = CheckoutShopController.BASE_CHECKOUT_URL)
public class CheckoutShopController {

    public static final String BASE_CHECKOUT_URL = "/shop/checkout";
    public static final String CHECKOUT_STEP_ONE_URL = "/step/one";
    public static final String CHECKOUT_STEP_ONE_VALIDATION_URL = "/step/one/validation";
    public static final String CHECKOUT_STEP_TWO_URL = "/step/two";
    public static final String CHECKOUT_STEP_THREE_URL = "/step/three";

    private final IDomainService<Sale> saleDomainService;
    private final IDomainService<Address> addressDomainService;

    private final SaleInProgress saleInProgress;
    private final ShopCart shopCart;
    private final LoggedUserHelper loggedUserHelper;
    private final CheckoutHelper checkoutHelper;
    private Customer loggedCustomer;

    private final ModelAndViewHelper<Customer> modelAndViewHelper;

    @Autowired
    public CheckoutShopController(@Qualifier("domainService")
                                  IDomainService<Sale> saleDomainService,
                                  @Qualifier("domainService")
                                  IDomainService<Address> addressDomainService,
                                  SaleInProgress saleInProgress,
                                  ShopCart shopCart,
                                  LoggedUserHelper loggedUserHelper,
                                  CheckoutHelper checkoutHelper,
                                  ModelAndViewHelper<Customer> modelAndViewHelper) {
        this.saleDomainService = saleDomainService;
        this.addressDomainService = addressDomainService;
        this.saleInProgress = saleInProgress;
        this.shopCart = shopCart;
        this.loggedUserHelper = loggedUserHelper;
        this.checkoutHelper = checkoutHelper;
        this.modelAndViewHelper = modelAndViewHelper;
    }

    @GetMapping(path = CHECKOUT_STEP_ONE_URL)
    public ModelAndView initializeStepOne() {
        loggedCustomer = loggedUserHelper.getLoggedCustomerUser();

        saleInProgress.setCustomer(loggedCustomer);

        return ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_ONE,
                loggedCustomer,
                EModelAttribute.CUSTOMER);
    }

    @PostMapping(path = CHECKOUT_STEP_ONE_VALIDATION_URL)
    public ModelAndView validateStepOne(Address address) {
        ModelAndView modelAndView = ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_ONE,
                loggedCustomer,
                EModelAttribute.CUSTOMER);

        Long addressId = address.getId();
        boolean isExistingAddress = addressId != null;

        if (isExistingAddress) {
            Optional<Address> foundAddressOptional = addressDomainService.findById(addressId, address);
            Address addressToAdd = foundAddressOptional.orElseThrow(NotFoundException::new);

            if (checkoutHelper.isItNeedsOneMoreAddress(address)) {
                modelAndView.addObject(
                        EModelAttribute.NOT_ENOUGH_ADDRESS.getName(),
                        true);
                return modelAndView;
            }

            saleInProgress.addAddress(addressToAdd);
            saleInProgress.finish();

            Optional<SaleInProgress> saleInProgressOptional = ProxyHelper.recoveryEntityFromProxy(saleInProgress);

            Sale sale = checkoutHelper.adapt(
                    saleInProgressOptional.orElseThrow(InternalError::new)
            );

            Sale savedSale = saleDomainService.save(sale);

            System.out.println(
                    "Sale ID = " +
                    savedSale.getId() +
                    " and first Address Place = " +
                            savedSale.getAdresses()
                                .get(0)
                                .getAddress()
                                .getPublicPlace()
            );
        }

        return modelAndView;
    }

    @GetMapping(path = CHECKOUT_STEP_TWO_URL)
    public ModelAndView handlerStepTwo(Address address) {

        return ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_TWO);
    }

    @GetMapping(path = CHECKOUT_STEP_THREE_URL)
    public ModelAndView initializeStepThree() {
        return ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_THREE);
    }


}
