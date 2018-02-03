package com.cities.country.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto {

    private Integer id;
    private String name;
    private Integer population;
    private String capital;
    private String iconFlag;
    private String smallFlag;
    private String bigFlag;
}
