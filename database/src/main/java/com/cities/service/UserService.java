package com.cities.service;

import com.cities.dao.UserDAO;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static com.cities.model.user.UserRoleEnum.ROLE_ADMIN;
import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static java.util.Collections.singletonList;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void save(User user, HashSet<UserRole> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRoles(roles);
        userDAO.save(user);
    }

    public void saveWithRoleUser(User user) {
        UserRole userRole = getRole(ROLE_USER.getName());
        save(user, new HashSet<>(singletonList(userRole)));
    }

    public void saveWithRoleAdmin(User user) {
        UserRole userRole = getRole(ROLE_ADMIN.getName());
        save(user, new HashSet<>(singletonList(userRole)));
    }

    public User get(String name) {
        return userDAO.get(name);
    }

    public UserRole get(Integer id) {
        return userDAO.getRole(id);
    }

    public UserRole getRole(String role) {
        return userDAO.getRole(role);
    }

    public void delete(User user) {
        userDAO.delete(user);
    }
}
