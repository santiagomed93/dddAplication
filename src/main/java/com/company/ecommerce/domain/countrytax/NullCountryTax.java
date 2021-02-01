package com.company.ecommerce.domain.countrytax;

class NullCountryTax extends CountryTax {
    NullCountryTax(CountryTaxId countryTaxId) {
        super(countryTaxId, 0);
    }
}
