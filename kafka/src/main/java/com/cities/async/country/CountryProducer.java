package com.cities.async.country;

import com.cities.model.country.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CountryProducer {

    @Autowired
    private KafkaTemplate<String, Country> countryKafkaTemplate;

    @Value(value = "${country.topic.name}")
    private String countryTopicName;

    public void sendCountryMessage(Country country) {
        countryKafkaTemplate.send(countryTopicName, country);
    }
}
