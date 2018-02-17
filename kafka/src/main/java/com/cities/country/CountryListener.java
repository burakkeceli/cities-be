package com.cities.country;

import com.cities.model.country.Country;
import com.cities.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CountryListener {

    @Autowired
    private CityService cityService;

    @KafkaListener(
            topics = "${country.topic.name}",
            containerFactory = "countryKafkaListenerContainerFactory")
    public void countryListener(Country country) {
        cityService.saveCountry(country);
    }
}