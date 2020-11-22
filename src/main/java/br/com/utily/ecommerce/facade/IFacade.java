package br.com.utily.ecommerce.facade;

import br.com.utily.ecommerce.entity.Entity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IFacade<T> {

    T save(T entity);

    void remove(T entity);

    Optional<T> findById(Long id, T entity);

    Optional<T> findBy(Entity targetEntity, T baseEntity);

    List<T> findAll(T entity);

}
