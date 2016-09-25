package com.cities.dao;

import com.cities.model.User;

import java.util.List;

public interface UserDAO {

    void save(User p);

    List<User> list();

}
