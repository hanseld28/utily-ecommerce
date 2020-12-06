package br.com.utily.ecommerce.entity.domain.stock;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "stock_history")
public class StockHistory extends DomainEntity {

    @Basic
    @Column(name = "sth_quantity")
    private Integer amount;

    @Column(name = "sth_date")
    private LocalDateTime date;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "sth_stc_id", referencedColumnName = "id", unique = true)
    private Stock stock;

    public void fillFieldsWith(Stock stock) {
        this.stock = stock;
        this.amount = stock.getLastOperationAmount();
        this.date = LocalDateTime.now();
    }
}

