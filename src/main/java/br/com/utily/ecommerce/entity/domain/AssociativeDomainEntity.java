package br.com.utily.ecommerce.entity.domain;

import br.com.utily.ecommerce.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@AllArgsConstructor

@Getter
@Setter

@MappedSuperclass
public class AssociativeDomainEntity extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;

}
