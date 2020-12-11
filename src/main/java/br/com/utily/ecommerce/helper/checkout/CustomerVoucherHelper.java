package br.com.utily.ecommerce.helper.checkout;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucher;
import br.com.utily.ecommerce.entity.domain.user.customer.voucher.CustomerVoucherId;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerVoucherHelper {

    private final IDomainService<Voucher> domainService;
    private final Voucher mockVoucher;

    private ObjectProvider<CustomerVoucher> customerVoucherObjectProvider;
    private ObjectProvider<CustomerVoucherId> customerVoucherIdObjectProvider;

    @Autowired
    public CustomerVoucherHelper(@Qualifier("domainService")
                                 IDomainService<Voucher> domainService,
                                 Voucher mockVoucher,
                                 ObjectProvider<CustomerVoucher> customerVoucherObjectProvider,
                                 ObjectProvider<CustomerVoucherId> customerVoucherIdObjectProvider) {
        this.domainService = domainService;
        this.mockVoucher = mockVoucher;
        this.customerVoucherObjectProvider = customerVoucherObjectProvider;
        this.customerVoucherIdObjectProvider = customerVoucherIdObjectProvider;
    }

    public CustomerVoucher adapt(Voucher voucher, Customer customer) {
        Long voucherId = voucher.getId();
        Optional<Voucher> voucherOptional = domainService.findById(voucherId, mockVoucher);
        Voucher voucherToSave = voucherOptional.orElseThrow(NotFoundException::new);

        CustomerVoucher customerVoucher = customerVoucherObjectProvider.getObject();
        CustomerVoucherId customerVoucherId = customerVoucherIdObjectProvider.getObject();

        return customerVoucher.adapt(customerVoucherId, customer, voucherToSave);
    }

    public CustomerVoucher provideNewObject() {
        return customerVoucherObjectProvider.getObject();
    }
}
