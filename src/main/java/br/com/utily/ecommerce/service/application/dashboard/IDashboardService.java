package br.com.utily.ecommerce.service.application.dashboard;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.service.IService;
import org.springframework.stereotype.Component;

@Component
public interface IDashboardService<T extends Entity> extends IService<T> {

}
