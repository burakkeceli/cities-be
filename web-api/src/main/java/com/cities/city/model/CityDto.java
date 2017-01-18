package com.cities.city.model;

import com.cities.country.model.CountryDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CityDto {

    private Integer id;
    private String name;
    private CountryDto country;
}
