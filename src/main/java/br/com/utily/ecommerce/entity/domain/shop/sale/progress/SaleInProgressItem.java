package br.com.utily.ecommerce.entity.domain.shop.sale.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor

@Getter
@Setter
public class SaleInProgressItem extends Entity {

    private Product product;
    private Integer quantity;
    private Double subtotal;

    public SaleInProgressItem(Product product, Integer quantity, Double subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public static SaleInProgressItem from(CartItem cartItem) {
        Product product = cartItem.getProduct();
        Integer quantity = cartItem.getAmount();
        Double subtotal = cartItem.getSubtotal();

        return new SaleInProgressItem(product, quantity, subtotal);
    }

}
