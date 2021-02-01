package com.company.ecommerce.application.countrytax;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.countrytax.CountryTax;

import java.util.List;
import java.util.stream.Collectors;

public class CountryTaxTransformer {

    public static final Transformer<CountryTax, CountryTaxDto> toCountryTaxDto = countryTax -> {
        CountryTaxDto countryTaxDto = new CountryTaxDto();
        countryTaxDto.setCountryId(countryTax.id().countryId());
        countryTaxDto.setPercentage(countryTax.percentage());
        countryTaxDto.setTaxType(countryTax.id().taxType());
        return countryTaxDto;
    };

    public static final Transformer<List<CountryTax>, List<CountryTaxDto>> toCountryTaxDtoList = countryTaxList -> countryTaxList.stream()
            .map(toCountryTaxDto::transform)
            .collect(Collectors.toList());
}
