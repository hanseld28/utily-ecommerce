package br.com.utily.ecommerce.helper.checkout;

import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.freight.Freight;
import br.com.utily.ecommerce.entity.domain.shop.sale.*;
import br.com.utily.ecommerce.entity.domain.shop.sale.progress.SaleInProgress;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.AddressType;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckoutHelper {

    private ObjectProvider<Sale> saleProvider;

    private final SaleItemHelper saleItemHelper;
    private final SaleAddressHelper saleAddressHelper;
    private final SaleCreditCardHelper saleCreditCardHelper;

    @Autowired
    public CheckoutHelper(ObjectProvider<Sale> saleProvider,
                          SaleItemHelper saleItemHelper,
                          SaleAddressHelper saleAddressHelper,
                          SaleCreditCardHelper saleCreditCardHelper) {
        this.saleProvider = saleProvider;
        this.saleItemHelper = saleItemHelper;
        this.saleAddressHelper = saleAddressHelper;
        this.saleCreditCardHelper = saleCreditCardHelper;
    }

    public Sale adapt(SaleInProgress saleInProgress) {
        Sale newSale = saleProvider.getObject();

        String identifyNumber = saleInProgress.getIdentifyNumber();
        ESaleStatus status = saleInProgress.getStatus();
        Customer customer = saleInProgress.getCustomer();
        List<CartItem> cartItems = saleInProgress.getCartItems();
        Freight freight = saleInProgress.getFreight();
        Voucher voucher = saleInProgress.getVoucher();

        List<SaleItem> saleItems = cartItems.stream()
                .map(cartItem -> saleItemHelper.adapt(cartItem, newSale))
                .collect(Collectors.toList());

        List<SaleAddress> saleAdresses = saleInProgress.getAdresses().stream()
                .map(address -> saleAddressHelper.adapt(address, newSale))
                .collect(Collectors.toList());

        List<SaleCreditCard> saleCreditCards = saleInProgress.getUsedCreditCards().stream()
                .map(creditCardValue -> saleCreditCardHelper.adapt(creditCardValue, newSale))
                .collect(Collectors.toList());

        freight.setSale(newSale);

        newSale.setIdentifyNumber(identifyNumber);
        newSale.setStatus(status);
        newSale.setCustomer(customer);
        newSale.setItems(saleItems);
        newSale.setAdresses(saleAdresses);
        newSale.setUsedCreditCards(saleCreditCards);
        newSale.setFreight(freight);
        newSale.setVoucher(voucher);

        return newSale;
    }

    public List<Address> filterAdressesByType(List<Address> adresses, AddressType filterType) {
        return adresses.stream()
                .filter(address -> address.getType().equals(filterType))
                .collect(Collectors.toList());
    }
}
