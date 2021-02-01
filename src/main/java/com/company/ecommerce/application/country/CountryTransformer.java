package com.company.ecommerce.application.country;

import com.company.ecommerce.application.Transformer;
import com.company.ecommerce.domain.country.Country;

import java.util.List;
import java.util.stream.Collectors;

public class CountryTransformer {

    public static final Transformer<Country, CountryDto> toCountryDto = country -> {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(country.id());
        countryDto.setName(country.name());
        return countryDto;
    };

    public static final Transformer<List<Country>, List<CountryDto>> toCountryDtoList = countryList -> countryList.stream()
            .map(toCountryDto::transform)
            .collect(Collectors.toList());

}
