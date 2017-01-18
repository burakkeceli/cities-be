package com.cities.country;

import com.cities.country.model.CountryDto;
import com.cities.model.country.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryLogic {

    public CountryDto getCountryDto (Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(country.getName());
        countryDto.setId(country.getId());
        return countryDto;
    }
}
