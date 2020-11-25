package br.com.utily.ecommerce.helper.entity;

import br.com.utily.ecommerce.entity.domain.shop.sale.ESaleStatus;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleAddress;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleAddressId;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import org.modelmapper.internal.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SaleEntityHelper {

    private final Sale mockSale;

    private final SaleAddress mockSaleAddress;
    private final SaleAddressId mockSaleAddressId;

    @Autowired
    public SaleEntityHelper(Sale mockSale, SaleAddress mockSaleAddress, SaleAddressId mockSaleAddressId) {
        this.mockSale = mockSale;
        this.mockSaleAddress = mockSaleAddress;
        this.mockSaleAddressId = mockSaleAddressId;
    }

    public Sale adapt(SaleInProgress saleInProgress) {
        String identifyNumber = saleInProgress.getIdentifyNumber();
        ESaleStatus status = saleInProgress.getStatus();
        Customer customer = saleInProgress.getCustomer();

        Address address = saleInProgress.getAdresses().get(0);
        SaleAddress saleAddress = mockSaleAddress.adapt(mockSaleAddressId, address);
        List<SaleAddress> saleAdresses = Collections.singletonList(saleAddress);

        mockSale.setIdentifyNumber(identifyNumber);
        mockSale.setStatus(status);
        mockSale.setCustomer(customer);
        mockSale.setAdresses(saleAdresses);

        return mockSale;
    }
}
