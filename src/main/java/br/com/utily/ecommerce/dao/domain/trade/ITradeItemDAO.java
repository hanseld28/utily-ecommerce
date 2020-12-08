package br.com.utily.ecommerce.dao.domain.trade;

import br.com.utily.ecommerce.dao.IAssociativeDomainDAO;
import br.com.utily.ecommerce.entity.domain.shop.trade.item.TradeItem;
import org.springframework.stereotype.Repository;

@Repository
public interface ITradeItemDAO extends IAssociativeDomainDAO<TradeItem> {

}
