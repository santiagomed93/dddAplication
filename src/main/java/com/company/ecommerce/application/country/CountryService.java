package com.company.ecommerce.application.country;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.country.CountryRepository;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    @Transactional
    public void createCountry(String name) {
        Country country = new Country(name);
        repository.save(country);
    }

    @Transactional
    public void deleteCountry(long id) {
        nonNullCountry(id);
        repository.remove(id);
    }

    public <U> U findCountryById(long id, Transformer<Country, U> transformer) {
        Country country = nonNullCountry(id);
        return transformer.transform(country);
    }

    public <U> U findAllCountries(Transformer<List<Country>, U> transformer) {
        List<Country> countries = repository.findAll();
        return transformer.transform(countries);
    }

    private Country nonNullCountry(long id) {
        return repository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("Country with id %s does not exist", id));
        });
    }
}
