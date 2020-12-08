package br.com.utily.ecommerce.dao.domain.trade;

import br.com.utily.ecommerce.dao.IAssociativeDomainDAO;
import br.com.utily.ecommerce.dao.domain.IDateFilter;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITradeDAO extends IAssociativeDomainDAO<Trade>, IDateFilter<Trade> {

    List<Trade> findAllByOrderCustomer(Customer customer);
}
