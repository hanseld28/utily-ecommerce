package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.AssociativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@ToString

@AllArgsConstructor
@NoArgsConstructor

@EqualsAndHashCode(callSuper = false)

@Getter
@Setter

@Entity
@Component
//@IdClass(SaleAddressId.class) // TODO: VERIFICAR PORQUE O IDCLASS NÃO ESTÁ ENCONTRANDO AS PROPRIEDADES DE SALEADDRESSID
@Table(name = "sales_adresses")
public class SaleAddress extends AssociativeDomainEntity {

    @EmbeddedId
    private SaleAddressId id;

    @MapsId("addressId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ssa_adr_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @MapsId("saleId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ssa_sls_id", referencedColumnName = "id", nullable = false)
    private Sale sale;

    public SaleAddress adapt(SaleAddressId saleAddressId, Sale sale, Address address) {
        Long addressId = address.getId();
        saleAddressId.setAddressId(addressId);

        this.setId(saleAddressId);
        this.setAddress(address);
        this.setSale(sale);
        return this;
    }
}

// TODO: UTIL LINKS:
//  https://www.objectdb.com/api/java/jpa/EmbeddedId
//  https://www.guj.com.br/t/embeddedid-onetomany/206317/9
//  https://www.google.com/search?q=Cascade+on+entity+with+embedded+id&oq=Cascade+on+entity+with+embedded+id&aqs=chrome..69i57.21230j0j7&sourceid=chrome&ie=UTF-8
