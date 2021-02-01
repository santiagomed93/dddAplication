package com.company.ecommerce.domain.countrytax;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTaxIdTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenIdShouldBeNull() {
        CountryTaxId countryTaxId = new CountryTaxId();

        assertThat(countryTaxId.countryId()).isEqualTo(0);
        assertThat(countryTaxId.taxType()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithIdConstructor_thenIdShouldNotBeNull() {
        CountryTaxId countryTaxId = new CountryTaxId(1, TaxType.BUSINESS);

        assertThat(countryTaxId.countryId()).isEqualTo(1);
        assertThat(countryTaxId.taxType()).isEqualTo(TaxType.BUSINESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithCountryIdEqualsToZero_thenValidationShouldBeThrown() {
        new CountryTaxId(0, TaxType.BUSINESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNegativeCountry_thenValidationShouldBeThrown() {
        new CountryTaxId(-1, TaxType.BUSINESS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithInvalidTaxType_thenValidationShouldBeThrown() {
        new CountryTaxId(1, null);
    }
}
