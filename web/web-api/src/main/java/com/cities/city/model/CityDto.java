package com.cities.city.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class CityDto {

    private Integer id;
    private String name;
    private Double latitude;
    private Double longitude;
    private String wikiUrl;
    private Integer countryId;
}
