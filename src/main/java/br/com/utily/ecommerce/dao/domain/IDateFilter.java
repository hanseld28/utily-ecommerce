package br.com.utily.ecommerce.dao.domain;

import br.com.utily.ecommerce.entity.Entity;

import java.util.List;

public interface IDateFilter<T extends Entity> {

    List<T> findAllByOrderByDateDesc();
}
