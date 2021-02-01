package com.company.ecommerce.application.customer;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.country.Country;
import com.company.ecommerce.domain.country.CountryRepository;
import com.company.ecommerce.domain.customer.*;
import com.company.ecommerce.domain.event.DomainEventPublisher;
import com.company.ecommerce.domain.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DomainEventPublisher publisher;

    @Transactional
    public void createCustomer(CreateCustomerCommand createCustomerCommand) {
        CustomerId id = new CustomerId(createCustomerCommand.getId());
        Country country = nonNullCountry(createCustomerCommand.getCountryId());
        Customer customer = new CustomerBuilder().with($ -> {
            $.id = id;
            $.country = country;
            $.email = createCustomerCommand.getEmail();
            $.firstName = createCustomerCommand.getFirstName();
            $.lastName = createCustomerCommand.getLastName();
        }).buildCustomer(publisher);
        repository.save(customer);
    }

    public <U> U findCustomerById(String id, Transformer<Customer, U> transformer) {
        Customer customer = nonNullCostumer(id);
        return transformer.transform(customer);
    }

    @Transactional
    public void deleteCustomerById(String id) {
        Customer customer = nonNullCostumer(id);
        repository.remove(customer.id());
    }

    public <U> U findAllCostumers(Transformer<List<Customer>, U> transformer) {
        List<Customer> customers = repository.findAll();
        return transformer.transform(customers);
    }

    @Transactional
    public void addCreditCard(AddCreditCardCommand addCreditCardCommand) {
        Customer customer = nonNullCostumer(addCreditCardCommand.getCustomerId());
        CreditCard creditCard = new CreditCardBuilder().with($ -> {
            $.active = addCreditCardCommand.isActive();
            $.cardNumber = addCreditCardCommand.getCardNumber();
            $.customerId = customer.id();
            $.nameOnCard = addCreditCardCommand.getNameOnCard();
            $.expiry = addCreditCardCommand.getExpiry();
        }).buildCreditCard();
        customer.addCreditCard(creditCard);
        repository.save(customer);
    }

    @Transactional
    public void removeCreditCardByNumber(String customerId, String cardNumber) {
        Customer customer = nonNullCostumer(customerId);
        customer.removeCreditCardByNumber(cardNumber);
        repository.save(customer);
    }

    private Customer nonNullCostumer(String id) {
        CustomerId customerId = new CustomerId(id);
        return repository.findById(customerId).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Customer with id %s does not exist", id));
        });
    }

    private Country nonNullCountry(long id) {
        return countryRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(String.format("The Country with id %s does not exist", id));
        });
    }
}
