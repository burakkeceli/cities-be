package com.cities.config;

import com.cities.dao.CityDAO;
import com.cities.model.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CityListener {

    @Autowired
    private CityDAO cityDAO;

    @KafkaListener(
            topics = "${city.topic.name}",
            containerFactory = "cityKafkaListenerContainerFactory")
    public void cityListener(City city) {
        System.out.println("Received city message: " + city);
        //cityDAO.save(city);
    }
}