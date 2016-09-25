package com.cities.dao;

import com.cities.model.User;

import java.util.List;

public interface UserDAO {

    public void save(User p);

    public List<User> list();

}
