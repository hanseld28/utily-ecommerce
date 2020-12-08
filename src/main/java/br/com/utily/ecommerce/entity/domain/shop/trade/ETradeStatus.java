package br.com.utily.ecommerce.entity.domain.shop.trade;

import lombok.Getter;

@Getter
public enum ETradeStatus {
    AWAITING_AUTHORIZATION("Aguardando autorização"),
    DENIED("Negada"),
    AUTHORIZED("Autorizada"),
    RECEIVED_ITEMS("Itens recebidos"),
    REPLACEMENT_ON_DELIVERY("Subistituição na entrega"),
    REPLACEMENT_DELIVERED("Substituição entregue");

    private final String displayName;

    ETradeStatus(String displayName) {
        this.displayName = displayName;
    }
}
