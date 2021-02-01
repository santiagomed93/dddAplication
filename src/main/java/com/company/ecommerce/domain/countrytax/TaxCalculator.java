package com.company.ecommerce.domain.countrytax;

import com.company.ecommerce.domain.DomainService;
import com.company.ecommerce.domain.Settings;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.customer.CustomerRepository;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import com.company.ecommerce.domain.product.Product;

import java.util.Optional;

public class TaxCalculator implements DomainService {

    private CountryTaxRepository taxRepository;

    private CustomerRepository customerRepository;

    public TaxCalculator(CountryTaxRepository taxRepository, CustomerRepository customerRepository) {
        this.taxRepository = taxRepository;
        this.customerRepository = customerRepository;
    }

    public float calculateTax(Product product, CustomerId id) {
        Customer customer = nonNullCustomer(id);
        CountryTax businessTax = nonNullCountryTax(customer.country().id(), TaxType.CUSTOMER);
        CountryTax customerTax = nonNullCountryTax(Settings.BUSINESS_COUNTRY, TaxType.BUSINESS);
        return calculateTotalTax(product.price(), businessTax.percentage(), customerTax.percentage());
    }

    private float calculateTotalTax(float price, float businessTax, float customerTax) {
        float totalBusinessTax = price * businessTax / 100;
        float totalCustomerTax = price * customerTax / 100;
        return totalBusinessTax + totalCustomerTax;
    }

    private CountryTax nonNullCountryTax(long countryId, TaxType taxType) {
        CountryTaxId countryTaxId = new CountryTaxId(countryId, taxType);
        Optional<CountryTax> tax = taxRepository.findById(countryTaxId);
        return tax.orElse(new NullCountryTax(countryTaxId));
    }

    private Customer nonNullCustomer(CustomerId customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Customer with id %s does not exist", customerId));
        });
    }
}
