package br.com.utily.ecommerce.entity.domain.shop.sale;

import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@ToString

@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter

@Entity
@Table(name = "sales_products")
public class SaleItem extends br.com.utily.ecommerce.entity.Entity implements Serializable {

    @EmbeddedId
    private SaleItemId id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @MapsId("itemId")
    @JoinColumn(name = "slp_prt_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("saleId")
    @JoinColumn(name = "slp_sls_id")
    private Sale sale;

    @Basic
    @Column(name = "slp_quantity")
    private Integer quantity;

    @Basic
    @Column(name = "slp_subtotal")
    private Double subtotal;

    public SaleItem(SaleItemId saleItemId, Product product, Integer quantity, Double subtotal) {
        id = saleItemId;
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    public static SaleItem from(CartItem cartItem) {
        Product product = cartItem.getProduct();
        Integer quantity = cartItem.getAmount();
        Double subtotal = cartItem.getSubtotal();

        SaleItemId partOfsaleItemId = new SaleItemId();
        partOfsaleItemId.setItemId(product.getId());

        return new SaleItem(partOfsaleItemId, product, quantity, subtotal);
    }

    public void setProduct(Product product) {
        SaleItemId saleItemId = new SaleItemId();
        saleItemId.setItemId(product.getId());

        this.id = saleItemId;
        this.product = product;
    }
}
