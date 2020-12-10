package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.freight.Freight;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.shop.voucher.EVoucherType;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "sales")
public class Sale extends DomainEntity {

    @Basic
    @Column(name = "sls_identify_number")
    private String identifyNumber;

    @CreationTimestamp
    @Column(name = "sls_purchase_date")
    private LocalDateTime date;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "sls_status")
    private ESaleStatus status;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "sls_cst_id", foreignKey = @ForeignKey(name = "sls_cst_fk"))
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
    private List<SaleItem> items;

    @OneToMany(mappedBy = "sale", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
    private List<SaleAddress> adresses;

    @OneToMany(mappedBy = "sale", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH })
    private List<SaleCreditCard> usedCreditCards;

    @ManyToOne
    @JoinColumn(name = "sls_vch_id", foreignKey = @ForeignKey(name = "sls_vch_id"))
    private Voucher voucher;

    @OneToMany(mappedBy = "order")
    private List<Trade> trades;

    @OneToOne(mappedBy = "sale", cascade = CascadeType.ALL, optional = false)
    private Freight freight;

    public void putStatusToProcessing() {
        this.status = ESaleStatus.PROCESSING;
    }

    public void changeStatusToInTransit() {
        this.status = ESaleStatus.IN_TRANSIT;
    }

    public void changeStatusToDelivered() {
        this.status = ESaleStatus.DELIVERED;
    }

    public String computeStatusColorClass(String prefix) {
        StringBuilder statusColor = new StringBuilder(prefix);
        statusColor.append("-");

        if (status.equals(ESaleStatus.PROCESSING)) {
            statusColor.append("info");
        }
        if (status.equals(ESaleStatus.IN_TRANSIT)) {
            statusColor.append("warning");
        }
        if (status.equals(ESaleStatus.DELIVERED)) {
            statusColor.append("success");
        }

        return statusColor.toString();
    }

    public boolean inProcessing() {
        return status.equals(ESaleStatus.PROCESSING);
    }

    public boolean inTransit() {
        return status.equals(ESaleStatus.IN_TRANSIT);
    }

    public boolean delivered() {
        return status.equals(ESaleStatus.DELIVERED);
    }

    public boolean wasOrIsInProcessing() {
        return inProcessing() || inTransit() || delivered();
    }

    public boolean wasOrIsInTransit() {
        return inTransit() || delivered();
    }

    public Boolean hasAnyVoucherApplied() {
        return voucher != null && voucher.getId() != null
                && (voucher.getMultiplicationFactor() != null || voucher.getValue() != null);
    }

    public Double calculateTotal() {
        Double subtotal = items.stream()
                .map(SaleItem::getSubtotal)
                .reduce(.0, Double::sum);

        return hasAnyVoucherApplied()
                ? subtotal - calculateDifferenceWithVoucher(subtotal)
                : subtotal;
    }

    private Double calculateDifferenceWithVoucher(Double subtotal) {
        Double coefficient = isDiscountVoucher()
                ? voucher.getMultiplicationFactor()
                : voucher.getValue();

        return (isDiscountVoucher()
                ? subtotal * coefficient
                : Math.min(coefficient, subtotal));
    }

    public Double calculateTotalWithFreight() {
        return calculateTotalWithoutVoucher() + freight.getValue();
    }

    public Double calculateTotalWithoutVoucher() {
        return isDiscountVoucher()
                ? calculateTotal() / (1 - voucher.getMultiplicationFactor())
                : calculateTotal() - voucher.getValue();
    }

    public Boolean isDiscountVoucher() {
        return voucher.getType().equals(EVoucherType.DISCOUNT);
    }

}
