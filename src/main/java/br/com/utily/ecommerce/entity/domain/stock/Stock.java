package br.com.utily.ecommerce.entity.domain.stock;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@ToString

@Getter
@Setter

@Entity
@Component
@Table(name = "stock")
public class Stock extends DomainEntity {

    @JoinColumn(name = "stc_prt_id", foreignKey = @ForeignKey(name = "stc_prt_fk"))
    @OneToOne(optional = false)
    private Product product;

    @Basic
    @Column(name = "stc_quantity")
    private Integer amount;

    @Transient
    private Integer lastOperationAmount;

    public Integer getLastOperationAmount() {
        return lastOperationAmount != null ? lastOperationAmount : amount;
    }

    public void recalculateAmount() {
        if (amount != null && lastOperationAmount != null) {
            int updatedAmount = lastOperationAmount + amount;
            lastOperationAmount = amount;
            amount = updatedAmount;
        }
    }
}
