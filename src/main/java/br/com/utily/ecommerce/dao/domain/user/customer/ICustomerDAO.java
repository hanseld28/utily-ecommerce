package br.com.utily.ecommerce.dao.domain.user.customer;

import br.com.utily.ecommerce.dao.IDomainDAO;
import br.com.utily.ecommerce.entity.domain.user.User;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerDAO extends IDomainDAO<Customer> {

    Optional<Customer> findCustomerByUser(User user);

    Optional<Customer> findCustomerByUserAndInactivatedFalse(User user);

    Optional<Customer> findCustomerByUserAndInactivatedTrue(User user);
}
