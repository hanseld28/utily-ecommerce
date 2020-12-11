package br.com.utily.ecommerce.entity.domain.user.customer.voucher;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode

@Getter
@Setter

@Embeddable
public class CustomerVoucherId implements Serializable {

    @Column(name = "cvh_cst_id", nullable = false)
    protected Long customerId;

    @Column(name = "cvh_vch_id", nullable = false)
    protected Long voucherId;

    @Column(name = "cvh_registered_at")
    private LocalDateTime date;

}
