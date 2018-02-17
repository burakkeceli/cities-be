package com.cities.city;

import com.cities.model.city.City;
import com.cities.service.city.CityService;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.stereotype.Component;

@Component
public class CityListener {

    @Autowired
    private CityService cityService;

    @KafkaListener(
            topics = "${city.topic.name}",
            containerFactory = "cityKafkaListenerContainerFactory")
    public void cityListener(City city) {
        cityService.saveCity(city);
    }
}