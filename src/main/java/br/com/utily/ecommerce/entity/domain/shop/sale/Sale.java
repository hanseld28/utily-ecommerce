package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "sales")
public class Sale extends DomainEntity {

    @Basic
    @Column(name = "sls_identify_number")
    private String identifyNumber;

    @CreationTimestamp
    @Column(name = "sls_purchase_date")
    private LocalDateTime purchaseDate;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "sls_status")
    private ESaleStatus status;

    @JoinColumn(name = "sls_cst_id", foreignKey = @ForeignKey(name = "sls_cst_fk"))
    @ManyToOne(cascade = CascadeType.DETACH)
    private Customer customer;

    private void generateIdentifyNumber() {
        String millis = String.valueOf(System.currentTimeMillis());
        StringBuilder invertedMillis = new StringBuilder();

        String uniqueCode = (customer != null && customer.getId() != null)
                ? customer.getId().toString()
                : String.valueOf(this.hashCode());

        invertedMillis.append(uniqueCode);

        for (int i = millis.length() - 1; i >= 0; i--) {
            invertedMillis.append(millis.charAt(i));
        }

        identifyNumber = invertedMillis.toString();
    }

    public void putStatusToProcessing() {
        this.status = ESaleStatus.PROCESSING;
    }

    public void changeStatusToInTransit() {
        this.status = ESaleStatus.IN_TRANSIT;
    }

    public void changeStatusToDelivered() {
        this.status = ESaleStatus.DELIVERED;
    }

    public void finish() {
        generateIdentifyNumber();
        putStatusToProcessing();
    }
}
