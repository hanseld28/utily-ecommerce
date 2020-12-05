package br.com.utily.ecommerce.service.associative.impl;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.AssociativeDomainEntity;
import br.com.utily.ecommerce.facade.IFacade;
import br.com.utily.ecommerce.service.associative.IAssociativeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociativeDomainService<T extends AssociativeDomainEntity> implements IAssociativeDomainService<T> {

    private IFacade<T> facade;

    @Autowired
    private void setFacade(IFacade<T> facade) {
        this.facade = facade;
    }

    @Override
    public T save(T entity) {
        return facade.save(entity);
    }

    @Override
    public List<T> findAll(T mockEntity) {
        return facade.findAll(mockEntity);
    }

    @Override
    public List<T> findAllBy(Entity targetEntity, T mockBaseEntity) {
        return facade.findAllBy(targetEntity, mockBaseEntity);
    }

    @Override
    public Optional<T> findById(Long id, T mockEntity) {
        return facade.findById(id, mockEntity);
    }
    @Override
    public Optional<T> findBy(Entity targetEntity, T mockBaseEntity) {
        return facade.findBy(targetEntity, mockBaseEntity);
    }
}
