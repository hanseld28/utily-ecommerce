package br.com.utily.ecommerce.util.constant.endpoint;

import lombok.Getter;

@Getter
public enum EEndpoint {

    NEW("/new"),
    EDIT("/edit"),
    MANAGE("/manage"),
    PATH_VARIABLE("/{pathVariable}"),
    IN_PROGRESS("/in-progress"),
    REQUEST("/request"),
    FINISH("/finish"),

    ADMIN("/admin"),
    AUTH("/auth"),
    LOGIN("/login"),
    SIGN_UP("/sign-up"),

    CUSTOMER("/customer"),

    PRODUCTS("/products"),
    STOCKS("/stocks"),
    STOCK_HISTORIES("/history"),
    SALES("/sales"),

    CUSTOMERS("/customers"),
    ACCOUNT("/account/"),
    ADRESSES("/adresses"),
    CREDIT_CARDS("/credit-cards"),
    ORDERS("/orders"),
    ORDER("/order"),
    TRADES("/trades"),
    TRADE("/trade"),

    SHOP("/shop"),
    CART("/cart"),
    ITEMS("/items"),
    CHECKOUT("/checkout"),
    STEP("/step"),
    STEP_ONE("/one"),
    STEP_TWO("/two"),
    STEP_THREE("/three"),

    ERROR("/error");

    private final String path;

    EEndpoint(String path) {
        this.path = path;
    }
}
