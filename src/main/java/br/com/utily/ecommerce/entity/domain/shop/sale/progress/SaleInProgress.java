package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.ESaleStatus;
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
    private Double total;
    private final Double freightValue = 10.0;
    private Boolean voucherAlreadyApplied = false;

    public void addAddress(Address address) {
        adresses.add(address);
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

    public Double recalculateTotalWithFreight() {
        return total + freightValue;
    }

    public Double calculateRemainingAmount() {
        Double coverageAmountByPaymentMethod = usedCreditCards.stream()
                .map(CreditCardValue::getValue)
                .reduce(0.0, Double::sum);
        
        return recalculateTotalWithFreight() - coverageAmountByPaymentMethod;
    }

    public Boolean isRemainingAmountFullyCovered() {
        return calculateRemainingAmount() == 0.0;
    }

    public Double calculateTotalWithoutVoucher() {
        return total / (1 - voucher.getMultiplicationFactor());
    }

    public Double calculateTotalWithFreightBeforeVoucherApplied() {
        return (total / (1 - voucher.getMultiplicationFactor())) + freightValue;
    }

    public void applyVoucher(Voucher voucher) {
        if (!voucherAlreadyApplied) {
            this.voucher = voucher;
            total -= (total * voucher.getMultiplicationFactor());
            voucherAlreadyApplied = true;
        }
    }
}
