package br.com.utily.ecommerce.entity.domain.shop.sale;

import lombok.Getter;
import lombok.ToString;

@ToString

@Getter
public enum ESaleStatus {
    PROCESSING("Em Processamento"),
    IN_TRANSIT("Em Trânsito"),
    DELIVERED("Entregue");

    private String displayName;

    ESaleStatus(String displayName) {
        this.displayName = displayName;
    }

}
