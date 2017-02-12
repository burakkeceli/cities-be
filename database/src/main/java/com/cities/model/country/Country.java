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
    @Column(name="co_id")
    @GeneratedValue(strategy= IDENTITY)
    private Integer id;

    @Column(name = "co_name", unique = true, nullable = false)
    private String name;

    @Column(name = "co_population")
    private Integer population;

    @Column(name = "co_capital", unique = true, nullable = false)
    private String capital;

    @Column(name = "co_icon_flag", unique = true)
    private String iconFlag;

    @Column(name = "co_small_flag", unique = true)
    private String smallFlag;

    @Column(name = "co_big_flag", unique = true)
    private String bigFlag;
}
