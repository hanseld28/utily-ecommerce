package br.com.utily.ecommerce.controller.domain.shop.checkout;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.dto.domain.shop.sale.creditCard.CreditCardIdAndValueDTO;
import br.com.utily.ecommerce.dto.domain.shop.voucher.VoucherIdDTO;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.shop.freight.Freight;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.CreditCardValue;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.AddressType;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import br.com.utily.ecommerce.helper.checkout.CheckoutHelper;
import br.com.utily.ecommerce.helper.checkout.CustomerVoucherHelper;
import br.com.utily.ecommerce.helper.freight.FreightHelper;
import br.com.utily.ecommerce.helper.proxy.ProxyHelper;
import br.com.utily.ecommerce.helper.security.LoggedUserHelper;
import br.com.utily.ecommerce.helper.session.SessionHelper;
import br.com.utily.ecommerce.helper.stock.StockHelper;
import br.com.utily.ecommerce.helper.view.ModelAndViewHelper;
import br.com.utily.ecommerce.helper.view.ModelMapperHelper;
import br.com.utily.ecommerce.service.associative.IAssociativeDomainService;
import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.util.constant.attribute.EModelAttribute;
import br.com.utily.ecommerce.util.constant.entity.EViewType;
import br.com.utily.ecommerce.util.constant.view.EView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = CheckoutShopController.BASE_CHECKOUT_URL)
public class CheckoutShopController {

    public static final String BASE_CHECKOUT_URL = "/shop/checkout";
    public static final String CHECKOUT_STEP_ONE_URL = "/step/one";
    public static final String CHECKOUT_STEP_TWO_URL = "/step/two";
    public static final String CHECKOUT_STEP_THREE_URL = "/step/three";
    public static final String CHECKOUT_STEP_ONE_VALIDATION_URL = "/step/one/validation";
    public static final String CHECKOUT_STEP_TWO_PAYMENT_CC_URL = "/step/two/payment/credit-cards";
    public static final String CHECKOUT_STEP_TWO_PAYMENT_VOUCHER_URL = "/step/two/payment/voucher";
    public static final String CHECKOUT_FINISH_URL = "/finish";

    private final IDomainService<Sale> saleDomainService;
    private final IDomainService<Address> addressDomainService;
    private final IDomainService<CreditCard> creditCardDomainService;
    private final IDomainService<Voucher> voucherDomainService;
    private final IAssociativeDomainService<CustomerVoucher> customerVoucherAlternativeDomainService;

    private CreditCard mockCreditCard;
    private CustomerVoucher mockCustomerVoucher;
    private Voucher mockVoucher;

    private SaleInProgress saleInProgress;
    private ShopCart shopCart;
    private Customer loggedCustomer;

    private CreditCardIdAndValueDTO mockCreditCardAndValueDTO;
    private VoucherIdDTO mockVoucherIdDTO;

    private LoggedUserHelper loggedUserHelper;
    private CheckoutHelper checkoutHelper;
    private CustomerVoucherHelper customerVoucherHelper;
    private StockHelper stockHelper;
    private FreightHelper freightHelper;

    private final UUID hash;

    @Autowired
    public CheckoutShopController(@Qualifier("domainService") final IDomainService<Sale> saleDomainService,
                                  @Qualifier("domainService") final IDomainService<Address> addressDomainService,
                                  @Qualifier("domainService") final IDomainService<CreditCard> creditCardDomainService,
                                  @Qualifier("domainService") final IDomainService<Voucher> voucherDomainService,
                                  @Qualifier("associativeDomainService") final IAssociativeDomainService<CustomerVoucher> customerVoucherAlternativeDomainService) {
        this.saleDomainService = saleDomainService;
        this.addressDomainService = addressDomainService;
        this.creditCardDomainService = creditCardDomainService;
        this.voucherDomainService = voucherDomainService;
        this.customerVoucherAlternativeDomainService = customerVoucherAlternativeDomainService;

        hash = UUID.randomUUID();
    }

    @Autowired
    private void setDependencyEntities(final ShopCart shopCart,
                                       final SaleInProgress saleInProgress,
                                       final CreditCard mockCreditCard,
                                       final CustomerVoucher mockCustomerVoucher,
                                       final Voucher mockVoucher) {
        this.shopCart = shopCart;
        this.saleInProgress = saleInProgress;
        this.mockCreditCard = mockCreditCard;
        this.mockCustomerVoucher = mockCustomerVoucher;
        this.mockVoucher = mockVoucher;
    }

    @Autowired
    private void setDependencyDTOs(CreditCardIdAndValueDTO mockCreditCardAndValueDTO,
                                   VoucherIdDTO mockVoucherIdDTO) {
        this.mockCreditCardAndValueDTO = mockCreditCardAndValueDTO;
        this.mockVoucherIdDTO = mockVoucherIdDTO;
    }

    @Autowired
    private void setDependencyHelpers(final LoggedUserHelper loggedUserHelper,
                                      final CheckoutHelper checkoutHelper,
                                      final CustomerVoucherHelper customerVoucherHelper,
                                      final StockHelper stockHelper,
                                      final FreightHelper freightHelper) {
        this.loggedUserHelper = loggedUserHelper;
        this.checkoutHelper = checkoutHelper;
        this.customerVoucherHelper = customerVoucherHelper;
        this.stockHelper = stockHelper;
        this.freightHelper = freightHelper;
    }

    @GetMapping(path = CHECKOUT_STEP_ONE_URL)
    public ModelAndView initializeStepOne(HttpSession httpSession) {
        SessionHelper.initialize(httpSession);

        loggedCustomer = loggedUserHelper.getLoggedCustomerUser();
        saleInProgress.setCustomer(loggedCustomer);

        Optional<ShopCart> shopCartOptional = ProxyHelper.recoveryEntityFromProxy(shopCart);
        ShopCart shopCartFromProxy = shopCartOptional.orElseThrow(NotFoundException::new);
        saleInProgress.setCartItems(shopCartFromProxy.getItems());
        saleInProgress.setSubtotal(shopCart.getTotal());

        ModelAndView modelAndView = ModelAndViewHelper.configure(
                EViewType.CHECKOUT_STEP_SHOP,
                EView.CHECKOUT_STEP_ONE,
                loggedCustomer,
                EModelAttribute.CUSTOMER);

        modelAndView.addObject(
                EModelAttribute.NOT_ENOUGH_ADDRESS.getName(),
                false);
        modelAndView.addObject(
                EModelAttribute.ENABLE_NEXT_STEP.getName(),
                false
        );

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

            Freight freight = freightHelper.build();
            saleInProgress.setFreight(freight);
            saleInProgress.calculateFreight();

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
        List<Voucher> customerVouchers = customerVoucherAlternativeDomainService
                .findAllBy(loggedCustomer, mockCustomerVoucher)
                .stream()
                .map(CustomerVoucher::getVoucher)
                .collect(Collectors.toList());

        Boolean enableNextStep = saleInProgress.isRemainingAmountFullyCovered();

        mockCreditCardAndValueDTO.setValue(saleInProgress.calculateRemainingAmount());

        Map<String, Object> modelMap = new HashMap<>();
        modelMap.put(EModelAttribute.CREDIT_CARDS.getName(), customerCreditCards);
        modelMap.put(EModelAttribute.VOUCHERS.getName(), customerVouchers);
        modelMap.put(EModelAttribute.CREDIT_CARD_AND_VALUE.getName(), mockCreditCardAndValueDTO);
        modelMap.put(EModelAttribute.VOUCHER.getName(), mockVoucherIdDTO);
        modelMap.put(EModelAttribute.ENABLE_NEXT_STEP.getName(), enableNextStep);
        modelMap.put(EModelAttribute.CREDIT_CARDS.getName(), customerCreditCards);
        ModelAndViewHelper.addModelMapTo(modelAndView, modelMap);

        return modelAndView;
    }

    @PostMapping(path = CHECKOUT_STEP_TWO_PAYMENT_CC_URL)
    public ModelAndView addCreditCardValueForPaymentStepTwo(@Valid CreditCardIdAndValueDTO creditCardIdAndValueDTO,
                                                            Errors errors,
                                                            RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = ModelAndViewHelper.configure(EViewType.REDIRECT_CHECKOUT_STEP_TWO);

        if (errors.hasFieldErrors("value")) {
            FieldError valueFieldError = errors.getFieldError("value");
            String message = valueFieldError != null ? valueFieldError.getDefaultMessage() : "Valor inválido";

            redirectAttributes.addFlashAttribute(EModelAttribute.IS_SUCCESS_MESSAGE.getName(), false);
            redirectAttributes.addFlashAttribute(EModelAttribute.MESSAGE.getName(), message);

            return modelAndView;
        }

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

        redirectAttributes.addFlashAttribute(EModelAttribute.IS_SUCCESS_MESSAGE.getName(), true);
        redirectAttributes.addFlashAttribute(EModelAttribute.MESSAGE.getName(), "Forma de pagamento e valor adicionado.");

        return modelAndView;
    }

    @PostMapping(path = CHECKOUT_STEP_TWO_PAYMENT_VOUCHER_URL)
    public ModelAndView addVoucherForPaymentStepTwo(@Valid VoucherIdDTO voucherIdDTO) {
        if (!saleInProgress.getVoucherAlreadyApplied()) {
            Long voucherId = voucherIdDTO.getId();
            Optional<Voucher> voucherOptional = voucherDomainService.findById(voucherId, mockVoucher);
            Voucher voucher = voucherOptional.orElseThrow(NotFoundException::new);

            saleInProgress.applyVoucher(voucher);
        }
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
            Sale sale = checkoutHelper.adapt(
                    saleInProgressOptional.orElseThrow(InternalError::new)
            );
            Sale savedSale = saleDomainService.save(sale);

            if (savedSale.getVoucher() != null) {
                // TODO: inspect the following block and this processing
                //       - voucher is not marked with "used", but it is
                //       - returning to customer as valid.
                CustomerVoucher savedCustomerVoucher = customerVoucherHelper.adapt(
                        savedSale.getVoucher(),
                        savedSale.getCustomer()
                );
                customerVoucherAlternativeDomainService.save(savedCustomerVoucher);
            }
            Map<Long, Integer> productsAndOperationAmounts = new HashMap<>();

            for (SaleItem saleItem : savedSale.getItems()) {
                Long productStockId = saleItem.getProduct().getStock().getId();
                Integer itemOperationAmount = saleItem.getQuantity();

                productsAndOperationAmounts.put(productStockId, itemOperationAmount);
            }

            stockHelper.down(productsAndOperationAmounts);

            if (savedSale.getId() != null) {
                SessionHelper.removeAttributesForSaleFinished(
                        shopCart,
                        saleInProgress
                );

                return ModelAndViewHelper.configure(
                        EViewType.CHECKOUT_FINISH_SHOP,
                        EView.FINISH,
                        savedSale,
                        EModelAttribute.SALE);
            }
        }
        
        return ModelAndViewHelper.configure(EViewType.REDIRECT_CHECKOUT_STEP_THREE);
    }
}
