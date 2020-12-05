package br.com.utily.ecommerce.dao.domain.voucher;

import br.com.utily.ecommerce.dao.IAlternativeDomainDAO;
import br.com.utily.ecommerce.entity.domain.shop.voucher.Voucher;
import org.springframework.stereotype.Repository;

@Repository
public interface IVoucherDAO extends IAlternativeDomainDAO<Voucher> {

}
