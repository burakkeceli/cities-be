package com.cities.friend;

import com.cities.model.user.User;
import com.cities.service.friendship.FriendshipService;
import com.cities.user.UserLogic;
import com.cities.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class FriendshipLogic {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserLogic userConverter;

    public List<UserDto> getPendingRequestUserList(Integer userId) {
        List<User> users = friendshipService.getPendingRequests(userId);
        return users.stream().map(user -> userConverter.fromUser(user)).collect(toList());
    }
}
