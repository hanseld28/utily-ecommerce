package br.com.utily.ecommerce.controller.domain.shop.checkout;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.dto.domain.sale.creditCard.CreditCardIdAndValueDTO;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.CreditCardValue;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.AddressType;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.helper.checkout.CheckoutHelper;
import br.com.utily.ecommerce.helper.proxy.ProxyHelper;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
@RequestMapping(path = CheckoutShopController.BASE_CHECKOUT_URL)
public class CheckoutShopController {

    public static final String BASE_CHECKOUT_URL = "/shop/checkout";
    public static final String CHECKOUT_STEP_ONE_URL = "/step/one";
    public static final String CHECKOUT_STEP_TWO_URL = "/step/two";
    public static final String CHECKOUT_STEP_THREE_URL = "/step/three";
    public static final String CHECKOUT_STEP_ONE_VALIDATION_URL = "/step/one/validation";
    public static final String CHECKOUT_STEP_TWO_PAYMENT_CC_URL = "/step/two/payment/credit-cards";
    public static final String CHECKOUT_FINISH_URL = "/finish";

    private final IDomainService<Sale> saleDomainService;
    private final IDomainService<Address> addressDomainService;
    private final IDomainService<CreditCard> creditCardDomainService;

    private CreditCard mockCreditCard;

    private SaleInProgress saleInProgress;
    private ShopCart shopCart;
    private LoggedUserHelper loggedUserHelper;
    private CheckoutHelper checkoutHelper;
    private Customer loggedCustomer;

    private ModelAndViewHelper modelAndViewHelper;

    private final UUID hash;

    @Autowired
    public CheckoutShopController(@Qualifier("domainService")
                                  final IDomainService<Sale> saleDomainService,
                                  @Qualifier("domainService")
                                  final IDomainService<Address> addressDomainService,
                                  @Qualifier("domainService")
                                  final IDomainService<CreditCard> creditCardDomainService) {
        this.saleDomainService = saleDomainService;
        this.addressDomainService = addressDomainService;
        this.creditCardDomainService = creditCardDomainService;

        hash = UUID.randomUUID();
    }

    @Autowired
    private void setDependencyEntities(final ShopCart shopCart,
                                       final SaleInProgress saleInProgress,
                                       final CreditCard mockCreditCard) {
        this.shopCart = shopCart;
        this.saleInProgress = saleInProgress;
        this.mockCreditCard = mockCreditCard;
    }

    @Autowired
    private void setDependencyHelpers(final LoggedUserHelper loggedUserHelper,
                                      final CheckoutHelper checkoutHelper,
                                      final ModelAndViewHelper modelAndViewHelper) {
        this.loggedUserHelper = loggedUserHelper;
        this.checkoutHelper = checkoutHelper;
        this.modelAndViewHelper = modelAndViewHelper;
    }

    @GetMapping(path = CHECKOUT_STEP_ONE_URL)
    public ModelAndView initializeStepOne() {
        loggedCustomer = loggedUserHelper.getLoggedCustomerUser();
        saleInProgress.setCustomer(loggedCustomer);

        Optional<ShopCart> shopCartOptional = ProxyHelper.recoveryEntityFromProxy(shopCart);
        ShopCart shopCartFromProxy = shopCartOptional.orElseThrow(NotFoundException::new);
        saleInProgress.setCartItems(shopCartFromProxy.getItems());
        saleInProgress.setTotal(shopCart.getTotal());

        ModelAndView modelAndView = ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_ONE,
                loggedCustomer,
                EModelAttribute.CUSTOMER);

        modelAndView.addObject(
                EModelAttribute.NOT_ENOUGH_ADDRESS.getName(),
                false);

        return modelAndView;
    }

    @PostMapping(path = CHECKOUT_STEP_ONE_VALIDATION_URL)
    public ModelAndView handlerStepOne(Address address) {
        loggedCustomer = loggedUserHelper.getLoggedCustomerUser();

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

            if (!addressToAdd.isShippingAndBilling()) {
                AddressType missingAddressType = address.isShipping()
                        ? AddressType.BILLING
                        : AddressType.SHIPPING;

                List<Address> adressesWithMissingType = checkoutHelper
                        .filterAdressesByType(
                                loggedCustomer.getAdresses(),
                                missingAddressType
                        );

                Map<String, Object> mvObjects = new HashMap<>();
                mvObjects.put(EModelAttribute.NOT_ENOUGH_ADDRESS.getName(), true);
                mvObjects.put(EModelAttribute.ADDRESS_TYPE.getName(), missingAddressType);
                mvObjects.put(EModelAttribute.ADRESSES.getName(), adressesWithMissingType);

                modelAndView.addAllObjects(mvObjects);

                return modelAndView;
            }

            saleInProgress.addAddress(addressToAdd);

            modelAndView = ModelAndViewHelper.configure(EViewType.REDIRECT_CHECKOUT_STEP_TWO);
        }

        return modelAndView;
    }

    @GetMapping(path = CHECKOUT_STEP_TWO_URL)
    public ModelAndView loadStepTwo() {
        ModelAndView modelAndView = ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_TWO,
                saleInProgress,
                EModelAttribute.SALE);

        loggedCustomer = loggedUserHelper.getLoggedCustomerUser();
        saleInProgress.setCustomer(loggedCustomer);

        List<CreditCard> customerCreditCards = creditCardDomainService.findAllBy(loggedCustomer, mockCreditCard);

        ModelAndViewHelper.addObjectTo(modelAndView, customerCreditCards, EModelAttribute.CREDIT_CARDS);

        return modelAndView;
    }

    @PostMapping(path = CHECKOUT_STEP_TWO_PAYMENT_CC_URL)
    public ModelAndView addCreditCardValueForPaymentStepTwo(CreditCardIdAndValueDTO creditCardIdAndValueDTO) {
        CreditCardValue creditCardValue = ModelMapperHelper.fromDTOToEntity(creditCardIdAndValueDTO, CreditCardValue.class);

        Optional<CreditCard> creditCardOptional = creditCardDomainService
                .findById(
                        creditCardValue.getCreditCard().getId(),
                        mockCreditCard
                );

        creditCardValue.setCreditCard(
                creditCardOptional.orElseThrow(NotFoundException::new)
        );

        saleInProgress.addCreditCardValue(creditCardValue);

        return ModelAndViewHelper.configure(EViewType.REDIRECT_CHECKOUT_STEP_TWO);
    }

    @GetMapping(path = CHECKOUT_STEP_THREE_URL)
    public ModelAndView initializeStepThree() {
        ModelAndView modelAndView = ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_THREE,
                saleInProgress,
                EModelAttribute.SALE);

        ModelAndViewHelper.addObjectTo(modelAndView, hash.toString(), EModelAttribute.HASH);

        return modelAndView;
    }

    @PostMapping(path = CHECKOUT_FINISH_URL)
    public ModelAndView finish(@RequestParam String hash) {
        if (this.hash.equals(UUID.fromString(hash))) {
            saleInProgress.finish();

            Optional<SaleInProgress> saleInProgressOptional = ProxyHelper.recoveryEntityFromProxy(saleInProgress);
            Sale sale = checkoutHelper.adapt(saleInProgressOptional.orElseThrow(InternalError::new));

            Sale savedSale = saleDomainService.save(sale);

            return ModelAndViewHelper.configure(
                    EViewType.CHECKOUT_FINISH_SHOP,
                    EView.CHECKOUT_FINISH,
                    savedSale,
                    EModelAttribute.SALE);
        }
        
        return ModelAndViewHelper.configure(EViewType.REDIRECT_CHECKOUT_STEP_THREE);
    }
}
