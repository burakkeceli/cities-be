package com.cities.dao;

import com.cities.model.city.City;
import com.cities.test.AbstractBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CityDAOITest extends AbstractBaseTest {

    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private CountryDAO countryDAO;

    @Test
    public void shouldSaveCity() {

        // given
        City city = new City();
        city.setName(UUID.randomUUID().toString());
        city.setCountryId(1);

        // when
        cityDAO.save(city);

        // then
        City fetchedCity = cityDAO.getById(city.getId());
        assertThat(fetchedCity.getName()).isEqualTo(city.getName());
        assertThat(fetchedCity.getCountryId()).isEqualTo(city.getCountryId());
    }
}
