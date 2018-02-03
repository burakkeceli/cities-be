package com.cities.country;

import com.cities.async.country.CountryProducer;
import com.cities.country.model.CountryDto;
import com.cities.model.country.Country;
import com.cities.service.city.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.cities.ApiConstants.Urls.ADMIN;
import static com.cities.ApiConstants.Urls.COUNTRY;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = ADMIN + COUNTRY, produces = APPLICATION_JSON_UTF8_VALUE)
public class AdminCountryController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryLogic countryLogic;
    @Autowired
    private CountryProducer countryProducer;

    @RequestMapping(method = GET)
    public ResponseEntity getAll(HttpServletRequest request) {
        List<Country> countryList = cityService.getAllCountries();
        List<CountryDto> countryDtoList = countryLogic.getCountryDtoList(countryList);
        return new ResponseEntity<>(countryDtoList, OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity saveCountryList(@RequestBody List<CountryDto> countryList) {
        for (CountryDto countryDto : countryList) {
            Country country = countryLogic.getCountry(countryDto);
            countryProducer.sendCountryMessage(country);
        }
        return new ResponseEntity<>(OK);
    }
}