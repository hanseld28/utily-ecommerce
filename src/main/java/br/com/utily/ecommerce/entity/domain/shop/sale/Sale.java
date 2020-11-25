package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

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

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleAddress> adresses;
}
