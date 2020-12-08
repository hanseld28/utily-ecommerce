package br.com.utily.ecommerce.entity.domain.shop.trade;

import lombok.Getter;

@Getter
public enum ETradeType {
    EXCHANGE("Troca"),
    REFUND("Devolução");

    private final String displayName;

    ETradeType(String displayName) {
        this.displayName = displayName;
    }
}
