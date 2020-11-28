package br.com.utily.ecommerce.util.constant.attribute;

import lombok.Getter;

@Getter
public enum EModelAttribute {

    CUSTOMERS("customers"),
    CUSTOMER("customer"),

    PRODUCTS("products"),
    PRODUCT("product"),

    SHOP_CART("shopCart"),

    SALE("sale"),
    SALES("sales"),

    ADRESSES("adresses"),
    ADDRESS("address"),
    ADDRESS_TYPE("addressType"),

    NOT_ENOUGH_ADDRESS("notEnoughAddress");

    private final String name;

    EModelAttribute(String name) {
        this.name = name;
    }
}
