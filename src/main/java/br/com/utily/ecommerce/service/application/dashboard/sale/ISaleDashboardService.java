package br.com.utily.ecommerce.service.application.dashboard.sale;

import br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategory;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.service.application.dashboard.IDashboardService;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleDashboardService extends IDashboardService<Sale> {

    List<SaleProductCategory> countSaleAndSumProductAmountByCategoriesBetweenInterval(
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
