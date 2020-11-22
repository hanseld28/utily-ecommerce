package br.com.utily.ecommerce.service.domain;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.service.IService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public interface IDomainService<T extends DomainEntity> extends IService<T> {

    T save(T domainEntity);

    Collection<T> findAll(T mockEntity);

    Optional<T> findById(Long id, T mockEntity);

    Optional<T> findBy(Entity targetEntity, T mockBaseEntity);

}
