package com.company.ecommerce.application.countrytax;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.countrytax.CountryTax;
import com.company.ecommerce.domain.countrytax.CountryTaxId;
import com.company.ecommerce.domain.countrytax.CountryTaxRepository;
import com.company.ecommerce.domain.countrytax.TaxType;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CountryTaxServiceTest {

    @TestConfiguration
    static class CountryTaxServiceTestContextConfiguration {

        @Bean
        public CountryTaxService countryTaxService() {
            return new CountryTaxService();
        }
    }

    @Autowired
    private CountryTaxService service;

    @MockBean
    private CountryTaxRepository repository;

    private CountryTax defaultCountryTax;

    @Before
    public void setUp() {
        defaultCountryTax = TestDomainObjectsFactory.createDefaultCountryTax();
        Mockito.when(repository.findById(Mockito.any(CountryTaxId.class))).thenReturn(Optional.of(defaultCountryTax));
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(defaultCountryTax));
    }

    @Test
    public void whenCreatingValidCountryTax_thenCountryTaxShouldBeCreated() {
        service.createCountryTax(1, TaxType.CUSTOMER, 5);

        Mockito.verify(repository).save(Mockito.any(CountryTax.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenDeletingValidCountryTax_thenCountryTaxShouldBeDeleted() {
        service.deleteCountryTax(1, TaxType.BUSINESS);

        Mockito.verify(repository).remove(Mockito.any(CountryTaxId.class));
        Mockito.verify(repository).findById(Mockito.any(CountryTaxId.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeletingCountryTaxWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CountryTaxId.class))).thenReturn(Optional.empty());

        service.deleteCountryTax(1, TaxType.BUSINESS);
    }

    @Test
    public void whenUpdatingValidCountryTax_thenCountryTaxShouldBeUpdated() {
        service.updateCountryTaxPercentage(1, TaxType.BUSINESS, 10);

        Mockito.verify(repository).findById(Mockito.any(CountryTaxId.class));
        Mockito.verify(repository).save(Mockito.any(CountryTax.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdatingCountryTaxWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CountryTaxId.class))).thenReturn(Optional.empty());

        service.updateCountryTaxPercentage(1, TaxType.CUSTOMER, 1);
    }

    @Test
    public void whenFindingCountryTaxWithValidId_thenCountryTaxShouldBeReturned() {
        CountryTaxDto countryTaxDto = service.findCountryTaxById(1, TaxType.BUSINESS, CountryTaxTransformer.toCountryTaxDto);

        assertThat(countryTaxDto.getPercentage()).isEqualTo(defaultCountryTax.percentage());
        assertThat(countryTaxDto.getTaxType()).isEqualTo(defaultCountryTax.id().taxType());
        assertThat(countryTaxDto.getCountryId()).isEqualTo(defaultCountryTax.id().countryId());
        Mockito.verify(repository).findById(Mockito.any(CountryTaxId.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindingCountryTaxWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CountryTaxId.class))).thenReturn(Optional.empty());

        service.findCountryTaxById(1, TaxType.BUSINESS, CountryTaxTransformer.toCountryTaxDto);
    }

    @Test
    public void whenFindingAllCountryTaxes_thenAllCountryTaxesShouldBeReturned() {
        List<CountryTaxDto> countryTaxes = service.findAllCountryTaxes(CountryTaxTransformer.toCountryTaxDtoList);

        assertThat(countryTaxes).isNotEmpty();
        assertThat(countryTaxes.size()).isEqualTo(1);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenFindingAllCountryTaxesForEmptyRepository_thenCountryTaxeListShouldBeEmpty() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        List<CountryTaxDto> countryTaxes = service.findAllCountryTaxes(CountryTaxTransformer.toCountryTaxDtoList);

        assertThat(countryTaxes).isEmpty();
        assertThat(countryTaxes.size()).isEqualTo(0);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

}