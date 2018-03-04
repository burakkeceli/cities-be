package com.cities.service.city;


import com.cities.cassandra.CassandraService;
import com.cities.dao.CityDAO;
import com.cities.model.city.City;
import com.cities.model.user.User;
import com.cities.service.user.UserService;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.cities.cassandra.CassandraQueryUtil.*;

@Component
public class CassandraCityService {

    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private CassandraService cassandraService;
    @Autowired
    private UserService userService;
/*
    public void saveUserInfoWhoLikesCity(Integer userId, Integer cityId) {
        saveCityLikeUser(userId, cityId);
        saveUserLikeCity(userId, cityId);
    }

    private void saveUserLikeCity(Integer userId, Integer cityId) {
        City city = cityDAO.getById(cityId);
        String insertUserLikeCityQuery = getQuery(INSERT_USER_LIKE_CITY, userId.toString(), cityId.toString(), city.getName());
        cassandraService.execute(insertUserLikeCityQuery);
    }

    private void saveCityLikeUser(Integer userId, Integer cityId) {
        User user = userService.getUserById(userId);
        String insertCityLikeUserQuery = getQuery(INSERT_CITY_LIKE_USER, cityId.toString(), userId.toString(), user.getUsername());
        cassandraService.execute(insertCityLikeUserQuery);
    }

    public Map<Integer, String> getUserListWhoLikeCity(Integer cityId) {
        String selectUserList = getQuery(SELECT_CITY_LIKE_USER, cityId.toString());
        ResultSet resultSet = cassandraService.execute(selectUserList);

        Map<Integer, String> userDetailMap = new HashMap<>();
        for (Row row : resultSet) {
            Integer fetchedUserId = row.getInt("user_id");
            String fetchedUserName = row.getString("user_name");
            userDetailMap.put(fetchedUserId, fetchedUserName);
        }
        return userDetailMap;
    }*/
}
