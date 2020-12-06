package br.com.utily.ecommerce.dto.domain.admin.stock;

import br.com.utily.ecommerce.constraint.domain.admin.stock.AmountNotEqualToZeroConstraint;
import br.com.utily.ecommerce.dto.DTOEntity;
import br.com.utily.ecommerce.dto.domain.admin.product.ProductForSimpleStockViewDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter

@Component
public class StockManageDTO extends DTOEntity {

    @NotNull(message = "O estoque é obrigatório")
    private Long id;

    private ProductForSimpleStockViewDTO product;

    private Integer amount;

    @AmountNotEqualToZeroConstraint
    @NotNull(message = "A quantidade da movimentação no estoque é obrigatória")
    private Integer operationAmount;
}
