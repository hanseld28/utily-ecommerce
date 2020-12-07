package br.com.utily.ecommerce.helper.stock;

import br.com.utily.ecommerce.entity.domain.stock.Stock;
import br.com.utily.ecommerce.entity.domain.stock.StockHistory;
import br.com.utily.ecommerce.service.alternative.IAlternativeDomainService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class StockHistoryHelper {

    private final IAlternativeDomainService<StockHistory> alternativeDomainService;
    private ObjectProvider<StockHistory> stockHistoryObjectProvider;

    public StockHistoryHelper(@Qualifier("alternativeDomainService")
                              IAlternativeDomainService<StockHistory> alternativeDomainService,
                              ObjectProvider<StockHistory> stockHistoryObjectProvider) {
        this.alternativeDomainService = alternativeDomainService;
        this.stockHistoryObjectProvider = stockHistoryObjectProvider;
    }

    public StockHistory registerStockOperationOnHistory(Stock stock) {
        StockHistory stockHistory = stockHistoryObjectProvider.getObject();
        stockHistory.fillFieldsWith(stock);

        return alternativeDomainService.save(stockHistory);
    }
}
