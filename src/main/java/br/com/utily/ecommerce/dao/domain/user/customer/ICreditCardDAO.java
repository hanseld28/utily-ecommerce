package br.com.utily.ecommerce.dao.domain.user.customer;

import br.com.utily.ecommerce.dao.IDomainDAO;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.creditCard.CreditCard;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICreditCardDAO extends IDomainDAO<CreditCard> {

    List<CreditCard> findAllByCustomer(Customer customer);

    List<CreditCard> findAllByCustomerAndInactivatedTrue(Customer customer);

    List<CreditCard> findAllByCustomerAndInactivatedFalse(Customer customer);

}
