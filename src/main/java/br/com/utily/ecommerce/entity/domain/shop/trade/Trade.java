package br.com.utily.ecommerce.entity.domain.shop.trade;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.trade.item.TradeItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "trades")
public class Trade extends DomainEntity {

    @Basic
    @Column(name = "trd_tracking_number", unique = true)
    private String trackingNumber;

    @CreationTimestamp
    @Column(name = "trd_request_date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "trd_type")
    private ETradeType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "trd_status")
    private ETradeStatus status;

    @OneToOne(optional = false)
    private Sale sale;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<TradeItem> items;

    public void generateTrackingNumber() {
        String uuid = String.valueOf(UUID.randomUUID());
        StringBuilder modifiedUUID  = new StringBuilder(uuid);

        String uniqueCode = (sale != null && sale.getId() != null)
                ? sale.getId().toString()
                : String.valueOf(this.hashCode());

        modifiedUUID.append(uniqueCode);

        trackingNumber = modifiedUUID.toString();
    }

    public void authorize() {
        generateTrackingNumber();
        putStatusToAuthorized();
    }

    public void putStatusToAwaitingAuthorization() {
        this.status = ETradeStatus.AWAITING_AUTHORIZATION;
    }

    public void putStatusToAuthorized() {
        this.status = ETradeStatus.AUTHORIZED;
    }

    public void putStatusToDenied() {
        this.status = ETradeStatus.DENIED;
    }

    public void putStatusToReceivedItems() {
        this.status = ETradeStatus.RECEIVED_ITEMS;
    }

    public void putStatusToReplacementOnDelivery() {
        this.status = ETradeStatus.REPLACEMENT_ON_DELIVERY;
    }

    public void putStatusReplacementDelivered() {
        this.status = ETradeStatus.REPLACEMENT_DELIVERED;
    }

}
