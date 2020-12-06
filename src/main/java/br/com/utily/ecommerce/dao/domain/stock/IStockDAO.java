package br.com.utily.ecommerce.dao.domain.stock;

import br.com.utily.ecommerce.dao.IAlternativeDomainDAO;
import br.com.utily.ecommerce.entity.domain.stock.Stock;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockDAO extends IAlternativeDomainDAO<Stock> {

}
