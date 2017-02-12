package com.cities.model.city;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="city")
@Data
public class City {

    @Id
    @Column(name="ci_id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    @Column(name="ci_name", unique = true, nullable = false)
    private String name;

    @Column(name="ci_latitude")
    private Double latitude;

    @Column(name="ci_longitude")
    private Double longitude;

    @Column(name="ci_country_id", nullable = false)
    private Integer countryId;

    @Column(name="ci_wiki_url")
    private String wikiUrl;
}
