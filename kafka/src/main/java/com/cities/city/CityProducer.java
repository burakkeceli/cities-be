package com.cities.city;

import com.cities.model.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CityProducer {

    @Autowired
    private KafkaTemplate<String, City> cityKafkaTemplate;

    @Value(value = "${city.topic.name}")
    private String cityTopicName;

    public void sendCityMessage(City city) {

        cityKafkaTemplate.send(cityTopicName, city);
    }
}
