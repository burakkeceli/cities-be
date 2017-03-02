package com.cities.city;

import com.cities.city.model.CityDto;
import com.cities.model.city.City;
import com.cities.service.city.CassandraCityService;
import com.cities.service.city.CityService;
import com.cities.user.UserLogic;
import com.cities.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.cities.constant.ApiConstants.Urls.CITY;
import static com.cities.constant.ApiConstants.Urls.LIKED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = CITY, produces = APPLICATION_JSON_UTF8_VALUE)
public class CityController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CityLogic cityLogic;
    @Autowired
    private CassandraCityService cassandraCityService;
    @Autowired
    private UserLogic userLogic;

    @RequestMapping(method = GET)
    public ResponseEntity getAll(HttpServletRequest request) {
        List<City> cityList = cityService.getAllCities();
        List<CityDto> cityDtoList = cityLogic.getCityDtoList(cityList);
        return new ResponseEntity<>(cityDtoList, OK);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity getById(@PathVariable Integer id, HttpServletRequest request) {
        City city = cityService.getCityById(id);
        if (city == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        CityDto cityDto = cityLogic.getCityDto(city);
        return new ResponseEntity<>(cityDto, OK);
    }

    @RequestMapping(value = "/{id}"+LIKED, method = GET)
    public ResponseEntity getUserListWhoLikeCity(@PathVariable Integer id, HttpServletRequest request) {
        City city = cityService.getCityById(id);
        if (city == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        Map<Integer, String> userMap = cassandraCityService.getUserListWhoLikeCity(id);
        List<UserDto> userDtoList = userLogic.fromUserMap(userMap);
        return new ResponseEntity<>(userDtoList, OK);
    }

    @RequestMapping(value = "/liked", method = GET)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity getLikedCities(@AuthenticationPrincipal UserDto userDto) {
        List<String> cities = Arrays.asList("Berlin", "Istanbul");
        return new ResponseEntity<>(cities, OK);
    }
}