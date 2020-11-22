package br.com.utily.ecommerce.entity.domain.user;

import lombok.Getter;

@Getter
public enum EUserRole {

    CUSTOMER("Cliente"),
    ADMIN("Administrador");

    private final String displayName;

    EUserRole(String displayName) {
        this.displayName = displayName;
    }
}
