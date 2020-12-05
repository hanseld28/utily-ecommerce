package br.com.utily.ecommerce.helper.checkout;

import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.*;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.AddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckoutHelper {

    private final Sale mockSale;

    private final SaleItemHelper saleItemHelper;
    private final SaleAddressHelper saleAddressHelper;
    private final SaleCreditCardHelper saleCreditCardHelper;

    @Autowired
    public CheckoutHelper(Sale mockSale,
                          SaleItemHelper saleItemHelper,
                          SaleAddressHelper saleAddressHelper,
                          SaleCreditCardHelper saleCreditCardHelper) {
        this.mockSale = mockSale;
        this.saleItemHelper = saleItemHelper;
        this.saleAddressHelper = saleAddressHelper;
        this.saleCreditCardHelper = saleCreditCardHelper;
    }

    public Sale adapt(SaleInProgress saleInProgress) {
        String identifyNumber = saleInProgress.getIdentifyNumber();
        ESaleStatus status = saleInProgress.getStatus();
        Customer customer = saleInProgress.getCustomer();
        List<CartItem> cartItems = saleInProgress.getCartItems();
        Voucher voucher = saleInProgress.getVoucher();

        List<SaleItem> saleItems = cartItems.stream()
                .map(cartItem -> saleItemHelper.adapt(cartItem, mockSale))
                .collect(Collectors.toList());

        List<SaleAddress> saleAdresses = saleInProgress.getAdresses().stream()
                .map(address -> saleAddressHelper.adapt(address, mockSale))
                .collect(Collectors.toList());

        List<SaleCreditCard> saleCreditCards = saleInProgress.getUsedCreditCards().stream()
                .map(creditCardValue -> saleCreditCardHelper.adapt(creditCardValue, mockSale))
                .collect(Collectors.toList());

        mockSale.setIdentifyNumber(identifyNumber);
        mockSale.setStatus(status);
        mockSale.setCustomer(customer);
        mockSale.setItems(saleItems);
        mockSale.setAdresses(saleAdresses);
        mockSale.setUsedCreditCards(saleCreditCards);
        mockSale.setVoucher(voucher);

        return mockSale;
    }

    public List<Address> filterAdressesByType(List<Address> adresses, AddressType filterType) {
        return adresses.stream()
                .filter(address -> address.getType().equals(filterType))
                .collect(Collectors.toList());
    }
}
