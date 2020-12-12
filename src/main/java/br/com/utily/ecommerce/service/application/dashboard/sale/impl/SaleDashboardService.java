package br.com.utily.ecommerce.service.application.dashboard.sale.impl;

import br.com.utily.ecommerce.dao.application.dashboard.sale.ISaleDashboardDAO;
import br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategory;
import br.com.utily.ecommerce.entity.application.dashboard.sale.SaleProductCategoryResult;
import br.com.utily.ecommerce.service.application.dashboard.sale.ISaleDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleDashboardService implements ISaleDashboardService {

    private final ISaleDashboardDAO saleDashboardDAO;

    @Autowired
    public SaleDashboardService(ISaleDashboardDAO saleDashboardDAO) {
        this.saleDashboardDAO = saleDashboardDAO;
    }

    @Override
    public Map<String, List<SaleProductCategory>> countSaleAndSumProductAmountByCategoriesBetweenInterval(LocalDateTime startDate, LocalDateTime endDate) {
        List<SaleProductCategoryResult> saleProductCategoriesResult = saleDashboardDAO.
                _countSaleAndSumProductAmountByCategoriesBetweenInterval(
                        startDate,
                        endDate.plusDays(1)
                );

        List<SaleProductCategory> saleProductCategories = saleProductCategoriesResult.stream()
                .map(saleProductCategoryResult -> new SaleProductCategory(
                        saleProductCategoryResult.getCategory(),
                        saleProductCategoryResult.getDate().toLocalDate(),
                        saleProductCategoryResult.getProductAmount()
                )).collect(Collectors.toList());

        List<SaleProductCategory> regrouped = new ArrayList<>();
        regrouped.add(saleProductCategories.get(0));

        int maxIndex = saleProductCategories.size() - 1;

        for (int i = 1; i < maxIndex; i++) {

            SaleProductCategory current = saleProductCategories.get(i);

            Optional<SaleProductCategory> anyEqualObjectOptional = regrouped.stream()
                    .filter(s -> s.getDate().compareTo(current.getDate()) == 0
                            && s.getCategory().equals(current.getCategory()))
                    .findFirst();

            if (anyEqualObjectOptional.isPresent()) {
                SaleProductCategory anyEqualObject = anyEqualObjectOptional.get();

                regrouped.remove(anyEqualObject);

                anyEqualObject.addProductAmount(current.getProductAmount());

                regrouped.add(anyEqualObject);
            } else {
                regrouped.add(current);
            }
        }

        Map<String, List<SaleProductCategory>> categorySalesProductGraph = new HashMap<>();

        for (SaleProductCategory saleProductCategory : regrouped) {
            String categoryName = saleProductCategory.getCategory();

            if (!categorySalesProductGraph.containsKey(categoryName)) {
                categorySalesProductGraph.put(categoryName, new ArrayList<>());
            }

            categorySalesProductGraph.get(categoryName)
                    .add(saleProductCategory);
        }

        return categorySalesProductGraph;
    }
}
