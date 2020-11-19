package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.cart.ShopCart;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@AllArgsConstructor

@Getter
@Setter

@Component
public class SaleInProgress extends Entity {

    private Customer customer;
    private Collection<SaleInProgressItem> items;
    private Collection<SaleInProgressAddress> adresses;
    private Collection<SaleInProgressCreditCard> usedCreditCards;
    private Double total;
    private final Double freightValue = 10.0;

    public SaleInProgress() {
        adresses = new HashSet<>();
        usedCreditCards = new HashSet<>();
        items = new HashSet<>();
    }

    public void addItemsFromShopCart(ShopCart shopCart) {
        List<CartItem> shopCartItems = shopCart.getItems();

        int i = 0, length = shopCartItems.size();
        for(; i < length; i++) {
            CartItem cartItem = shopCartItems.get(i);
            SaleInProgressItem saleInProgressItem = SaleInProgressItem.from(cartItem);
            items.add(saleInProgressItem);
        }

        total = shopCart.getTotal();
    }

    public void addAddress(Address address) {
        SaleInProgressAddress saleInProgressAddress = SaleInProgressAddress.from(address);
        adresses.add(saleInProgressAddress);
    }

    public void addCreditCardAndValueUsed(CreditCard creditCard, Double valueUsed) {
        SaleInProgressCreditCard saleInProgressCreditCard = SaleInProgressCreditCard.from(creditCard, valueUsed);
        usedCreditCards.add(saleInProgressCreditCard);
    }

    public void removeCreditCardUsedById(Long creditCardId) {
        usedCreditCards.removeIf(saleCreditCard -> saleCreditCard.getCreditCard().getId().equals(creditCardId));
    }

    public Boolean creditCreditCardAlreadyAdded(CreditCard creditCard) {
        return usedCreditCards.stream().anyMatch(saleCreditCard -> saleCreditCard.getCreditCard().equals(creditCard));
    }

    public Double recalculateTotalWithFreight() {
        return total + freightValue;
    }

    public Double calculateRemainingAmount() {
        Double coverageAmountByPaymentMethod = usedCreditCards.stream()
                .map(SaleInProgressCreditCard::getValue)
                .reduce(0.0, Double::sum);
        return recalculateTotalWithFreight() - coverageAmountByPaymentMethod;
    }

    public Boolean isRemainingAmountFullyCovered() {
        return calculateRemainingAmount() == 0.0;
    }
}
