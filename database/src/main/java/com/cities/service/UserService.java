package com.cities.service;

import com.cities.model.User;
import com.cities.model.UserRole;

public interface UserService {

    void save(User user);

    User get(String userName);

    UserRole get(Integer id);
}
