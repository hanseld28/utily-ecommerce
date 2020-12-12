package br.com.utily.ecommerce.controller.application.dashboard.sale;

import br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategory;
import br.com.utily.ecommerce.service.application.dashboard.sale.ISaleDashboardService;
import br.com.utily.ecommerce.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class SaleDashboardController {

    public static final String SALES_DASHBOARD_BASE_URL = "/dashboard/sales";
    private final String SALES_PRODUCTS_BY_CATEGORY_BETWEEN_INTERNAL_URL = "/products/categories/interval";

    private final ISaleDashboardService saleDashboardService;

    @Autowired
    public SaleDashboardController(ISaleDashboardService saleDashboardService) {
        this.saleDashboardService = saleDashboardService;
    }

    @GetMapping(path = SALES_DASHBOARD_BASE_URL + SALES_PRODUCTS_BY_CATEGORY_BETWEEN_INTERNAL_URL)
    public ResponseEntity<?> countSalesAndSumProductsByCategoryBetween(@RequestParam("startDate") String plainStartDate, @RequestParam("endDate") String plainEndDate) {
        LocalDateTime startDate = DateUtil.from(plainStartDate);
        LocalDateTime endDate = DateUtil.from(plainEndDate);

        Map<String, List<SaleProductCategory>> saleProductCategoryResultCasesByInterval = saleDashboardService
                .countSaleAndSumProductAmountByCategoriesBetweenInterval(
                        startDate,
                        endDate
                );

        return ResponseEntity.ok(saleProductCategoryResultCasesByInterval);
    }
}
