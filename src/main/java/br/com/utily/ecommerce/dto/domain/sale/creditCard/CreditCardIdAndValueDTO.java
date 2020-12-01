package br.com.utily.ecommerce.dto.domain.sale.creditCard;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreditCardIdAndValueDTO extends DTOEntity {

    @NotNull
    private Long creditCardId;

    @NotNull
    private Double value;

}
