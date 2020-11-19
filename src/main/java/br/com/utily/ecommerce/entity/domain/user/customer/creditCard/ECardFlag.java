package br.com.utily.ecommerce.entity.domain.user.customer.creditCard;

import lombok.Getter;

@Getter
public enum ECardFlag {

    VISA("Visa"),
    MASTERCARD("MasterCard");


    private final String displayName;

    ECardFlag(String displayName) {
        this.displayName = displayName;
    }
}
