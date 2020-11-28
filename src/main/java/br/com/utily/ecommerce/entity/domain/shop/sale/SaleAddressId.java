package br.com.utily.ecommerce.entity.domain.shop.sale;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode

@Getter
@Setter

@Component
@Embeddable
public class SaleAddressId implements Serializable {

    @Column(name = "ssa_sls_id", nullable = false)
    protected Long saleId;

    @Column(name = "ssa_adr_id", nullable = false)
    protected Long addressId;
}
