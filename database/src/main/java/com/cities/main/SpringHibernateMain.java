package com.cities.main;

import com.cities.config.PersistenceConfig;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class SpringHibernateMain {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PersistenceConfig persistenceConfig;

    private Session session;

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        //session = sessionFactory.openSession();
        UserService userService = context.getBean(UserService.class);

        // create user roles
        UserRole roleAdmin = new UserRole();
        roleAdmin.setRole("ROLE_ADMIN");

        UserRole roleUser = new UserRole();
        roleUser.setRole("ROLE_USER");

        // create 2 users
        User u = new User();
        u.setPassword("123");
        u.setCountry("Germany");
        u.setName("Burak");
        userService.save(u, new HashSet<>(asList(roleAdmin, roleUser)));

        User u2 = new User();
        u2.setPassword("123");
        u2.setCountry("Germany");
        u2.setName("Deneme");
        userService.save(u2, new HashSet<>(asList(roleUser)));
    }
}
