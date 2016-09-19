package com.cities.city;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/city")
@PreAuthorize("hasAnyRole('ROLE_USER')")
public class CityController {

    @RequestMapping(value = "liked", method = GET)
    public ResponseEntity getLikedCities() {
        List cities = Arrays.asList("Berlin", "Istanbul");
        return new ResponseEntity<>(cities, OK);
    }
}
