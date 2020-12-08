package br.com.utily.ecommerce.entity.domain.shop.trade.item;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode

@Getter
@Setter

@Embeddable
public class TradeItemId implements Serializable {

    @Column(name = "trp_trd_id")
    private Long tradeId;

    @Column(name = "trp_prt_id")
    private Long itemId;

    @Column(name = "trp_registered_at")
    private LocalDateTime registeredAt;
}
