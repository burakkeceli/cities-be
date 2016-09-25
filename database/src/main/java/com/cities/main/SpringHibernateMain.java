package com.cities.main;

import com.cities.model.User;
import com.cities.model.UserRole;
import com.cities.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringHibernateMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        UserService userService = context.getBean(UserService.class);
        UserRole uRole = userService.get(1);
        User user = userService.get("Burak");

        System.out.println("Person::"+user);
        context.close();
    }
}
