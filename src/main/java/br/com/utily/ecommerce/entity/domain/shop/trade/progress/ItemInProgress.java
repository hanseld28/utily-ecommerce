package br.com.utily.ecommerce.entity.domain.shop.trade.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemInProgress extends Entity {

    private Product product;
    private Integer orderAmount;
    private Integer amount;
    private String reason;
    private Boolean include;

    private ItemInProgress(Product product, Integer orderAmount) {
        this.product = product;
        this.orderAmount = orderAmount;
        this.amount = 1;
    }

    private ItemInProgress(Product product, Integer amount, String reason, Boolean include) {
        this.product = product;
        this.amount = amount;
        this.reason = reason;
        this.include = include;
    }

    public static ItemInProgress build(Product product, Integer orderAmount) {
        return new ItemInProgress(product, orderAmount);
    }

    public static ItemInProgress build(Product product, Integer amount, String reason, Boolean include) {
        return new ItemInProgress(product, amount, reason, include);
    }
}
