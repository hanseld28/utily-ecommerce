package br.com.utily.ecommerce.helper.stock;

import br.com.utily.ecommerce.entity.domain.stock.Stock;
import br.com.utily.ecommerce.entity.domain.stock.StockHistory;
import br.com.utily.ecommerce.service.alternative.IAlternativeDomainService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class StockHistoryHelper {

    private final IAlternativeDomainService<StockHistory> alternativeDomainService;
    private final StockHistory stockHistory;

    public StockHistoryHelper(@Qualifier("alternativeDomainService")
                              IAlternativeDomainService<StockHistory> alternativeDomainService,
                              StockHistory stockHistory) {
        this.alternativeDomainService = alternativeDomainService;
        this.stockHistory = stockHistory;
    }

    public void registerStockOperationOnHistory(Stock stock) {
        stockHistory.fillFieldsWith(stock);
        StockHistory registeredStockHistory = alternativeDomainService.save(stockHistory);

        if (registeredStockHistory.getId() != null) {
            System.out.println("Stock operation for product '"
                    + registeredStockHistory.getStock().getProduct().getTitle()
                    + "' with [" + registeredStockHistory.getAmount()
                    + "] amount has been registered at "
                    + registeredStockHistory.getDate().format(DateTimeFormatter.ISO_DATE_TIME) + " on history.");
        }
    }
}
