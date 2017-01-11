package com.cities.main;

import com.cities.config.PersistenceConfig;
import com.cities.dao.FriendshipDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.service.FriendshipService;
import com.cities.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashSet;
import java.util.List;

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
        FriendshipService friendshipService = context.getBean(FriendshipService.class);
        FriendshipDAO friendshipDAO = context.getBean(FriendshipDAO.class);
        initialize2Users(userService);
        createPendingRequest(userService, friendshipService);
        //getPendingRequestList(userService, friendshipService);
    }

    private static void getPendingRequestList(UserService userService, FriendshipService friendshipService) {
        User user1 = userService.get("Burak");
        List<User> userList = friendshipService.getPendingRequests(user1.getId());
    }

    private static void createPendingRequest(UserService userService, FriendshipService friendshipService) {
        User user1 = userService.get("Burak");
        User user2 = userService.get("Deneme");

        Friendship friendship = new Friendship();
        friendship.setUserTo(user2);
        friendship.setUserFrom(user1);
        friendshipService.savePendingRequest(friendship);
/*
        Friendship friendship2 = new Friendship();
        friendship2.setUserTo(user1);
        friendship2.setUserFrom(user2);
        friendshipService.savePendingRequest(friendship2);*/
    }

    private static void initialize2Users(UserService userService) {
        // create user roles
        UserRole roleAdmin = createRole("ROLE_ADMIN");
        UserRole roleUser = createRole("ROLE_USER");

        // create 2 users
        createUser(userService, "Burak", new HashSet<>(asList(roleAdmin, roleUser)));
        createUser(userService, "Deneme", new HashSet<>(asList(roleUser)));
    }

    private static UserRole createRole(String role_admin) {
        UserRole roleAdmin = new UserRole();
        roleAdmin.setRole(role_admin);
        return roleAdmin;
    }

    private static void createUser(UserService userService, String burak, HashSet<UserRole> roles) {
        User u = new User();
        u.setPassword("123");
        u.setCountry("Germany");
        u.setFirstName(burak);
        userService.save(u, roles);
    }
}
