package br.com.utily.ecommerce.service.domain.associative;

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

    List<T> findAllValidBy(Entity targetEntity, T mockBaseEntity);

    Optional<T> findValidByEmbeddedEntity(AssociativeDomainEntity associativeEntity, Entity entityOne, Entity entityTwo);

    Optional<T> findById(Long id, T mockEntity);

    Optional<T> findBy(Entity targetEntity, T mockBaseEntity);


}
