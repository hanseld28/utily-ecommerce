package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor


@Getter
@Setter

public class SaleInProgressAddress extends Entity {

    private Address address;

    public static SaleInProgressAddress from(Address address) {
        SaleInProgressAddress saleAddress = new SaleInProgressAddress();
        saleAddress.address = address;

        return saleAddress;
    }
}
