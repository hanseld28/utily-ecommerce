package br.com.utily.ecommerce.dao.domain.user.customer;

import br.com.utily.ecommerce.dao.IDomainDAO;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressDAO extends IDomainDAO<Address> {

    List<Address> findAllByCustomer(Customer customer);

    List<Address> findAllByCustomerAndInactivatedTrue(Customer customer);

    List<Address> findAllByCustomerAndInactivatedFalse(Customer customer);
}
