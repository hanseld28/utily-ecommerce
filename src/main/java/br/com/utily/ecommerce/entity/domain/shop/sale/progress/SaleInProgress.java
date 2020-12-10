package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.freight.Freight;
import br.com.utily.ecommerce.entity.domain.shop.sale.ESaleStatus;
import br.com.utily.ecommerce.entity.domain.shop.voucher.EVoucherType;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Scope(
        scopeName = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
@Component
public class SaleInProgress extends Entity {

    private String identifyNumber;
    private ESaleStatus status;
    private Customer customer;
    private List<Address> adresses = new ArrayList<>();
    private List<CartItem> cartItems = new ArrayList<>();
    private List<CreditCardValue> usedCreditCards = new ArrayList<>();
    private Voucher voucher;
    private Freight freight;
    private double subtotal;
    private double total;
    private Boolean voucherAlreadyApplied = false;

    public void addAddress(Address address) {
        adresses.add(address);
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
        addToTotal(subtotal);
    }

    public void addToTotal(Double value) {
        total += value;
    }

    public void calculateFreight() {
        String zipCode = adresses.stream()
                .filter(address -> address.isShippingAndBilling() || address.isBilling())
                .findFirst()
                .orElseThrow(NotFoundException::new)
                .getCep();
        freight.calculate(subtotal, zipCode);
        addToTotal(freight.getValue());
    }

    public void addCreditCardValue(CreditCardValue creditCardValue) {
        usedCreditCards.add(creditCardValue);
    }

    private void generateIdentifyNumber() {
        String millis = String.valueOf(System.currentTimeMillis());
        StringBuilder invertedMillis = new StringBuilder();

        String uniqueCode = (customer != null && customer.getId() != null)
                ? customer.getId().toString()
                : String.valueOf(this.hashCode());

        invertedMillis.append(uniqueCode);

        for (int i = millis.length() - 1; i >= 0; i--) {
            invertedMillis.append(millis.charAt(i));
        }

        identifyNumber = invertedMillis.toString();
    }

    public void putStatusToProcessing() {
        this.status = ESaleStatus.PROCESSING;
    }

    public void finish() {
        generateIdentifyNumber();
        putStatusToProcessing();
    }

    public void removeCreditCardUsedById(Long creditCardId) {
        usedCreditCards.removeIf(saleCreditCard -> saleCreditCard.getCreditCard().getId().equals(creditCardId));
    }

    public Boolean creditCreditCardAlreadyAdded(CreditCard creditCard) {
        return usedCreditCards.stream()
                .anyMatch(saleCreditCard -> saleCreditCard
                        .getCreditCard()
                        .equals(creditCard));
    }

    public Double calculateRemainingAmount() {
        Double coverageAmountByPaymentMethod = usedCreditCards.stream()
                .map(CreditCardValue::getValue)
                .reduce(0.0, Double::sum);
        
        return total - coverageAmountByPaymentMethod;
    }

    public Boolean isRemainingAmountFullyCovered() {
        return calculateRemainingAmount() == .0;
    }

    public Double calculateTotalWithoutVoucher() {
        return isDiscountVoucher()
                ? total / (1 - voucher.getMultiplicationFactor())
                : total - voucher.getValue();
    }

    public Double calculateTotalWithFreightBeforeVoucherApplied() {
        return (isDiscountVoucher()
                ? total / (1 - voucher.getMultiplicationFactor())
                : total + voucher.getValue()
        ) + freight.getValue();
    }

    public Boolean isDiscountVoucher() {
        return voucher.getType().equals(EVoucherType.DISCOUNT);
    }

    public void applyVoucher(Voucher voucher) {
        if (!voucherAlreadyApplied) {
            this.voucher = voucher;
            double x = (isDiscountVoucher() ? voucher.getMultiplicationFactor() : voucher.getValue());
            addToTotal((isDiscountVoucher() ? total * x : Math.min(x, total)) * -1);

            voucherAlreadyApplied = true;
        }
    }
}
