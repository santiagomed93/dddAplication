package com.company.ecommerce.domain.countrytax;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.customer.CustomerRepository;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TaxCalculatorTest {

    @TestConfiguration
    static class TaxCalculatorTestContextConfiguration {

        @Bean
        public TaxCalculator taxCalculator() {
            return new TaxCalculator(countryTaxRepository(), customerRepository());
        }

        @Bean
        public CountryTaxRepository countryTaxRepository() {
            return Mockito.mock(CountryTaxRepository.class);
        }

        @Bean
        public CustomerRepository customerRepository() {
            return Mockito.mock(CustomerRepository.class);
        }
    }

    @Autowired
    private CountryTaxRepository taxRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TaxCalculator service;

    private CountryTaxId businessCountryTaxId;

    private CountryTaxId customerCountryTaxId;

    @Before
    public void setUp() {
        businessCountryTaxId = new CountryTaxId(1001L, TaxType.BUSINESS);
        customerCountryTaxId = new CountryTaxId(1L, TaxType.CUSTOMER);
        Customer defaultCustomer = TestDomainObjectsFactory.createDefaultCustomer();
        Mockito.when(customerRepository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.of(defaultCustomer));
    }

    @Test
    public void whenCalculatingTaxWithBusinessTaxAndCustomerTax_thenProperTaxShouldBeCalculated() {
        CountryTax businessCountryTax = TestDomainObjectsFactory.createCountryTaxWithCustomTaxType(TaxType.BUSINESS);
        CountryTax customerCountryTax = TestDomainObjectsFactory.createCountryTaxWithCustomTaxType(TaxType.CUSTOMER);
        Mockito.when(taxRepository.findById(businessCountryTaxId)).thenReturn(Optional.of(businessCountryTax));
        Mockito.when(taxRepository.findById(customerCountryTaxId)).thenReturn(Optional.of(customerCountryTax));
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        float tax = service.calculateTax(product, new CustomerId("1"));

        assertThat(tax).isEqualTo(10);
    }

    @Test
    public void whenCalculatingTaxWithBusinessTaxAndWithoutCustomerTax_thenProperTaxShouldBeCalculated() {
        CountryTax businessCountryTax = TestDomainObjectsFactory.createCountryTaxWithCustomTaxType(TaxType.BUSINESS);
        Mockito.when(taxRepository.findById(businessCountryTaxId)).thenReturn(Optional.of(businessCountryTax));
        Mockito.when(taxRepository.findById(customerCountryTaxId)).thenReturn(Optional.empty());
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        float tax = service.calculateTax(product, new CustomerId("1"));

        assertThat(tax).isEqualTo(5);
    }

    @Test
    public void whenCalculatingTaxWithoutBusinessTaxAndWithCustomerTax_thenProperTaxShouldBeCalculated() {
        CountryTax customerCountryTax = TestDomainObjectsFactory.createCountryTaxWithCustomTaxType(TaxType.CUSTOMER);
        Mockito.when(taxRepository.findById(businessCountryTaxId)).thenReturn(Optional.empty());
        Mockito.when(taxRepository.findById(customerCountryTaxId)).thenReturn(Optional.of(customerCountryTax));
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        float tax = service.calculateTax(product, new CustomerId("1"));

        assertThat(tax).isEqualTo(5);
    }

    @Test
    public void whenCalculatingTaxWithoutBusinessTaxAndWithoutCustomerTax_thenProperTaxShouldBeCalculated() {
        Mockito.when(taxRepository.findById(businessCountryTaxId)).thenReturn(Optional.empty());
        Mockito.when(taxRepository.findById(customerCountryTaxId)).thenReturn(Optional.empty());
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        float tax = service.calculateTax(product, new CustomerId("1"));

        assertThat(tax).isEqualTo(0);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenCalculatingTaxForCustomerThatDoesNotExist() {
        Mockito.when(customerRepository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());
        Product product = TestDomainObjectsFactory.createDefaultProduct();

        service.calculateTax(product, new CustomerId("1"));
    }

}