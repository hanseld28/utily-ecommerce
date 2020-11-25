package br.com.utily.ecommerce.util.constant.attribute;

import lombok.Getter;

@Getter
public enum EModelAttribute {

    CUSTOMERS("customers"),
    CUSTOMER("customer"),

    PRODUCTS("products"),
    PRODUCT("product"),

    SHOP_CART("shopCart"),

    NOT_ENOUGH_ADDRESS("notEnoughAddress");

    private final String name;

    EModelAttribute(String name) {
        this.name = name;
    }
}
