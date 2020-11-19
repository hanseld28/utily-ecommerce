package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@ToString

@EqualsAndHashCode(callSuper = false)

@Getter
@Setter

@Entity
@Table(name = "sales_credit_cards")
public class SaleCreditCard extends br.com.utily.ecommerce.entity.Entity implements Serializable {

    @EmbeddedId
    private SaleCreditCardId id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @MapsId("creditCardId")
    @JoinColumn(name = "scc_crd_id")
    private CreditCard creditCard;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("saleId")
    @JoinColumn(name = "scc_sls_id")
    private Sale sale;

    @Basic
    @Column(name = "scc_value")
    private Double value;

    public static SaleCreditCard from(CreditCard creditCard, Double valueUsed) {
        SaleCreditCardId saleCreditCardId = new SaleCreditCardId();
        saleCreditCardId.setCreditCardId(creditCard.getId());

        SaleCreditCard saleCreditCard = new SaleCreditCard();
        saleCreditCard.setId(saleCreditCardId);
        saleCreditCard.setCreditCard(creditCard);
        saleCreditCard.setValue(valueUsed);

        return saleCreditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        SaleCreditCardId saleCreditCardId = new SaleCreditCardId();
        saleCreditCardId.setCreditCardId(creditCard.getId());

        this.id = saleCreditCardId;
        this.creditCard = creditCard;
    }
}
