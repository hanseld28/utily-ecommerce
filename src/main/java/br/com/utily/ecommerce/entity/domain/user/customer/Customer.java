package br.com.utily.ecommerce.entity.domain.user.customer;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@Entity
@Component
@Table(name = "customers")
public class Customer extends AlternativeDomainEntity {

    @Basic
    @Column(name = "cst_name")
    private String name;

    @Basic
    @Column(name = "cst_cpf")
    private String cpf;

    @Basic
    @Column(name = "cst_birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "cst_gender")
    private Gender gender;

    @Basic
    @Column(name = "cst_phone")
    private String phone;

    @OneToOne(
            cascade = { CascadeType.ALL },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "cst_usr_id", foreignKey = @ForeignKey(name = "cst_usr_fk"))
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> adresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CreditCard> creditCards;

    @OneToMany(mappedBy = "customer")
    private List<Sale> orders;

    @OneToMany(mappedBy = "customer")
    private List<CustomerVoucher> vouchers;
}