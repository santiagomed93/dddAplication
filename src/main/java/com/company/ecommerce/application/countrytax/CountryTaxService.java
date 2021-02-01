package com.company.ecommerce.application.countrytax;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.countrytax.CountryTax;
import com.company.ecommerce.domain.countrytax.CountryTaxId;
import com.company.ecommerce.domain.countrytax.CountryTaxRepository;
import com.company.ecommerce.domain.countrytax.TaxType;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryTaxService {

    @Autowired
    private CountryTaxRepository repository;

    @Transactional
    public void createCountryTax(long countryId, TaxType taxType, float percentage) {
        CountryTaxId countryTaxId = createCountryTaxId(countryId, taxType);
        CountryTax countryTax = new CountryTax(countryTaxId, percentage);
        repository.save(countryTax);
    }

    @Transactional
    public void deleteCountryTax(long countryId, TaxType taxType) {
        CountryTaxId countryTaxId = createCountryTaxId(countryId, taxType);
        nonNullCountryTax(countryTaxId);
        repository.remove(countryTaxId);
    }

    @Transactional
    public void updateCountryTaxPercentage(long countryId, TaxType taxType, float percentage) {
        CountryTaxId countryTaxId = createCountryTaxId(countryId, taxType);
        CountryTax countryTax = nonNullCountryTax(countryTaxId);
        countryTax.updateTaxPercentage(percentage);
        repository.save(countryTax);

    }

    public <U> U findCountryTaxById(long countryId, TaxType taxType, Transformer<CountryTax, U> transformer) {
        CountryTaxId countryTaxId = createCountryTaxId(countryId, taxType);
        CountryTax countryTax = nonNullCountryTax(countryTaxId);
        return transformer.transform(countryTax);
    }

    public <U> U findAllCountryTaxes(Transformer<List<CountryTax>, U> transformer) {
        List<CountryTax> taxes = repository.findAll();
        return transformer.transform(taxes);
    }

    private CountryTaxId createCountryTaxId(long countryId, TaxType taxType) {
        return new CountryTaxId(countryId, taxType);
    }

    private CountryTax nonNullCountryTax(CountryTaxId id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("Country Tax for country with id %s and tax type %s does not exist", id.countryId(), id.taxType()));
        });
    }
}
