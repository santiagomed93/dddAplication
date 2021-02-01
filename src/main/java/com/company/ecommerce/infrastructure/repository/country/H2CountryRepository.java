package com.company.ecommerce.infrastructure.repository.country;

import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.country.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2CountryRepository implements CountryRepository {

    @Autowired
    private HibernateCountryRepository repository;

    @Override
    public Country save(Country country) {
        return repository.save(country);
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Country> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return repository.findById(id);
    }
}
