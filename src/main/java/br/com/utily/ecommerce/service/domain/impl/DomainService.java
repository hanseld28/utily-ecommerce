package br.com.utily.ecommerce.service.domain.impl;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.facade.IFacade;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DomainService<T extends DomainEntity> implements IDomainService<T> {

    private IFacade<T> facade;

    @Autowired
    private void setFacade(IFacade<T> facade) {
        this.facade = facade;
    }

    @Override
    public T save(T domainEntity) {
        return facade.save(domainEntity);
    }

    @Override
    public T saveAndFlush(T domainEntity) {
        return facade.saveAndFlush(domainEntity);
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

    @Override
    public Optional<T> findActivatedById(Long id, Entity entity) {
        return facade.findActivatedById(id, entity);
    }

    @Override
    public Optional<T> findInactivatedById(Long id, Entity entity) {
        return facade.findInactivatedById(id, entity);
    }

    @Override
    public List<T> findAllActivatedBy(T entity) {
        return facade.findAllActivatedBy(entity);
    }

    @Override
    public List<T> findAllInactivatedBy(T entity) {
        return facade.findAllInactivatedBy(entity);
    }

}
