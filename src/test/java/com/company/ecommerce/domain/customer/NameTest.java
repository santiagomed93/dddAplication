package com.company.ecommerce.domain.customer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NameTest {

    @Test
    public void whenCreatingNameWithValidData_theNameShouldBeCreated() {
        Name name = new Name();
        assertThat(name.firstName()).isEqualTo(null);
        assertThat(name.lastName()).isEqualTo(null);
        assertThat(name.fullName()).isEqualTo(null);
    }

    @Test
    public void whenCreatingNameWithDefaultConstructor_theAllPropertiesShouldBeNull() {
        Name name = new Name("The", "Test");
        assertThat(name.firstName()).isEqualTo("The");
        assertThat(name.lastName()).isEqualTo("Test");
        assertThat(name.fullName()).isEqualTo("The Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingNameWithEmptyFirstName_thenValidationShouldBeThrown() {
        new Name("", "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingNameWithNullFirstName_thenValidationShouldBeThrown() {
        new Name(null, "Test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingNameWithEmptyLastName_thenValidationShouldBeThrown() {
        new Name("The", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreatingNameWithNullLastName_thenValidationShouldBeThrown() {
        new Name("The", null);
    }

}