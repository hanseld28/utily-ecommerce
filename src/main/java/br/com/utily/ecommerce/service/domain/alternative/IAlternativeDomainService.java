package br.com.utily.ecommerce.service.domain.alternative;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.service.IService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IAlternativeDomainService<T extends DomainEntity> extends IService<T> {

    T save(T domainEntity);

    T saveAndFlush(T domainEntity);

    List<T> findAll(T mockEntity);

    List<T> findAllBy(Entity targetEntity, T mockBaseEntity);

    Optional<T> findById(Long id, T mockEntity);

    Optional<T> findBy(Entity targetEntity, T mockBaseEntity);

}
