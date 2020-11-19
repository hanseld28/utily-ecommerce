package br.com.utily.ecommerce.entity.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter

@MappedSuperclass
public class AlternativeDomainEntity extends DomainEntity {

    @Basic
    @Column(name = "inactivated")
    protected boolean inactivated;
}
