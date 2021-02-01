package com.company.ecommerce.domain.country;

import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Country {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    public Country() {
        super();
    }

    public Country(long id, String name) {
        setId(id);
        setName(name);
    }

    public Country(String name) {
        setName(name);
    }

    private void setId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Country Id should be grater than 0");
        }
        this.id = id;
    }

    private void setName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Country Name is Required");
        }
        this.name = name;
    }

    public String name() {
        return name;
    }

    public long id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id == country.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
