package com.cities.country;

import com.cities.country.model.CountryDto;
import com.cities.model.country.Country;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryLogic {

    public List<CountryDto> getCountryDtoList(List<Country> countryList) {
        List<CountryDto> countryDtoList = new ArrayList<>();
        for (Country country : countryList) {
            CountryDto countryDto = getCountryDto(country);
            countryDtoList.add(countryDto);
        }
        return countryDtoList;
    }

    public CountryDto getCountryDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(country.getName());
        countryDto.setId(country.getId());
        countryDto.setCapital(country.getCapital());
        countryDto.setBigFlag(country.getBigFlag());
        countryDto.setIconFlag(country.getIconFlag());
        countryDto.setSmallFlag(country.getSmallFlag());
        countryDto.setPopulation(country.getPopulation());
        return countryDto;
    }
}
