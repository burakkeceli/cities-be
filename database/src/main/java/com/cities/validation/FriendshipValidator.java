package com.cities.validation;

import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FriendshipValidator {

    @Autowired
    private FriendshipService friendshipService;

    public boolean validateUserHasFriend(Integer userFromId, Integer userToId) {
        return friendshipService.doesUserHaveFriend(userFromId, userToId);
    }

    public boolean hasFriendshipRequest(Integer userFromId, Integer userToId) {
        List<User> users = friendshipService.getPendingRequests(userFromId);
        Optional fetchedUser = users.stream().filter(user -> user.getId().equals(userToId)).findFirst();
        return fetchedUser.isPresent();
    }
}
