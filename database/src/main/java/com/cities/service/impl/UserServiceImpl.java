package com.cities.service.impl;

import com.cities.dao.UserDAO;
import com.cities.model.User;
import com.cities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void save(User user) {
        userDAO.save(user);
    }
}
