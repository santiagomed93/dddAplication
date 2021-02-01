package com.company.ecommerce.domain.customer;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerId implements Serializable {

    private String customerId;

    public CustomerId() {
    }

    public CustomerId(String customerId) {
        setCustomerId(customerId);
    }

    private void setCustomerId(String customerId) {
        if (StringUtils.isEmpty(customerId)) {
            throw new IllegalArgumentException("Customer Id is required");
        }
        this.customerId = customerId;
    }

    public String id() {
        return customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId that = (CustomerId) o;
        return Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}
