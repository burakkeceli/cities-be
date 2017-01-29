package com.cities.service.city;

import com.cities.dao.CityDAO;
import com.cities.dao.CountryDAO;
import com.cities.model.city.City;
import com.cities.model.country.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityService {

    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private CountryDAO countryDAO;

    public List<City> getCities() {
        return cityDAO.getAll();
    }

    public City getById(Integer id) {
        return cityDAO.getById(id);
    }

    public void saveCountry(Country country) {
        countryDAO.save(country);
    }

    public void saveCity(City city) {
        cityDAO.save(city);
    }
}
