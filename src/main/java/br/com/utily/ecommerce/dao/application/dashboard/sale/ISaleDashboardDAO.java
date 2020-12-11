package br.com.utily.ecommerce.dao.application.dashboard.sale;

import br.com.utily.ecommerce.dao.application.dashboard.IDashboardDAO;
import br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategoryResult;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ISaleDashboardDAO extends IDashboardDAO<Sale> {

    @Query(value = "SELECT new br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategoryResult(" +
            "ctg.name, " +
            "sls.date, " +
            "SUM(slp.quantity), " +
            "COUNT(sls.id) " +
            ") FROM Sale AS sls " +
            "  INNER JOIN SaleItem AS slp ON slp.sale.id = sls.id" +
            "  INNER JOIN Product AS prt ON prt.id = slp.product.id" +
            "  INNER JOIN Category AS ctg ON ctg.id = prt.category.id " +
            "WHERE sls.date >= :startDate AND sls.date <= :endDate " +
            "GROUP BY sls.date, ctg.name " +
            "ORDER BY sls.date")
    List<SaleProductCategoryResult> _countSaleAndSumProductAmountByCategoriesBetweenInterval(@Param("startDate") LocalDateTime startDate,
                                                                                             @Param("endDate") LocalDateTime endDate);

}
