package br.com.utily.ecommerce.dao.domain.sale;

import br.com.utily.ecommerce.dao.IAssociativeDomainDAO;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleItemDAO extends IAssociativeDomainDAO<SaleItem> {

}
