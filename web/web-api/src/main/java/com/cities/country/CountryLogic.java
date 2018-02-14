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

    public List<Country> getCountryList(List<CountryDto> countryDtoList) {
        List<Country> countryList = new ArrayList<>();
        for (CountryDto countryDto : countryDtoList) {
            Country country = getCountry(countryDto);
            countryList.add(country);
        }
        return countryList;

    }

    public Country getCountry(CountryDto countryDto) {
        Country country = new Country();
        country.setName(countryDto.getName());
        country.setId(countryDto.getId());
        country.setCapital(countryDto.getCapital());
        country.setBigFlag(countryDto.getBigFlag());
        country.setIconFlag(countryDto.getIconFlag());
        country.setSmallFlag(countryDto.getSmallFlag());
        country.setPopulation(countryDto.getPopulation());
        return country;
    }
}
