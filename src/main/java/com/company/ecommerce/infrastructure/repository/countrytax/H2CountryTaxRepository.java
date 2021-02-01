package com.company.ecommerce.infrastructure.repository.countrytax;

import com.company.ecommerce.domain.countrytax.CountryTax;
import com.company.ecommerce.domain.countrytax.CountryTaxId;
import com.company.ecommerce.domain.countrytax.CountryTaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2CountryTaxRepository implements CountryTaxRepository {

    @Autowired
    private HibernateCountryTaxRepository repository;

    @Override
    public CountryTax save(CountryTax countryTax) {
        return repository.save(countryTax);
    }

    @Override
    public void remove(CountryTaxId countryTaxId) {
        repository.deleteById(countryTaxId);
    }

    @Override
    public List<CountryTax> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<CountryTax> findById(CountryTaxId countryTaxId) {
        return repository.findById(countryTaxId);
    }
}
