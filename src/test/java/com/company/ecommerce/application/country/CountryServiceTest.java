package com.company.ecommerce.application.country;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.country.CountryRepository;
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
public class CountryServiceTest {

    @TestConfiguration
    static class CountryServiceTestContextConfiguration {

        @Bean
        public CountryService countryService() {
            return new CountryService();
        }
    }

    @Autowired
    private CountryService service;

    @MockBean
    private CountryRepository repository;

    private Country defaultCountry;

    @Before
    public void setUp() {
        defaultCountry = TestDomainObjectsFactory.createDefaultCountry();

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(defaultCountry));
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(defaultCountry));
    }

    @Test
    public void whenCreatingValidCountry_thenCountryShouldBeCreated() {
        service.createCountry("Colombia");

        Mockito.verify(repository).save(Mockito.any(Country.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenDeletingValidCountry_thenCountryShouldBeDeleted() {
        service.deleteCountry(1L);

        Mockito.verify(repository).remove(1L);
        Mockito.verify(repository).findById(1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeletingCountryInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        service.deleteCountry(1);
    }

    @Test
    public void whenFindingCountryWithValidId_thenCountryShouldBeReturned() {
        CountryDto countryDto = service.findCountryById(1L, CountryTransformer.toCountryDto);

        assertThat(countryDto.getId()).isEqualTo(defaultCountry.id());
        assertThat(countryDto.getName()).isEqualTo(defaultCountry.name());
        Mockito.verify(repository).findById(1L);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindingCountryWithInvalidId_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        service.findCountryById(1, CountryTransformer.toCountryDto);
    }

    @Test
    public void whenFindingAllCountries_thenAllCountriesShouldBeReturned() {
        List<CountryDto> countries = service.findAllCountries(CountryTransformer.toCountryDtoList);

        assertThat(countries).isNotEmpty();
        assertThat(countries.size()).isEqualTo(1);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenFindingAllCountriesForEmptyRepository_thenCountryListShouldBeEmpty() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        List<CountryDto> countries = service.findAllCountries(CountryTransformer.toCountryDtoList);

        assertThat(countries).isEmpty();
        assertThat(countries.size()).isEqualTo(0);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }
}