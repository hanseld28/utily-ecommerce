package br.com.utily.ecommerce.entity.domain.user.customer.adresses;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleAddress;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "adresses")
public class Address extends AlternativeDomainEntity {

    @Basic
    @Column(name = "adr_owner_description")
    private String ownerDescription;

    @Basic
    @Column(name = "adr_cep")
    private String cep;

    @Basic
    @Column(name = "adr_public_place")
    private String publicPlace;

    @Basic
    @Column(name = "adr_number")
    private String number;

    @Basic
    @Column(name = "adr_complement")
    private String complement;

    @Basic
    @Column(name = "adr_neighbourhood")
    private String neighbourhood;

    @Basic
    @Column(name = "adr_city")
    private String city;

    @Basic
    @Column(name = "adr_state")
    private String state;

    @JoinColumn(name = "adr_cst_id", foreignKey = @ForeignKey(name = "adr_cst_fk"))
    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "adr_type")
    private AddressType type;

    @OneToMany(mappedBy = "address")
    private List<SaleAddress> relatedSales = new ArrayList<>();

    public String buildAddressShortName() {
        if (publicPlace != null && number != null) {
            StringBuilder addressShortName = new StringBuilder(publicPlace);
            addressShortName.append(", ");
            addressShortName.append(number);

            if (ownerDescription != null) {
                addressShortName.append(" - ");
                addressShortName.append(ownerDescription);
            }
            if (ownerDescription == null && cep != null) {
                addressShortName.append("(");
                addressShortName.append(cep);
                addressShortName.append(")");
            }

            return addressShortName.toString();
        }

        return null;
    }

    public String buildOwnerIfSpecifiedAndAddressType() {
        StringBuilder addressShortName = new StringBuilder();

        if (ownerDescription != null) {
            addressShortName.append(ownerDescription);
        }
        if (type != null) {
            addressShortName.append(" - ");
            addressShortName.append("Endereço de ");
            addressShortName.append(type.getDisplayName());
        }
        if (ownerDescription == null && type == null && id != null) {
            addressShortName.append("Endereço ID ");
            addressShortName.append(id);
        }

        return addressShortName.toString();
    }

    public boolean isBilling() {
        return type.equals(AddressType.BILLING);
    }

    public boolean isShipping() {
        return type.equals(AddressType.SHIPPING);
    }

    public boolean isShippingAndBilling() {
        return type.equals(AddressType.SHIPPING_AND_BILLING);
    }
}
