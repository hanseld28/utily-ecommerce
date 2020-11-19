package br.com.utily.ecommerce.strategy;

import br.com.utily.ecommerce.entity.Entity;

public interface IStrategy<T> {

    String process(T entity);
}
