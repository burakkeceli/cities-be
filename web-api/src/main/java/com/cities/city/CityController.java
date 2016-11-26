package com.cities.city;

import com.cities.user.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/city")
public class CityController {

    @RequestMapping(value = "liked", method = GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity getLikedCities(@AuthenticationPrincipal UserDto userDto) {
        List<String> cities = Arrays.asList("Berlin", "Istanbul");
        return new ResponseEntity<>(cities, OK);
    }
}