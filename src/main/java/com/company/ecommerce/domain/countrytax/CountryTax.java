package com.company.ecommerce.domain.countrytax;

import com.company.ecommerce.domain.Aggregate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class CountryTax implements Aggregate {
    @EmbeddedId
    private CountryTaxId countryTaxId;
    private float percentage;

    public CountryTax() {
        super();
    }

    public CountryTax(CountryTaxId countryTaxId, float percentage) {
        setCountryTaxId(countryTaxId);
        setPercentage(percentage);
    }

    public void updateTaxPercentage(float percentage) {
        setPercentage(percentage);
    }

    private void setCountryTaxId(CountryTaxId countryTaxId) {
        if (Objects.isNull(countryTaxId)) {
            throw new IllegalArgumentException("The country Tax Id is Required");
        }
        this.countryTaxId = countryTaxId;
    }

    private void setPercentage(float percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("The Percentage should be equals or grater than zero");
        }
        this.percentage = percentage;
    }

    public float percentage() {
        return percentage;
    }

    public CountryTaxId id() {
        if (Objects.isNull(countryTaxId)) {
            return null;
        }
        return new CountryTaxId(countryTaxId.countryId(), countryTaxId.taxType());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryTax that = (CountryTax) o;
        return Objects.equals(countryTaxId, that.countryTaxId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryTaxId);
    }
}
