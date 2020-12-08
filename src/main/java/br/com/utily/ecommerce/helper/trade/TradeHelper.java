package br.com.utily.ecommerce.helper.trade;

import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.shop.trade.ETradeType;
import br.com.utily.ecommerce.entity.domain.shop.trade.Trade;
import br.com.utily.ecommerce.entity.domain.shop.trade.item.TradeItem;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.ItemInProgress;
import br.com.utily.ecommerce.entity.domain.shop.trade.progress.TradeInProgress;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TradeHelper {

    private final ObjectProvider<Trade> tradeProvider;
    private final TradeItemHelper tradeItemHelper;

    @Autowired
    public TradeHelper(ObjectProvider<Trade> tradeProvider, TradeItemHelper tradeItemHelper) {
        this.tradeProvider = tradeProvider;
        this.tradeItemHelper = tradeItemHelper;
    }

    public Trade adapt(TradeInProgress tradeInProgress) {
        Trade newTrade = tradeProvider.getObject();

        Sale order = tradeInProgress.getOrder();
        ETradeType tradeType = tradeInProgress.getType();
        List<ItemInProgress> tradeItemsInProgress = tradeInProgress.getItems()
                .stream()
                .filter(ItemInProgress::isInclude)
                .collect(Collectors.toList());

        List<TradeItem> tradeItems = tradeItemsInProgress.stream()
                .map(itemInProgress -> tradeItemHelper.adapt(itemInProgress, newTrade))
                .collect(Collectors.toList());

        newTrade.setOrder(order);
        newTrade.setItems(tradeItems);
        newTrade.setType(tradeType);
        newTrade.setItems(tradeItems);

        return newTrade;
    }
}
