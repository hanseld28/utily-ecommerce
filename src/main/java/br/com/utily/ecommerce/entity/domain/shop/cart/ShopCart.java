package br.com.utily.ecommerce.entity.domain.shop.cart;

import br.com.utily.ecommerce.entity.Entity;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Getter

@Scope(
        scopeName = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class ShopCart extends Entity {

    private List<CartItem> items;
    private Integer itemsAmount;
    private Double total;

    public ShopCart() {
        items = new ArrayList<>();
        updateItemsAmountWithCurrentListSize();
    }

    public void addItem(CartItem cartItem) {
        boolean itemNotAdded = !existsItem(cartItem);

        if (itemNotAdded) {
            this.items.add(cartItem);
            updateProperties();
        }
    }

    public void removeItem(CartItem cartItem) {
        if (existsItem(cartItem)) {
            int i = items.size() - 1;

            for (; i > -1; i--) {
                if (areItemsTheSame(items.get(i), cartItem)) {
                    items.remove(i);
                    break;
                }
            }
            updateProperties();
        }
    }

    private Boolean existsItem(CartItem cartItem) {
        return this.items
                .stream()
                .anyMatch(existingCartItem -> areItemsTheSame(existingCartItem, cartItem));

    }

    private Boolean areItemsTheSame(CartItem one, CartItem two) {
        return one.getProduct().getId().equals(two.getProduct().getId());
    }

    public void updateItem(CartItem cartItem) {
        int index = this.items.indexOf(cartItem);

        if (index != -1) {
            CartItem existingCartItem = items.get(index);
            cartItem.setProduct(existingCartItem.getProduct());
            items.set(index, cartItem);
            calculateTotal();
        }
    }

    public void clear() {
        this.items.clear();
        this.total = 0.0d;
    }

    private void updateItemsAmountWithCurrentListSize() {
        itemsAmount = items.size();
    }

    private void calculateTotal() {
        Double total = 0.0d;
        for (CartItem cartItem : items) {
            total += cartItem.getSubtotal();
        }
        this.total = total;
    }

    private void updateProperties() {
        updateItemsAmountWithCurrentListSize();
        calculateTotal();
    }

}
