package br.com.utily.ecommerce.dao.domain.voucher;

import br.com.utily.ecommerce.dao.IAlternativeDomainDAO;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerVoucherDAO extends IAlternativeDomainDAO<CustomerVoucher> {

    List<CustomerVoucher> findCustomerVoucherByCustomer(Customer customer);
}
