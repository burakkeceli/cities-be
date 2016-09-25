package com.cities.dao;

import com.cities.model.User;
import com.cities.model.UserRole;

import java.util.List;

public interface UserDAO {

    void save(User p);

    User get(String userName);

    UserRole get(Integer id);

    List<User> list();

}
