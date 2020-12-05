package br.com.utily.ecommerce.entity.domain.user.customer.voucher;

import br.com.utily.ecommerce.entity.domain.AssociativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import lombok.*;

import javax.persistence.*;

@ToString

@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper = false)

@Getter
@Setter

@Entity
@Table(name = "customers_vouchers")
public class CustomerVoucher extends AssociativeDomainEntity {

    @EmbeddedId
    private CustomerVoucherId id;

    @MapsId("voucherId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cvh_vch_id", referencedColumnName = "id", nullable = false)
    private Voucher voucher;

    @MapsId("customerId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cvh_cst_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    public CustomerVoucher adapt(CustomerVoucherId customerVoucherId,
                                 Customer customer,
                                 Voucher voucher) {
        Long voucherId = voucher.getId();
        Long customerId = customer.getId();

        customerVoucherId.setVoucherId(voucherId);
        customerVoucherId.setCustomerId(customerId);
        customerVoucherId.setUsed(true);
        
        this.setId(customerVoucherId);
        this.setVoucher(voucher);
        this.setCustomer(customer);

        return this;
    }
}