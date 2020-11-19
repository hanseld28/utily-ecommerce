package br.com.utily.ecommerce.dao;

import br.com.utily.ecommerce.entity.Entity;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface IDomainDAO<T extends Entity> extends IDAO<T> {

    Optional<T> findByIdAndInactivatedFalse(Long id);

    Optional<T> findByIdAndInactivatedTrue(Long id);

    List<T> findAllByInactivatedFalse();

    List<T> findAllByInactivatedTrue();

}
