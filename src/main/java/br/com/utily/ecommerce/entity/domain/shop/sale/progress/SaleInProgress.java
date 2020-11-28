package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.ESaleStatus;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;
import br.com.utily.ecommerce.entity.domain.user.customer.adresses.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

@Scope(
        scopeName = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
@Component
public class SaleInProgress extends Entity {

    private String identifyNumber;
    private ESaleStatus status;
    private Customer customer;
    private List<Address> adresses = new ArrayList<>();
    private List<CartItem> cartItems = new ArrayList<>();

    public void addAddress(Address address) {
        adresses.add(address);
    }

    private void generateIdentifyNumber() {
        String millis = String.valueOf(System.currentTimeMillis());
        StringBuilder invertedMillis = new StringBuilder();

        String uniqueCode = (customer != null && customer.getId() != null)
                ? customer.getId().toString()
                : String.valueOf(this.hashCode());

        invertedMillis.append(uniqueCode);

        for (int i = millis.length() - 1; i >= 0; i--) {
            invertedMillis.append(millis.charAt(i));
        }

        identifyNumber = invertedMillis.toString();
    }

    public void putStatusToProcessing() {
        this.status = ESaleStatus.PROCESSING;
    }

    public void changeStatusToInTransit() {
        this.status = ESaleStatus.IN_TRANSIT;
    }

    public void changeStatusToDelivered() {
        this.status = ESaleStatus.DELIVERED;
    }

    public void finish() {
        generateIdentifyNumber();
        putStatusToProcessing();
    }
}
