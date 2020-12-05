package br.com.utily.ecommerce.entity.domain.shop.voucher;

import lombok.Getter;

@Getter
public enum EVoucherType {
    DISCOUNT("Desconto"),
    TRADE("Troca");

    private final String displayName;

    EVoucherType(String displayName) {
        this.displayName = displayName;
    }
}
