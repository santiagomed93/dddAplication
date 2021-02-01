package com.company.ecommerce.infrastructure.repository.customer;

import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerId;
import com.company.ecommerce.domain.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class H2CustomerRepository implements CustomerRepository {

    @Autowired
    private HibernateCustomerRepository repository;

    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public void remove(CustomerId customerId) {
        repository.deleteById(customerId);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Customer> findById(CustomerId customerId) {
        return repository.findById(customerId);
    }
}
