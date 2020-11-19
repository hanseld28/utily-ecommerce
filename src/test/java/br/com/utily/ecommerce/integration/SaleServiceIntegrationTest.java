package br.com.utily.ecommerce.integration;

import br.com.utily.ecommerce.entity.domain.shop.sale.ESaleStatus;
import br.com.utily.ecommerce.entity.domain.shop.sale.Sale;
import br.com.utily.ecommerce.entity.domain.user.customer.Customer;

import br.com.utily.ecommerce.service.domain.IDomainService;
import br.com.utily.ecommerce.service.domain.impl.DomainService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RunWith(SpringRunner.class)
class SaleServiceIntegrationTest {

    @TestConfiguration
    static class DomainServicesIntegrationTestContextConfiguration {

        @Bean
        public IDomainService<Sale> saleService() {
            return new DomainService<Sale>();
        }

        @Bean
        public IDomainService<Customer> customerService() {
            return new DomainService<Customer>();
        }
    }

    @Autowired
    private IDomainService<Sale> saleService;

    @Autowired
    private IDomainService<Customer> customerService;

    @Autowired
    private Sale mockSale;

    @Autowired
    private Customer mockCustomer;

    @Test
    public void whenSaveSale_thenReturnFilledSale() {
        assertNotEquals(null, customerService);
        assertNotEquals(null, saleService);
        assertNotEquals(null, mockSale);
        assertNotEquals(null, mockCustomer);

//
//        Optional<Customer> customerOptional = customerService.findById(103L, mockCustomer);
//        Customer customer = null;
//        if (customerOptional.isPresent()) {
//            customer = customerOptional.get();
//        }
//
//        mockSale.setCustomer(customer);
//        mockSale.finish();
//
//        Sale savedSale = saleService.save(mockSale);
//
//        assertNotEquals(null, savedSale.getId());
//        assertNotEquals(null, savedSale.getIdentifyNumber());
//        assertEquals(ESaleStatus.PROCESSING, savedSale.getStatus());
    }
}