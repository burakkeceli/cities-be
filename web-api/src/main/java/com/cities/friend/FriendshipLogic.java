package com.cities.friend;

import com.cities.model.friend.Friendship;
import com.cities.model.user.User;
import com.cities.service.FriendshipService;
import com.cities.user.UserDtoToUserConverter;
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
    private UserDtoToUserConverter userConverter;

    public List<UserDto> getPendingRequestUserList(Integer userId) {
        List<User> users = friendshipService.getPendingRequests(userId);
        return users.stream().map(user -> userConverter.fromUser(user)).collect(toList());
    }

    public void acceptFriendshipRequest(Integer userFromId, Integer userToId) {
        friendshipService.acceptFriendshipRequest(userFromId, userToId);
    }
}
