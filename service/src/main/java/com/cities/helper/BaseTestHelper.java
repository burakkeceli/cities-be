package com.cities.helper;

import com.cities.model.city.City;
import com.cities.model.country.Country;
import com.cities.model.user.User;
import com.cities.service.city.CityService;
import com.cities.service.friendship.FriendshipService;
import com.cities.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.cities.model.user.UserRoleEnum.ROLE_USER;

@Component
public class BaseTestHelper {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private CityService cityService;

    public User createUserWithUserRole(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user, ROLE_USER);
        return user;
    }

    public void createFriendshipRequest(User userFrom, User userTo) {
        friendshipService.savePendingRequest(userFrom.getId(), userTo.getId());
    }

    public City createCity(Integer countryId, String cityName) {
        City city = new City();
        city.setName(cityName);
        city.setCountryId(countryId);
        cityService.saveCity(city);
        return city;
    }

    public Country createCountry(String countryName, String capital) {
        Country country = new Country();
        country.setName(countryName);
        country.setCapital(capital);
        cityService.saveCountry(country);
        return country;
    }
}
