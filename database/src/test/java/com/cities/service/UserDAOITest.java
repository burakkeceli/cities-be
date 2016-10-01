package com.cities.service;

import com.cities.config.PersistenceConfig;
import com.cities.dao.UserDAO;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserDAOITest {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldCreateUserWithUserRole() {
        // given
        UserRole role = userDAO.getRole(1);

        User user = new User();
        String name = UUID.randomUUID().toString();
        String password = "123";
        String country = "Turkey";
        user.setName(name);
        user.setUsername(name);
        user.setPassword(password);
        user.setUserRoles(new HashSet<>(singletonList(role)));
        user.setCountry(country);

        // when
        userDAO.save(user);

        // then
        user = userDAO.get(name);
        assertThat(user.getName()).isEqualToIgnoringCase(name);
        assertThat(user.getUsername()).isEqualToIgnoringCase(name);
        assertThat(user.getUserRoles()).contains(role);
        assertThat(user.getCountry()).isEqualToIgnoringCase(country);
    }

    @Test
    public void shouldDeleteUserButShouldNotDeleteUserRole() {
        // given
        UserRole role = userDAO.getRole(1);

        User user = new User();
        String name = UUID.randomUUID().toString();
        String password = "123";
        String country = "Turkey";
        user.setName(name);
        user.setUsername(name);
        user.setPassword(password);
        user.setUserRoles(new HashSet<>(singletonList(role)));
        user.setCountry(country);
        userDAO.save(user);

        // when
        userDAO.delete(user);

        // then
        user = userDAO.get(name);
        assertThat(user).isNull();

        UserRole fetchedRole = userDAO.getRole(role.getId());
        assertThat(fetchedRole).isNotNull();
        assertThat(fetchedRole.getRole()).isEqualToIgnoringCase(role.getRole());
    }
}
