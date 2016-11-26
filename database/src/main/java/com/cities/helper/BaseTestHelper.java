package com.cities.helper;

import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import com.cities.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseTestHelper {

    @Autowired
    private UserService userService;
    @Autowired
    private FriendshipService friendshipService;

    public User createUserWithUserRole(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.saveWithRoleUser(user);
        return user;
    }

    public void createFriendshipRequest(User userFrom, User userTo) {
        friendshipService.savePendingRequest(userFrom.getId(), userTo.getId());
    }
}
