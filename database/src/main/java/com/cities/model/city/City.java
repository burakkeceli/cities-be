package com.cities.model.city;

import com.cities.model.country.Country;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="city")
@Data
public class City {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    @Column(name="name", unique = true, nullable = false)
    private String name;
    private Double latitude;
    private Double longitude;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;
}
