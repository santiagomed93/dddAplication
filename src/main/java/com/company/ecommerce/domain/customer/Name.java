package com.company.ecommerce.domain.customer;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    private String firstName;
    private String lastName;

    public Name() {
        super();
    }

    public Name(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    public String fullName() {
        if (StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName)) {
            return null;
        } else {
            return firstName + " " + lastName;
        }
    }

    private void setFirstName(String firstName) {
        if (StringUtils.isEmpty(firstName)) {
            throw new IllegalArgumentException("First Name is Required");
        }
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        if (StringUtils.isEmpty(lastName)) {
            throw new IllegalArgumentException("Last Name is Required");
        }
        this.lastName = lastName;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(firstName, name.firstName) &&
                Objects.equals(lastName, name.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
