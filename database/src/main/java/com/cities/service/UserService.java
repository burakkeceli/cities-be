package com.cities.service;

import com.cities.dao.UserDAO;
import com.cities.model.User;
import com.cities.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public void save(User user) {
        userDAO.save(user);
    }

    public User get(String name) {
        return userDAO.get(name);
    }

    public UserRole get(Integer id) {
        return userDAO.get(id);
    }
}
