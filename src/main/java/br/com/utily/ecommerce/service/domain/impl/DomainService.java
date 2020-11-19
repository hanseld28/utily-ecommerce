package br.com.utily.ecommerce.service.domain.impl;

import br.com.utily.ecommerce.entity.domain.DomainEntity;
import br.com.utily.ecommerce.facade.IFacade;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public Collection<T> findAll(T mockEntity) {
        return facade.findAll(mockEntity);
    }

    @Override
    public Optional<T> findById(Long id, T mockEntity) {
        return facade.findById(id, mockEntity);
    }
}
