package com.cities.main;

import com.cities.model.User;
import com.cities.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringHibernateMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        UserService userService = context.getBean(UserService.class);

        User user = new User();
        user.setName("Pankaj"); user.setCountry("India");

        userService.save(user);

        System.out.println("Person::"+user);
        context.close();
    }
}
