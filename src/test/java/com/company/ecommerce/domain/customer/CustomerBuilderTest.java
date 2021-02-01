package com.company.ecommerce.domain.customer;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerBuilderTest {

    private CustomerId defaultCustomerId = new CustomerId("1");
    private Country defaultCountry = TestDomainObjectsFactory.createDefaultCountry();
    private DomainEventPublisher publisher = Mockito.mock(DomainEventPublisher.class);

    @Test
    public void whenBuildingCustomerWithValidData_thenCustomerShouldBeCreated() {
        Customer customer = new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "h@h.com";
            $.firstName = "Hector";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);

        assertThat(customer.country()).isEqualTo(defaultCountry);
        assertThat(customer.creditCards()).isEmpty();
        assertThat(customer.email()).isEqualTo("h@h.com");
        assertThat(customer.name().firstName()).isEqualTo("Hector");
        assertThat(customer.name().lastName()).isEqualTo("Hurtado");
        assertThat(customer.id()).isEqualTo(defaultCustomerId);
        Mockito.verify(publisher).publishEvent(Mockito.any(CustomerCreated.class));
        Mockito.verifyNoMoreInteractions(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithoutData_theValidationShouldBeThrown() {
        new CustomerBuilder().buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithNullId_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.country = defaultCountry;
            $.email = "h@h.com";
            $.firstName = "Hector";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithNullCountry_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.email = "h@h.com";
            $.firstName = "Hector";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithEmptyFirstName_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "h@h.com";
            $.firstName = "";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithNullFirstName_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "h@h.com";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithEmptyLastName_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "h@h.com";
            $.firstName = "Hector";
            $.lastName = "";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithNullLastName_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "h@h.com";
            $.firstName = "Hector";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithEmptyEmail_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "";
            $.firstName = "Hector";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithNullEmail_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.firstName = "Hector";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenBuildingCustomerWithEmailWithBadFormat_thenValidationShouldBeThrown() {
        new CustomerBuilder().with($ -> {
            $.id = defaultCustomerId;
            $.country = defaultCountry;
            $.email = "h@h";
            $.firstName = "Hector";
            $.lastName = "Hurtado";
        }).buildCustomer(publisher);
    }

}