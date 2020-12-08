package br.com.utily.ecommerce.util.constant.folder;

import lombok.Getter;

@Getter
public enum EPageFolder {

    PAGES("pages/"),

    ADMIN("admin/"),
    AUTH("auth/"),

    PRODUCT("product/"),
    STOCK("stock/"),
    STOCK_HISTORY("history/"),
    SALE("sale/"),

    CUSTOMER("customer/"),
    ACCOUNT("account/"),
    ADDRESS("address/"),
    CREDIT_CARD("credit-card/"),
    ORDER("order/"),
    TRADE("trade/"),

    SHOP("shop/"),
    CART("cart/"),
    CHECKOUT("checkout/"),
    STEP("step/"),

    ERROR("/error");

    private final String path;

    EPageFolder(String path) {
        this.path = path;
    }
}
