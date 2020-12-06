package br.com.utily.ecommerce.dao.domain.stock;

import br.com.utily.ecommerce.dao.IAlternativeDomainDAO;
import br.com.utily.ecommerce.dao.domain.IDateFilter;
import br.com.utily.ecommerce.entity.domain.stock.StockHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface IStockHistoryDAO extends IAlternativeDomainDAO<StockHistory>, IDateFilter<StockHistory> {

}
