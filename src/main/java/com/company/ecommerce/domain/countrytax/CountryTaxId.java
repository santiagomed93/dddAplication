package com.company.ecommerce.domain.countrytax;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CountryTaxId implements Serializable {
    private long countryId;
    @Enumerated(EnumType.STRING)
    private TaxType taxType;

    public CountryTaxId() {
        super();
    }

    public CountryTaxId(long countryId, TaxType taxType) {
        setCountryId(countryId);
        setTaxType(taxType);
    }

    private void setCountryId(long countryId) {
        if (countryId <= 0) {
            throw new IllegalArgumentException("The Country should be Valid");
        }
        this.countryId = countryId;
    }

    private void setTaxType(TaxType taxType) {
        if (Objects.isNull(taxType)) {
            throw new IllegalArgumentException("The tax type is required");
        }
        this.taxType = taxType;
    }

    public long countryId() {
        return countryId;
    }

    public TaxType taxType() {
        return taxType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountryTaxId that = (CountryTaxId) o;
        return countryId == that.countryId &&
                taxType == that.taxType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryId, taxType);
    }
}
