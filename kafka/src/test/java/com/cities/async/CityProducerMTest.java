package com.cities.async;

import com.cities.async.city.CityProducer;
import com.cities.model.city.City;
import com.cities.service.city.CityService;
import com.cities.test.AbstractBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CityProducerMTest extends AbstractBaseTest {

    @Autowired
    private CityProducer cityProducer;
    @Autowired
    private CityService cityService;

    @Test
    public void shouldSaveCityAsync() throws Exception {
        // given
        City city = new City();
        city.setName(UUID.randomUUID().toString());
        city.setCountryId(1);

        // when
        cityProducer.sendCityMessage(city);

        // wait
        Thread.sleep(7_000);

        // then
        List<City> allCities = cityService.getAllCities();
        Optional<City> optionalFetchedCity = allCities.stream().filter(x -> x.getName().equals(city.getName())).findFirst();
        assertThat(optionalFetchedCity).isPresent();
    }
}
