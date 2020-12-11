package br.com.utily.ecommerce.entity.application.dashboard.sale;

import br.com.utily.ecommerce.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class SaleProductCategoryResult extends Entity {

    private String category;
    private LocalDateTime date;
    private Long productAmount;
    private Long salesQuantity;

}
