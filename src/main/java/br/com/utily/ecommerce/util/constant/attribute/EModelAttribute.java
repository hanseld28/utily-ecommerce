package br.com.utily.ecommerce.util.constant.attribute;

import lombok.Getter;

@Getter
public enum EModelAttribute {

    CUSTOMERS("customers"),
    CUSTOMER("customer"),

    PRODUCTS("products"),
    PRODUCT("product"),

    STOCKS("stocks"),
    STOCK("stock"),
    STOCK_MANAGE("stockManage"),

    STOCK_HISTORY("stockHistory"),

    SHOP_CART("shopCart"),

    SALE("sale"),
    SALES("sales"),
    ORDER("order"),
    ORDERS("orders"),

    ADRESSES("adresses"),
    ADDRESS("address"),
    ADDRESS_TYPE("addressType"),

    CREDIT_CARDS("creditCards"),
    CREDIT_CARD("creditCard"),
    CREDIT_CARD_AND_VALUE("creditCardAndValue"),

    VOUCHERS("vouchers"),
    VOUCHER("voucher"),

    TRADE("trade"),
    TRADES("trades"),

    MESSAGE("message"),
    IS_SUCCESS_MESSAGE("isSuccess"),
    NOT_ENOUGH_ADDRESS("notEnoughAddress"),
    ENABLE_NEXT_STEP("enableNextStep"),
    HASH("hash"),
    HASH_OPERATION("hashOperation"),
    THERE_ARE_ORDERS("thereAreOrders"),
    THERE_ARE_TRADES("thereAreTrades");

    private final String name;

    EModelAttribute(String name) {
        this.name = name;
    }
}
