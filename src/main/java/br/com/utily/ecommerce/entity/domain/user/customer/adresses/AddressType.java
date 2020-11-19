package br.com.utily.ecommerce.entity.domain.user.customer.adresses;

import lombok.Getter;

@Getter
public enum AddressType {
    SHIPPING("Entrega"),
    BILLING("Cobrança"),
    SHIPPING_AND_BILLING("Entrega e Cobrança");

    private String displayName;

    AddressType(String displayName) {
        this.displayName = displayName;
    }
}
