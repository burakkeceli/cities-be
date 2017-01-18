package com.cities.dao;

import com.cities.base.AbstractBaseITest;
import com.cities.model.city.City;
import com.cities.model.country.Country;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CityDAOITest extends AbstractBaseITest {

    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private CountryDAO countryDAO;

    @Test
    public void shouldSaveCity() {

        // given
        Country country = new Country();
        country.setName(UUID.randomUUID().toString());
        countryDAO.save(country);

        City city = new City();
        city.setName(UUID.randomUUID().toString());
        city.setCountry(country);

        // when
        cityDAO.save(city);

        // then
        City fetchedCity = cityDAO.getById(city.getId());
        assertThat(fetchedCity.getName()).isEqualTo(city.getName());
        assertThat(fetchedCity.getCountry()).isEqualTo(city.getCountry());
    }
}
