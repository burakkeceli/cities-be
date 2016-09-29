package com.cities.main;

import com.cities.config.PersistenceConfig;
import com.cities.model.User;
import com.cities.model.UserRole;
import com.cities.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.Set;

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
        UserRole uRole = userService.get(1);
        Set roleSet = new HashSet();
        roleSet.add(uRole);

        User u = new User();
        u.setPassword("123");
        u.setCountry("Germany");
        u.setName("Burak");
        u.setUserRoles(roleSet);
        userService.save(u);

        User user = userService.get("Burak");

        System.out.println("Person::"+user);
    }
}
