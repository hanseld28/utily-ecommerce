package br.com.utily.ecommerce.dao;

import br.com.utily.ecommerce.entity.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IDAO<T extends Entity> extends JpaRepository<T, Long> {

}
