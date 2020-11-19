package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString

@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper = false)

@Getter
@Setter

@Entity
@Table(name = "sales_adresses")
public class SaleAddress extends br.com.utily.ecommerce.entity.Entity implements Serializable {

    @EmbeddedId
    private SaleAddressId id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @MapsId("addressId")
    @JoinColumn(name = "ssa_adr_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("saleId")
    @JoinColumn(name = "ssa_sls_id")
    private Sale sale;

    public static SaleAddress from(Address address) {
        SaleAddress saleAddress = new SaleAddress();

        saleAddress.id = new SaleAddressId();
        saleAddress.id.setAddressId(address.getId());

        saleAddress.address = address;

        return saleAddress;
    }

    public void setAddress(Address address) {
        SaleAddressId saleItemId = new SaleAddressId();
        saleItemId.setAddressId(address.getId());

        this.id = saleItemId;
        this.address = address;
    }
}
