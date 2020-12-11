package br.com.utily.ecommerce.dao.domain.voucher;

import br.com.utily.ecommerce.dao.IAssociativeDomainDAO;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerVoucherDAO extends IAssociativeDomainDAO<CustomerVoucher> {

    List<CustomerVoucher> findCustomerVoucherByCustomer(Customer customer);

    List<CustomerVoucher> findCustomerVoucherByUsedFalseAndCustomer(Customer customer);

    Optional<CustomerVoucher> findByUsedFalseAndCustomerAndVoucher(Customer customer, Voucher voucher);

}
