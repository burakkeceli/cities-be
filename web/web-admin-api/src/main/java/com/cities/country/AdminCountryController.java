package com.cities.country;

import com.cities.city.CityProducer;
import com.cities.city.model.CityDto;
import com.cities.country.model.CountryDto;
import com.cities.model.city.City;
import com.cities.model.country.Country;
import com.cities.service.city.CityService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequestMapping(value = ADMIN + COUNTRY, produces = APPLICATION_JSON_UTF8_VALUE)
public class AdminCountryController {

    @Autowired
    private CityService cityService;
    @Autowired
    private CountryLogic countryLogic;
    @Autowired
    private CountryProducer countryProducer;
    @Autowired
    private CityProducer cityProducer;

    @RequestMapping(method = GET)
    public ResponseEntity getAll(HttpServletRequest request) {
        List<Country> countryList = cityService.getAllCountries();
        List<CountryDto> countryDtoList = countryLogic.getCountryDtoList(countryList);
        return new ResponseEntity<>(countryDtoList, OK);
    }

    @RequestMapping(method = POST)
    public ResponseEntity saveCountryList(@RequestBody List<CountryDto> countryList) {
        try {
            for (CountryDto countryDto : countryList) {
                Country country = countryLogic.getCountry(countryDto);
                countryProducer.sendCountryMessage(country);
            }
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            log.error("ERROR => ", e);
            return new ResponseEntity<>(e.getMessage(), OK);
        }
    }

    @RequestMapping(value = "/city", method = POST)
    public ResponseEntity saveCityList(@RequestBody List<CityDto> cityDtoList) {
        try {
            for (CityDto cityDto: cityDtoList) {
                City city = new City();
                city.setCountryId(cityDto.getCountryId());
                city.setName(cityDto.getName());
                log.debug("city before save");
                cityProducer.sendCityMessage(city);
            }
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            log.error("ERROR => ", e);
            return new ResponseEntity<>(e.getMessage(), OK);
        }
    }
}