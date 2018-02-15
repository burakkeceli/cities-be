package com.cities.service.user;

import com.cities.dao.UserDAO;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.model.user.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.singletonList;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user, Set<UserRole> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRoles(roles);
        userDAO.save(user);
    }

    public User saveUser(User user, UserRoleEnum role) {
        UserRole userRole = getRoleByName(role.getName());
        saveUser(user, new HashSet<>(singletonList(userRole)));
        return user;
    }

    public void saveUserRole(UserRole userRole) {
        userDAO.save(userRole);
    }

    public User getUserByName(String userName) {
        return userDAO.get(userName);
    }

    public User getUserById(Integer userId) {
        return userDAO.get(userId);
    }

    public UserRole getRoleById(Integer roleId) {
        return userDAO.getRole(roleId);
    }

    public UserRole getRoleByName(String roleName) {
        return userDAO.getRole(roleName);
    }

    public void delete(User user) {
        userDAO.delete(user);
    }
}
