package br.com.utily.ecommerce.entity.domain.shop.trade.progress;

import br.com.utily.ecommerce.entity.Entity;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.trade.ETradeStatus;
import br.com.utily.ecommerce.entity.domain.shop.trade.ETradeType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter

@Scope(
        scopeName = WebApplicationContext.SCOPE_SESSION,
        proxyMode = ScopedProxyMode.TARGET_CLASS
)
@Component
public class TradeInProgress extends Entity {

    private ETradeType type;
    private ETradeStatus status;
    private Sale sale;
    private List<ItemInProgress> items;

    public void updateItem(ItemInProgress itemInProgress) {
        Long itemId = itemInProgress.getProduct().getId();
        Integer tradeAmount = itemInProgress.getAmount();
        String reason = itemInProgress.getReason();
        Boolean include = itemInProgress.getInclude();

        ItemInProgress existingItem = findItemById(itemId);

        if (existingItem != null) {
            int index = items.indexOf(existingItem);
            existingItem.setAmount(tradeAmount);
            existingItem.setReason(reason);
            existingItem.setInclude(include);

            items.set(index, existingItem);
        }
    }

    private ItemInProgress findItemById(Long itemId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(itemId))
                .limit(1)
                .collect(Collectors.toList())
                .get(0);
    }

}
