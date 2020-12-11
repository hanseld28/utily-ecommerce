package br.com.utily.ecommerce.controller.application.dashboard.sale;

import br.com.utily.ecommerce.dto.application.dashboard.sale.SaleIntervalDTO;
import br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategory;
import br.com.utily.ecommerce.service.application.dashboard.sale.ISaleDashboardService;
import br.com.utily.ecommerce.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SaleDashboardController {

    public static final String SALES_DASHBOARD_BASE_URL = "/dashboard/sales";
    private final String SALES_PRODUCTS_BY_CATEGORY_BETWEEN_INTERNAL_URL = "/products/categories/interval";

    private final ISaleDashboardService saleDashboardService;

    @Autowired
    public SaleDashboardController(ISaleDashboardService saleDashboardService) {
        this.saleDashboardService = saleDashboardService;
    }

    @PostMapping(path = SALES_DASHBOARD_BASE_URL + SALES_PRODUCTS_BY_CATEGORY_BETWEEN_INTERNAL_URL)
    public ResponseEntity<?> countSalesAndSumProductsByCategoryBetween(@RequestBody SaleIntervalDTO saleIntervalDTO) {
        String plainStartDate = saleIntervalDTO.getStartDate();
        String plainEndDate = saleIntervalDTO.getEndDate();

        LocalDateTime startDate = DateUtil.from(plainStartDate);
        LocalDateTime endDate = DateUtil.from(plainEndDate);


        List<SaleProductCategory> saleProductCategoryResultCasesByInterval = saleDashboardService
                .countSaleAndSumProductAmountByCategoriesBetweenInterval(
                        startDate,
                        endDate
                );

        return ResponseEntity.ok(saleProductCategoryResultCasesByInterval);
    }
}
