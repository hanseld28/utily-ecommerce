package br.com.utily.ecommerce.entity.domain.stock;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Component
@Table(name = "stock")
public class Stock extends DomainEntity {

    @JoinColumn(name = "stc_prt_id", foreignKey = @ForeignKey(name = "stc_prt_fk"))
    @OneToOne
    private Product product;

    @Basic
    @Column(name = "stc_quantity")
    private Integer amount;

    @Transient
    private Integer lastOperationAmount;

    public void recalculateAmount(Integer amount) {
        if (amount != null) {
            this.lastOperationAmount = amount;
            Integer existingAmount = this.amount;
            this.amount = existingAmount + amount;
        }
    }
}
