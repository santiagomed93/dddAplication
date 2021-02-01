package com.company.ecommerce.domain.country;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {

    @Test
    public void whenInstantiateWithDefaultConstructor_thenAllPropertiesShouldBeEmpty() {
        Country country = new Country();

        assertThat(country.id()).isEqualTo(0);
        assertThat(country.name()).isEqualTo(null);
    }

    @Test
    public void whenInstantiateWithAllPropertiesConstructor_thenAllPropertiesShouldNotBeEmpty() {
        Country country = new Country(1, "Colombia");

        assertThat(country.id()).isEqualTo(1);
        assertThat(country.name()).isEqualTo("Colombia");
    }

    @Test
    public void whenInstantiateWithPartialConstructor_thenPartialPropertiesShouldNotBeEmpty() {
        Country country = new Country("Colombia");

        assertThat(country.name()).isEqualTo("Colombia");
        assertThat(country.id()).isEqualTo(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNegativeId_thenValidationShouldBeThrown() {
        new Country(-1, "Colombia");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithIdEqualsToZero_thenValidationShouldBeThrown() {
        new Country(0, "Colombia");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithEmptyName_thenValidationShouldBeThrown() {
        Country country = new Country("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInstantiateWithNameEqualsToNull_thenValidationShouldBeThrown() {
        new Country(null);
    }
}