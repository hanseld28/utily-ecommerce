package br.com.utily.ecommerce.service.associative;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.AssociativeDomainEntity;
import br.com.utily.ecommerce.service.IService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IAssociativeDomainService<T extends AssociativeDomainEntity>
        extends IService<T> {

    T save(T entity);

    List<T> findAll(T mockEntity);

    List<T> findAllBy(Entity targetEntity, T mockBaseEntity);

    Optional<T> findById(Long id, T mockEntity);

    Optional<T> findBy(Entity targetEntity, T mockBaseEntity);

}
