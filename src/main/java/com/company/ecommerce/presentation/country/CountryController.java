package com.company.ecommerce.presentation.country;

import com.company.ecommerce.application.country.CountryDto;
import com.company.ecommerce.application.country.CountryService;
import com.company.ecommerce.application.country.CountryTransformer;
import com.company.ecommerce.presentation.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CountryController {

    @Autowired
    private CountryService service;

    @PostMapping("/countries")
    public void createCountry(@RequestBody CountryDto countryDto) {
        service.createCountry(countryDto.getName());
    }

    @DeleteMapping("/countries/{id}")
    public void deleteCountry(@PathVariable Long id) {
        service.deleteCountry(id);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<GenericResponse<CountryDto>> findCountryById(@PathVariable Long id) {
        CountryDto countryDto = service.findCountryById(id, CountryTransformer.toCountryDto);
        GenericResponse<CountryDto> response = new GenericResponse<>(countryDto, "Country Found");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/countries")
    public ResponseEntity<GenericResponse<List<CountryDto>>> findAllCountries() {
        List<CountryDto> countryDtoList = service.findAllCountries(CountryTransformer.toCountryDtoList);
        GenericResponse<List<CountryDto>> response = new GenericResponse<>(countryDtoList, "Here are all the countries");
        return ResponseEntity.ok(response);
    }
}
