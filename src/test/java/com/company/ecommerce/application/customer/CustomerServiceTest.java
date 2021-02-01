package com.company.ecommerce.application.customer;

import com.company.ecommerce.TestDomainObjectsFactory;
import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.country.CountryRepository;
import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerCreated;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.customer.CustomerRepository;
import com.company.ecommerce.domain.event.DomainEventPublisher;
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
public class CustomerServiceTest {

    @TestConfiguration
    static class CustomerServiceTestContextConfiguration {

        @Bean
        public CustomerService customerService() {
            return new CustomerService();
        }
    }

    @MockBean
    private CustomerRepository repository;

    @MockBean
    private CountryRepository countryRepository;

    @MockBean
    private DomainEventPublisher publisher;

    @Autowired
    private CustomerService service;

    private Customer defaultCustomer;

    private Country defaultCountry;

    @Before
    public void setUp() {
        defaultCustomer = TestDomainObjectsFactory.createDefaultCustomerWithCreditCards();
        defaultCountry = TestDomainObjectsFactory.createDefaultCountry();
        Mockito.when(repository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.of(defaultCustomer));
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(defaultCustomer));
        Mockito.when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(defaultCountry));
    }

    @Test
    public void whenCreatingCustomerWithValidData_thenCustomerShouldBeCreated() {
        CreateCustomerCommand customerCommand = TestDomainObjectsFactory.createCustomerCommand();

        service.createCustomer(customerCommand);

        Mockito.verify(repository).save(Mockito.any(Customer.class));
        Mockito.verifyNoMoreInteractions(repository);
        Mockito.verify(countryRepository).findById(Mockito.anyLong());
        Mockito.verifyNoMoreInteractions(repository);
        Mockito.verify(publisher).publishEvent(Mockito.any(CustomerCreated.class));
        Mockito.verifyNoMoreInteractions(publisher);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenCreatingCustomerForCountryThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(countryRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        CreateCustomerCommand customerCommand = TestDomainObjectsFactory.createCustomerCommand();

        service.createCustomer(customerCommand);
    }

    @Test
    public void whenFindingCustomerWithValidId_thenCustomerShouldBeReturned() {
        CustomerDto customerDto = service.findCustomerById("1", CustomerTransformer.toCustomerDto);

        assertThat(customerDto.getCountryId()).isEqualTo(defaultCustomer.country().id());
        assertThat(customerDto.getEmail()).isEqualTo(defaultCustomer.email());
        assertThat(customerDto.getFirstName()).isEqualTo(defaultCustomer.name().firstName());
        assertThat(customerDto.getLastName()).isEqualTo(defaultCustomer.name().lastName());
        assertThat(customerDto.getFullName()).isEqualTo(defaultCustomer.name().fullName());
        assertThat(customerDto.getId()).isEqualTo(defaultCustomer.id().id());
        Mockito.verify(repository).findById(Mockito.any(CustomerId.class));
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindingCustomerThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());

        service.findCustomerById("1", CustomerTransformer.toCustomerDto);
    }

    @Test
    public void whenDeletingCustomerWithValidId_thenCustomerShouldBeReturned() {
        service.deleteCustomerById("1");

        Mockito.verify(repository).remove(Mockito.any(CustomerId.class));
        Mockito.verify(repository).findById(Mockito.any(CustomerId.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeletingCustomerThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());

        service.deleteCustomerById("1");
    }

    @Test
    public void whenFindingAllCustomer_thenAllCustomersShouldBeReturned() {
        List<CustomerDto> customers = service.findAllCostumers(CustomerTransformer.toCustomerDtoList);

        assertThat(customers).isNotEmpty();
        assertThat(customers.size()).isEqualTo(1);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenFindingAllCustomerForEmptyRepository_thenCustomersListShouldBeEmpty() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());

        List<CustomerDto> customers = service.findAllCostumers(CustomerTransformer.toCustomerDtoList);

        assertThat(customers).isEmpty();
        assertThat(customers.size()).isEqualTo(0);
        Mockito.verify(repository).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void whenAddingCreditCardToCustomer_thenCreditCardShouldBeAdded() {
        AddCreditCardCommand addCreditCardCommand = TestDomainObjectsFactory.createAddCreditCardCommand();

        service.addCreditCard(addCreditCardCommand);

        Mockito.verify(repository).findById(Mockito.any(CustomerId.class));
        Mockito.verify(repository).save(Mockito.any(Customer.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenAddingCreditCardToCustomerThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());
        AddCreditCardCommand addCreditCardCommand = TestDomainObjectsFactory.createAddCreditCardCommand();

        service.addCreditCard(addCreditCardCommand);
    }

    @Test
    public void whenRemovingCreditCardFromCustomer_thenCreditCardShouldBeRemoved() {
        service.removeCreditCardByNumber("1", "1234567890");

        Mockito.verify(repository).findById(Mockito.any(CustomerId.class));
        Mockito.verify(repository).save(Mockito.any(Customer.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenAddingCartToCustomerThatDoesNotExist_thenValidationShouldBeThrown() {
        Mockito.when(repository.findById(Mockito.any(CustomerId.class))).thenReturn(Optional.empty());

        service.removeCreditCardByNumber("1", "777777777");
    }
}
