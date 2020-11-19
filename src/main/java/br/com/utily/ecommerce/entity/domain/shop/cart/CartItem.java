package br.com.utily.ecommerce.entity.domain.shop.cart;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Setter

@Component
public class CartItem extends Entity {

    private Long id;
    private Product product;
    private Integer amount;
    private Double subtotal;

    public CartItem() {
        amount = 1;
    }

    public void setProduct(Product product) {
        this.product = product;
        id = product.getId();
        calculateSubtotalIfPermitted();
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        calculateSubtotalIfPermitted();
    }

    private void calculateSubtotalIfPermitted() {
        if (subtotalIsCalculable()) {
            calculateSubtotal();
        }
    }

    private Boolean subtotalIsCalculable() {
        return (product != null) && (product.getPrice() != null);
    }

    private void calculateSubtotal() {
        this.subtotal = product.getPrice() * amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
