package com.cities.validation;

import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipValidator {

    @Autowired
    private FriendshipService friendshipService;

    public boolean validateConflictRequest(User userFrom, User userTo) {

        List<User> userList = friendshipService.getPendingRequests(userTo.getId());
        if (userList.stream().filter(x -> x.getId() == userFrom.getId()).findFirst().isPresent()) {
            return false;
        }
        return true;
    }
}
