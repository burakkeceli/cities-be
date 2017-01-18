package com.cities.service.city;

import com.cities.dao.CityDAO;
import com.cities.model.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CityService {

    @Autowired
    private CityDAO cityDAO;

    public List<City> getAll() {
        return cityDAO.getAll();
    }
}
