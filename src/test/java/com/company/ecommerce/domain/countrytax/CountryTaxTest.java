package com.company.ecommerce.domain.countrytax;

import com.company.ecommerce.TestDomainObjectsFactory;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTaxTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeEmpty() {
        CountryTax countryTax = new CountryTax();

        assertThat(countryTax.id()).isEqualTo(null);
        assertThat(countryTax.percentage()).isEqualTo(0);
    }

    @Test
    public void whenInstantiateWithAllPropertiesConstructor_thenPropertiesShouldNotBeEmpty() {
        CountryTaxId countryTaxId = new CountryTaxId(1, TaxType.BUSINESS);
        CountryTax countryTax = new CountryTax(countryTaxId, 7);

        assertThat(countryTax.id()).isEqualTo(countryTaxId);
        assertThat(countryTax.percentage()).isEqualTo(7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNullCountryTaxId_thenValidationShouldBeThrown() {
        new CountryTax(null, 7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNegativePercentage_thenValidationShouldBeThrown() {
        CountryTaxId countryTaxId = new CountryTaxId(1, TaxType.BUSINESS);
        new CountryTax(countryTaxId, -7);
    }

    @Test
    public void whenUpdatingWithValidParameters_thenCountryTaxShouldBeUpdated() {
        CountryTax countryTax = TestDomainObjectsFactory.createDefaultCountryTax();

        countryTax.updateTaxPercentage(10);

        assertThat(countryTax.percentage()).isEqualTo(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdatingWithNegativePercentage_thenValidationShouldBeThrown() {
        CountryTax countryTax = TestDomainObjectsFactory.createDefaultCountryTax();

        countryTax.updateTaxPercentage(-10);
    }

}