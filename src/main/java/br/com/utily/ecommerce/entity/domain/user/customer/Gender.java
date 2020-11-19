package br.com.utily.ecommerce.entity.domain.user.customer;

import lombok.Getter;

@Getter
public enum Gender {

    MALE("Masculino"),
    FEMALE("Feminino");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }
}
