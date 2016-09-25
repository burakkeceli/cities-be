package com.cities.service.impl;

import com.cities.dao.UserDAO;
import com.cities.model.User;
import com.cities.model.UserRole;
import com.cities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public User get(String name) {
        return userDAO.get(name);
    }

    @Override
    public UserRole get(Integer id) {
        return userDAO.get(id);
    }
}
