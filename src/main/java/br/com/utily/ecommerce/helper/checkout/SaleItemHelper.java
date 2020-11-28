package br.com.utily.ecommerce.helper.checkout;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.cart.CartItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItemId;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SaleItemHelper {

    private final IDomainService<Product> productDomainService;
    private final Product mockProduct;

    private ObjectProvider<SaleItem> saleItemProvider;
    private ObjectProvider<SaleItemId> saleItemIdProvider;

    @Autowired
    public SaleItemHelper(@Qualifier("domainService") IDomainService<Product> productDomainService,
                          Product mockProduct,
                          ObjectProvider<SaleItem> saleItemProvider,
                          ObjectProvider<SaleItemId> saleItemIdProvider) {
        this.productDomainService = productDomainService;
        this.mockProduct = mockProduct;
        this.saleItemProvider = saleItemProvider;
        this.saleItemIdProvider = saleItemIdProvider;
    }

    public SaleItem adapt(CartItem cartItem, Sale sale) {
        Long productId = cartItem.getProduct().getId();
        Optional<Product> productOptional = productDomainService.findById(productId, mockProduct);
        Product product = productOptional.orElseThrow(NotFoundException::new);
        Integer amount = cartItem.getAmount();
        Double subtotal = cartItem.getSubtotal();

        SaleItem saleItem = saleItemProvider.getObject();
        SaleItemId saleItemId = saleItemIdProvider.getObject();
        saleItemId.setItemId(productId);

        saleItem.setId(saleItemId);
        saleItem.setSale(sale);
        saleItem.setProduct(product);
        saleItem.setQuantity(amount);
        saleItem.setSubtotal(subtotal);

        return saleItem;
    }
}
