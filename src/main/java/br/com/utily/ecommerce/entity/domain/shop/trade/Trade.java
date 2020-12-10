package br.com.utily.ecommerce.entity.domain.shop.trade;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.trade.item.TradeItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "trades")
public class Trade extends DomainEntity {

    @Basic
    @Column(name = "trd_number", unique = true)
    private String number;

    @CreationTimestamp
    @Column(name = "trd_request_date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "trd_type")
    private ETradeType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "trd_status")
    private ETradeStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trd_sls_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "trd_sls_fk"))
    private Sale order;

    @OneToMany(mappedBy = "trade", cascade = CascadeType.ALL)
    private List<TradeItem> items;

    private void generateNumber() {
        String uuid = String.valueOf(UUID.randomUUID());
        StringBuilder modifiedUUID  = new StringBuilder(uuid);

        String uniqueCode = (order != null && order.getId() != null)
                ? order.getId().toString()
                : String.valueOf(this.hashCode());

        modifiedUUID.append(uniqueCode);

        number = modifiedUUID.toString();
    }

    public void request() {
        changeStatusToAwaitingAuthorization();
        generateNumber();
    }

    public Boolean isReceivedItems() {
        return status.equals(ETradeStatus.RECEIVED_ITEMS);
    }

    public void authorize() {
        changeStatusToAuthorized();
    }

    public void deny() {
        this.status = ETradeStatus.DENIED;
    }

    public void changeStatusToAwaitingAuthorization() {
        this.status = ETradeStatus.AWAITING_AUTHORIZATION;
    }

    public void changeStatusToAuthorized() {
        this.status = ETradeStatus.AUTHORIZED;
    }

    public void changeStatusToReceivedItems() {
        this.status = ETradeStatus.RECEIVED_ITEMS;
    }

    public void changeStatusGeneratedVoucher() {
        this.status = ETradeStatus.GENERATED_VOUCHER;
    }

    public void changeStatusToReplacementOnDelivery() {
        this.status = ETradeStatus.REPLACEMENT_ON_DELIVERY;
    }

    public void changeStatusReplacementDelivered() {
        this.status = ETradeStatus.REPLACEMENT_DELIVERED;
    }

    public Double calculateTotalBalanceOfItems() {
        return items.stream()
                .map(tradeItem -> tradeItem.getProduct().getPrice() * tradeItem.getQuantity())
                .reduce(0d, Double::sum);
    }

    public Boolean isGeneratedVoucher() {
        return status.equals(ETradeStatus.GENERATED_VOUCHER);
    }
}
