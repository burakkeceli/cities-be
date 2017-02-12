package com.cities.city;

import com.cities.city.model.CityDto;
import com.cities.country.CountryLogic;
import com.cities.model.city.City;
import com.cities.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityLogic {

    @Autowired
    private CountryLogic countryLogic;
    @Autowired
    private CityService cityService;

    public List<CityDto> getCityDtoList(List<City> cityList) {
        List<CityDto> cityDtoList = new ArrayList<>();
        for (City city : cityList) {
            CityDto cityDto = getCityDto(city);
            cityDtoList.add(cityDto);
        }
        return cityDtoList;
    }

    public CityDto getCityDto(City city) {
        CityDto cityDto = new CityDto();
        cityDto.setName(city.getName());
        cityDto.setId(city.getId());
        cityDto.setLatitude(city.getLatitude());
        cityDto.setLongitude(city.getLongitude());
        cityDto.setWikiUrl(city.getWikiUrl());
        cityDto.setCountryId(city.getCountryId());
        return cityDto;
    }
}
