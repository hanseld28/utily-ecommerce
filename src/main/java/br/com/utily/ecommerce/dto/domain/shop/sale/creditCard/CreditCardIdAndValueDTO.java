package br.com.utily.ecommerce.dto.domain.shop.sale.creditCard;

import br.com.utily.ecommerce.constraint.domain.shop.sale.creditCard.MinimumPaymentValueConstraint;
import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter

@Component
public class CreditCardIdAndValueDTO extends DTOEntity {

    @NotNull
    private Long creditCardId;

    @NotNull
    @MinimumPaymentValueConstraint
    private Double value;

}
