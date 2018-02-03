package com.cities.country;

import com.cities.country.model.CountryDto;
import com.cities.model.country.Country;
import com.cities.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cities.ApiConstants.Urls.COUNTRY;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = COUNTRY, produces = APPLICATION_JSON_UTF8_VALUE)
public class CountryController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryLogic countryLogic;

    @RequestMapping(method = GET)
    public ResponseEntity getAll(HttpServletRequest request) {
        List<Country> countryList = cityService.getAllCountries();
        List<CountryDto> countryDtoList = countryLogic.getCountryDtoList(countryList);
        return new ResponseEntity<>(countryDtoList, OK);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity getById(@PathVariable Integer id, HttpServletRequest request) {
        Country country = cityService.getCountryById(id);
        if (country == null) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        CountryDto countryDto = countryLogic.getCountryDto(country);
        return new ResponseEntity<>(countryDto, OK);
    }
}