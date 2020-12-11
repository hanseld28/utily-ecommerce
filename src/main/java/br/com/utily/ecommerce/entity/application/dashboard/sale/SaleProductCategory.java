package br.com.utily.ecommerce.entity.application.dashboard.sale;

import br.com.utily.ecommerce.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
public class SaleProductCategory extends Entity {

    private String category;
    private LocalDate date;
    private Long productAmount;
    private Long salesQuantity;

    public void addProductAmount(Long productAmount) {
        this.productAmount += productAmount;
    }

    public void addSalesQuantity(Long salesQuantity) {
        this.salesQuantity += salesQuantity;
    }
}
