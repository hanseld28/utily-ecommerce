package br.com.utily.ecommerce.helper.voucher;

import br.com.utily.ecommerce.entity.domain.shop.voucher.EVoucherType;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucherId;
import br.com.utily.ecommerce.service.domain.associative.IAssociativeDomainService;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class VoucherHelper {

    private final IDomainService<Voucher> voucherDomainService;
    private final IAssociativeDomainService<CustomerVoucher> customerVoucherAssociativeDomainService;

    private ObjectProvider<Voucher> voucherProvider;
    private ObjectProvider<CustomerVoucher> customerVoucherProvider;
    private ObjectProvider<CustomerVoucherId> customerVoucherIdProvider;

    @Autowired
    public VoucherHelper(@Qualifier("domainService")
                         IDomainService<Voucher> voucherDomainService,
                         @Qualifier("associativeDomainService")
                         IAssociativeDomainService<CustomerVoucher> customerVoucherAssociativeDomainService,
                         ObjectProvider<Voucher> voucherProvider,
                         ObjectProvider<CustomerVoucher> customerVoucherProvider,
                         ObjectProvider<CustomerVoucherId> customerVoucherIdProvider) {
        this.voucherDomainService = voucherDomainService;
        this.customerVoucherAssociativeDomainService = customerVoucherAssociativeDomainService;
        this.voucherProvider = voucherProvider;
        this.customerVoucherProvider = customerVoucherProvider;
        this.customerVoucherIdProvider = customerVoucherIdProvider;
    }


    public Voucher adapt(Double value, EVoucherType type) {
        Voucher newVoucher = voucherProvider.getObject();

        if (type.equals(EVoucherType.TRADE)) {
            String name = "Cupom de " + type.getDisplayName() +
                    " R$ " + (int) Math.ceil(value);

            newVoucher.setName(name);
            newVoucher.setType(type);
            newVoucher.setValue(value);
            newVoucher.setMultiplicationFactor(1d);
        }

        return newVoucher;
    }

    public CustomerVoucher adapt(Customer customer, Voucher voucher) {
        CustomerVoucher newCustomerVoucher = customerVoucherProvider.getObject();
        CustomerVoucherId newCustomerVoucherId = customerVoucherIdProvider.getObject();

        newCustomerVoucherId.setCustomerId(customer.getId());
        newCustomerVoucherId.setVoucherId(voucher.getId());
        newCustomerVoucherId.setDate(LocalDateTime.now());

        newCustomerVoucher.setId(newCustomerVoucherId);

        newCustomerVoucher.setUsed(false);
        newCustomerVoucher.setCustomer(customer);
        newCustomerVoucher.setVoucher(voucher);

        return newCustomerVoucher;
    }

    public Voucher adaptAndSave(Double value, EVoucherType type) {
        Voucher voucher = adapt(value, type);

        return voucherDomainService.save(voucher);
    }

    public CustomerVoucher adaptAndSave(Customer customer, Voucher voucher) {
        CustomerVoucher customerVoucher = adapt(customer, voucher);

        return customerVoucherAssociativeDomainService.save(customerVoucher);
    }
}
