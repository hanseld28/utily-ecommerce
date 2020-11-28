package br.com.utily.ecommerce.helper.checkout;

import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.*;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.AddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckoutHelper {

    private final Sale mockSale;

    private final SaleAddress mockSaleAddress;
    private final SaleAddressId mockSaleAddressId;

    private final SaleItemHelper saleItemHelper;

    @Autowired
    public CheckoutHelper(Sale mockSale, SaleAddress mockSaleAddress,
                          SaleAddressId mockSaleAddressId, SaleItemHelper saleItemHelper) {
        this.mockSale = mockSale;
        this.mockSaleAddress = mockSaleAddress;
        this.mockSaleAddressId = mockSaleAddressId;
        this.saleItemHelper = saleItemHelper;
    }

    public Sale adapt(SaleInProgress saleInProgress) {
        String identifyNumber = saleInProgress.getIdentifyNumber();
        ESaleStatus status = saleInProgress.getStatus();
        Customer customer = saleInProgress.getCustomer();
        List<CartItem> cartItems = saleInProgress.getCartItems();

        List<SaleItem> saleItems = cartItems.stream()
                .map(cartItem -> saleItemHelper.adapt(cartItem, mockSale))
                .collect(Collectors.toList());

        Address address = saleInProgress.getAdresses().get(0);

        SaleAddress saleAddress = mockSaleAddress.adapt(mockSaleAddressId, mockSale, address);
        List<SaleAddress> saleAdresses = Collections.singletonList(saleAddress);

        mockSale.setIdentifyNumber(identifyNumber);
        mockSale.setStatus(status);
        mockSale.setCustomer(customer);
        mockSale.setItems(saleItems);
        mockSale.setAdresses(saleAdresses);

        return mockSale;
    }

    public boolean isItNeedsOneMoreAddress(Address address) {
        return !address.isShippingAndBilling();
    }

    public AddressType whichAddressTypeIsMissing(Address address) {
        return address.isShipping() ? AddressType.BILLING : AddressType.SHIPPING;
    }

    public List<Address> filterAdressesByType(List<Address> adresses, AddressType filterType) {
        return adresses.stream()
                .filter(address -> address.getType().equals(filterType))
                .collect(Collectors.toList());
    }
}
