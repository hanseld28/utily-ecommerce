package br.com.utily.ecommerce.facade;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IFacade<T> {

    T save(T entity);

    void remove(T entity);

    Optional<T> findById(Long id, T entity);

    List<T> findAll(T entity);

}
