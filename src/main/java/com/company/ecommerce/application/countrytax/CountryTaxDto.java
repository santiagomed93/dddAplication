package com.company.ecommerce.application.countrytax;

import com.company.ecommerce.domain.countrytax.TaxType;

public class CountryTaxDto {
    private long countryId;
    private float percentage;
    private TaxType taxType;

    public CountryTaxDto() {
        super();
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }
}