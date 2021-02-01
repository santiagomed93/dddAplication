package com.company.ecommerce.presentation.countrytax;

import com.company.ecommerce.application.countrytax.CountryTaxDto;
import com.company.ecommerce.application.countrytax.CountryTaxService;
import com.company.ecommerce.application.countrytax.CountryTaxTransformer;
import com.company.ecommerce.domain.countrytax.TaxType;
import com.company.ecommerce.presentation.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryTaxController {

    @Autowired
    private CountryTaxService service;

    @PostMapping("/taxes")
    public void createCountryTax(@RequestBody CountryTaxDto countryTaxDto) {
        service.createCountryTax(countryTaxDto.getCountryId(), countryTaxDto.getTaxType(), countryTaxDto.getPercentage());
    }

    @PutMapping("/taxes/percentage")
    public void updateCountryTaxPercentage(@RequestBody CountryTaxDto countryTaxDto) {
        service.updateCountryTaxPercentage(countryTaxDto.getCountryId(), countryTaxDto.getTaxType(), countryTaxDto.getPercentage());
    }

    @DeleteMapping("/taxes/country-id/{countryId}/tax-type/{taxType}")
    public void deleteCountryTax(@PathVariable Long countryId, @PathVariable String taxType) {
        service.deleteCountryTax(countryId, TaxType.valueOf(taxType.toUpperCase()));
    }

    @GetMapping("/taxes/country-id/{countryId}/tax-type/{taxType}")
    public ResponseEntity<GenericResponse<CountryTaxDto>> findCountryTaxById(@PathVariable Long countryId, @PathVariable String taxType) {
        CountryTaxDto countryTaxDto = service.findCountryTaxById(countryId, TaxType.valueOf(taxType.toUpperCase()), CountryTaxTransformer.toCountryTaxDto);
        GenericResponse<CountryTaxDto> response = new GenericResponse<>(countryTaxDto, "Country Tax Found");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/taxes")
    public ResponseEntity<GenericResponse<List<CountryTaxDto>>> findAllCountries() {
        List<CountryTaxDto> countryTaxDtoList = service.findAllCountryTaxes(CountryTaxTransformer.toCountryTaxDtoList);
        GenericResponse<List<CountryTaxDto>> response = new GenericResponse<>(countryTaxDtoList, "Here are all the country taxes");
        return ResponseEntity.ok(response);
    }
}
