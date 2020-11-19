package br.com.utily.ecommerce.entity.domain.product.provider;

import br.com.utily.ecommerce.entity.domain.AlternativeDomainEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Component
@Table(name = "providers")
public class Provider extends AlternativeDomainEntity {

    @Basic
    @Column(name = "pvd_name")
    private String name;

}
