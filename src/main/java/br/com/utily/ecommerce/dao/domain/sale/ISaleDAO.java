package br.com.utily.ecommerce.dao.domain.sale;

import br.com.utily.ecommerce.dao.IAssociativeDomainDAO;
import br.com.utily.ecommerce.dao.domain.IDateFilter;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaleDAO extends IAssociativeDomainDAO<Sale>, IDateFilter<Sale> {

    List<Sale> findAllByCustomer(Customer customer);
}
