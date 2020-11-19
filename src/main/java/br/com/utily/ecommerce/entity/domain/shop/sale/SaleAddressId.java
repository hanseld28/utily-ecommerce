package br.com.utily.ecommerce.entity.domain.shop.sale;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode

@Getter
@Setter

@Embeddable
public class SaleAddressId implements Serializable {

    @Column(name = "ssa_sls_id")
    private Long saleId;

    @Column(name = "ssa_adr_id")
    private Long addressId;
}
