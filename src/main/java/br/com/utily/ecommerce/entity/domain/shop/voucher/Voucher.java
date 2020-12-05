package br.com.utily.ecommerce.entity.domain.shop.voucher;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "vouchers")
public class Voucher extends AlternativeDomainEntity {

    @Basic
    @Column(name = "vch_name")
    private String name;

    @Basic
    @Column(name = "vch_multiplication_factor")
    private Double multiplicationFactor;

    @Enumerated(EnumType.STRING)
    @Column(name = "vch_type")
    private EVoucherType type;

    @Basic
    @Column(name = "vch_value")
    private Double value;
}
