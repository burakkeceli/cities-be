package com.cities.city;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/")
public class CityController {

    @RequestMapping(method = GET)
    public ResponseEntity listCities() {
        List cities = Arrays.asList("Berlin", "Istanbul");
        return new ResponseEntity<>(cities, OK);
    }
}
