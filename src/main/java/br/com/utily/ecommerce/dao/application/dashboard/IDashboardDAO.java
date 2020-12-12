package br.com.utily.ecommerce.dao.application.dashboard;

import br.com.utily.ecommerce.dao.IDAO;
import br.com.utily.ecommerce.entity.Entity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IDashboardDAO<T extends Entity> extends IDAO<T> {
}
