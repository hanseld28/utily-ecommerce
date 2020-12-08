package br.com.utily.ecommerce.dao.domain.trade;

import br.com.utily.ecommerce.dao.IAssociativeDomainDAO;
import br.com.utily.ecommerce.dao.domain.IDateFilter;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITradeDAO extends IAssociativeDomainDAO<Trade>, IDateFilter<Trade> {

    @Query("SELECT 'trd.*', 'trp.*', 'sls.*', 'cst.*' FROM Trade trd " +
            "LEFT JOIN TradeItem trp on (trd.id=trp.id.tradeId) " +
            "LEFT JOIN Sale sls on (trd.sale.id=sls.id) " +
            "LEFT JOIN Customer cst on (sls.customer.id=cst.id) " +
            "WHERE cst.id=?1")
    List<Trade> findAllByCustomer(Customer customer);
}
