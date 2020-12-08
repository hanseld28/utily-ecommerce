package br.com.utily.ecommerce.helper.trade;

import br.com.utily.ecommerce.controller.handler.exception.NotFoundException;
import br.com.utily.ecommerce.entity.domain.product.Product;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.shop.trade.item.TradeItem;
import br.com.utily.ecommerce.entity.domain.shop.trade.item.TradeItemId;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.ItemInProgress;
import br.com.utily.ecommerce.service.domain.IDomainService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TradeItemHelper {

    private final IDomainService<Product> productDomainService;
    private final Product mockProduct;

    private ObjectProvider<TradeItem> tradeItemProvider;
    private ObjectProvider<TradeItemId> tradeItemIdProvider;

    public TradeItemHelper(@Qualifier("domainService")
                           IDomainService<Product> productDomainService,
                           Product mockProduct,
                           ObjectProvider<TradeItem> tradeItemProvider,
                           ObjectProvider<TradeItemId> tradeItemIdProvider) {
        this.productDomainService = productDomainService;
        this.mockProduct = mockProduct;
        this.tradeItemProvider = tradeItemProvider;
        this.tradeItemIdProvider = tradeItemIdProvider;
    }

    public TradeItem adapt(ItemInProgress itemInProgress, Trade trade) {
        Long productId = itemInProgress.getProduct().getId();
        Optional<Product> productOptional = productDomainService.findById(productId, mockProduct);
        Product product = productOptional.orElseThrow(NotFoundException::new);
        Integer amount = itemInProgress.getAmount();
        String reason = itemInProgress.getReason();

        TradeItem tradeItem = tradeItemProvider.getObject();
        TradeItemId tradeItemId = tradeItemIdProvider.getObject();
        tradeItemId.setItemId(productId);
        tradeItemId.setRegisteredAt(LocalDateTime.now());

        tradeItem.setId(tradeItemId);
        tradeItem.setTrade(trade);
        tradeItem.setProduct(product);
        tradeItem.setQuantity(amount);
        tradeItem.setReason(reason);

        return tradeItem;
    }
}
