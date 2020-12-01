package br.com.utily.ecommerce.config.bean;

import br.com.utily.ecommerce.entity.domain.shop.sale.*;
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

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SaleAddress createPrototypeForSaleAddress() {
        return new SaleAddress();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SaleAddressId createPrototypeForSaleAddressId() {
        return new SaleAddressId();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SaleCreditCard createPrototypeForSaleCreditCard() {
        return new SaleCreditCard();
    }

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public SaleCreditCardId createPrototypeForSaleCreditCardId() {
        return new SaleCreditCardId();
    }

}
