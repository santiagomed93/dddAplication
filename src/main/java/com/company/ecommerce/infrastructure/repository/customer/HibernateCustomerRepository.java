package com.company.ecommerce.infrastructure.repository.customer;

import com.company.ecommerce.domain.customer.Customer;
import com.company.ecommerce.domain.customer.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface HibernateCustomerRepository extends JpaRepository<Customer, CustomerId> {
}
