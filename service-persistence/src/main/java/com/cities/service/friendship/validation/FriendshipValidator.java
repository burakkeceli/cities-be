package com.cities.service.friendship.validation;

import com.cities.model.user.User;
import com.cities.service.friendship.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FriendshipValidator {

    @Autowired
    private FriendshipService friendshipService;

    public boolean hasUserFriend(Integer userFromId, Integer userToId) {
        return friendshipService.doesUserHaveFriend(userFromId, userToId);
    }

    public boolean hasFriendshipRequest(Integer userToId, Integer userFromId) {
        List<User> users = friendshipService.getPendingRequests(userFromId);
        Optional<User> fetchedUser = users.stream().filter(user -> user.getId().equals(userToId)).findFirst();
        return fetchedUser.isPresent();
    }
}
