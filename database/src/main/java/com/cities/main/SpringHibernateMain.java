package com.cities.main;

import com.cities.model.User;
import com.cities.model.UserRole;
import com.cities.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static java.util.Collections.singleton;

public class SpringHibernateMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        UserService userService = context.getBean(UserService.class);
        UserRole uRole = new UserRole();
        uRole.setRole("ROLE_USER");

        User user = new User();
        user.setName("Burak");
        user.setUserRoleSet(singleton(uRole));
        user.setCountry("Turkey");

        userService.save(user);

        System.out.println("Person::"+user);
        context.close();
    }
}
