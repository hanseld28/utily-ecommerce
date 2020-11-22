package br.com.utily.ecommerce.util.constant.endpoint;

import lombok.Getter;

@Getter
public enum EEndpoint {

    ADMIN("/admin"),
    AUTH("/auth"),
    LOGIN("/login"),
    SIGN_UP("/sign-up"),

    CUSTOMER("/customer"),

    PRODUCTS("/products"),
    STOCKS("/stocks"),
    STOCK_HISTORIES("/histories"),
    SALES("/sales"),

    CUSTOMERS("/customers"),
    ACCOUNT("/account/"),
    ADRESSES("/adresses"),
    CREDIT_CARDS("/credit-cards"),
    ORDERS("/orders"),

    SHOP("/shop"),
    CART("/cart"),
    ITEMS("/items"),
    CHECKOUT("/checkout"),
    STEP("/step"),

    ERROR("/error");

    private final String path;

    EEndpoint(String path) {
        this.path = path;
    }
}
