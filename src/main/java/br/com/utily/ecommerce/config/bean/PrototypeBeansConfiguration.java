package br.com.utily.ecommerce.config.bean;

import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItem;
import br.com.utily.ecommerce.entity.domain.shop.sale.SaleItemId;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PrototypeBeansConfiguration {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SaleItem createPrototypeForSaleItem() {
        return new SaleItem();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SaleItemId createPrototypeForSaleItemId() {
        return new SaleItemId();
    }

}
