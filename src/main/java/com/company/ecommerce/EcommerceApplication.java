package com.company.ecommerce;

import com.company.ecommerce.domain.cart.CartRepository;
import com.company.ecommerce.domain.countrytax.CountryTaxRepository;
import com.company.ecommerce.domain.countrytax.TaxCalculator;
import com.company.ecommerce.domain.customer.CustomerRepository;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import com.company.ecommerce.domain.product.ProductRepository;
import com.company.ecommerce.domain.purchaseorder.CheckOutService;
import com.company.ecommerce.domain.purchaseorder.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    @Configuration
    static class DomainServicesConfiguration {

        @Autowired
        private CountryTaxRepository countryTaxRepository;

        @Autowired
        private CustomerRepository customerRepository;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private PurchaseOrderRepository purchaseOrderRepository;

        @Autowired
        private CartRepository cartRepository;

        @Autowired
        private DomainEventPublisher domainEventPublisher;

        @Bean
        public TaxCalculator taxCalculator() {
            return new TaxCalculator(countryTaxRepository, customerRepository);
        }

        @Bean
        public CheckOutService checkOutService() {
            return new CheckOutService(productRepository, purchaseOrderRepository, domainEventPublisher, cartRepository);
        }
    }
}
