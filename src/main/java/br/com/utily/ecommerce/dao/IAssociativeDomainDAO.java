package br.com.utily.ecommerce.dao;

import br.com.utily.ecommerce.entity.Entity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IAssociativeDomainDAO<T extends Entity> extends IDAO<T> {
    
}
