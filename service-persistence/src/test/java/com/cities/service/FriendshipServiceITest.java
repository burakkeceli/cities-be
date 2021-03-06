package com.cities.service;

import com.cities.dao.FriendshipDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import com.cities.service.friendship.FriendshipService;
import com.cities.service.user.UserService;
import com.cities.test.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class FriendshipServiceITest extends AbstractBaseTest {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendshipDAO friendshipDAO;

    @Before
    public void setup() {

    }

    @Test
    public void shouldSendPendingRequest() {
        // given
        UserRole role = userService.getRoleById(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userService.saveUser(userFrom, ROLE_USER);
        userService.saveUser(userTo, ROLE_USER);

        // and
        Friendship friendship = new Friendship();
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        friendshipService.savePendingRequest(friendship);

        // when
        List<User> users = friendshipService.getPendingRequests(userTo.getId());

        // then
        assertThat(users).contains(userFrom);
    }

    @Test
    public void shouldAcceptPendingRequest() {
        // given
        UserRole role = userService.getRoleById(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userService.saveUser(userFrom, ROLE_USER);
        userService.saveUser(userTo, ROLE_USER);

        // and
        Friendship friendship = new Friendship();
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        friendshipService.savePendingRequest(friendship);

        // when
        friendshipService.saveFriendship(userFrom.getId(), userTo.getId());

        // then
        friendship = friendshipService.getFriendship(userFrom.getId(), userTo.getId());
        assertThat(friendship.getFriendshipStatusEnum()).isEqualTo(ACTIVE);
    }

    private User createUser(UserRole role, String name, String password, String country) {
        User user = new User();
        user.setFirstName(name);
        user.setUsername(name);
        user.setPassword(password);
        user.setUserRoles(new HashSet<>(singletonList(role)));
        user.setCountry(country);
        user.setEmail(UUID.randomUUID().toString());
        return user;
    }
}
