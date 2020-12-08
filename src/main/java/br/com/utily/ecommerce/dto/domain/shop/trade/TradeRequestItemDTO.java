package br.com.utily.ecommerce.dto.domain.shop.trade;

import br.com.utily.ecommerce.dto.DTOEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter

@Component
public class TradeRequestItemDTO extends DTOEntity {

    @NotNull(message = "O item é obrigatório")
    private Long id;

    @NotNull(message = "A quantidade é obrigatória")
    private Integer amount;

    private String reason;

    @NotNull
    private Boolean include;
}
