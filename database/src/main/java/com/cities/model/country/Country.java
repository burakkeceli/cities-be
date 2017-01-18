package com.cities.model.country;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="country")
@Data
@EqualsAndHashCode
public class Country {

    @Id
    @Column(name="country_id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, name = "name")
    private String name;
}
