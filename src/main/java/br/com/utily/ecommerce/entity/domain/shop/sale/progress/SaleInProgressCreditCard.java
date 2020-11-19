package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SaleInProgressCreditCard extends Entity {

    private CreditCard creditCard;
    private Double value;

    public static SaleInProgressCreditCard from(CreditCard creditCard, Double valueUsed) {
        SaleInProgressCreditCard saleCreditCard = new SaleInProgressCreditCard();
        saleCreditCard.setCreditCard(creditCard);
        saleCreditCard.setValue(valueUsed);

        return saleCreditCard;
    }
}
