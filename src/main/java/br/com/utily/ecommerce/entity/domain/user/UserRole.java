package br.com.utily.ecommerce.entity.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {

    CUSTOMER("Cliente"),
    ADMIN("Administrador");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }
}
