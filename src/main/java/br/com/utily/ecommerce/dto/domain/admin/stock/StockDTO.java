package br.com.utily.ecommerce.dto.domain.admin.stock;

import br.com.utily.ecommerce.dto.DTOEntity;
import br.com.utily.ecommerce.dto.domain.admin.product.ProductIdDAO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter

@Component
public class StockDTO extends DTOEntity {

    @NotNull(message = "O produto é obrigatório")
    private ProductIdDAO product;

    @Min(value = 1, message = "A quantidade mínima é 1")
    @NotNull(message = "A quantidade é obrigatória")
    private Integer amount;

}
