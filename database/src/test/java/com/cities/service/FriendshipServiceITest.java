package com.cities.service;

import com.cities.base.AbstractBaseITest;
import com.cities.config.PersistenceConfig;
import com.cities.dao.FriendshipDAO;
import com.cities.dao.UserDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static com.cities.model.user.UserRoleEnum.ROLE_USER;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class FriendshipServiceITest extends AbstractBaseITest {

    @Autowired
    private FriendshipService friendshipService;
    @Autowired
    private UserService userService;
    @Autowired
    private FriendshipDAO friendshipDAO;

    @Test
    public void shouldSendPendingRequest() {
        // given
        UserRole role = userService.getRole(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userService.saveWithRoleUser(userFrom);
        userService.saveWithRoleUser(userTo);

        // and
        Friendship friendship = new Friendship();
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        friendshipService.savePendingRequest(friendship);

        // when
        List<User> users = friendshipService.getPendingRequests(userFrom.getId());

        // then
        assertThat(users).contains(userTo);
    }

    @Test
    public void shouldAcceptPendingRequest() {
        // given
        UserRole role = userService.getRole(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userService.saveWithRoleUser(userFrom);
        userService.saveWithRoleUser(userTo);

        // and
        Friendship friendship = new Friendship();
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        friendshipService.savePendingRequest(friendship);

        // when
        friendshipService.createFriendship(userFrom.getId(), userTo.getId());

        // then
        friendship = friendshipService.getFriendship(userFrom.getId(), userTo.getId());
        assertThat(friendship.getFriendshipStatusEnum()).isEqualTo(ACTIVE);
    }

    private User createUser(UserRole role, String name, String password, String country) {
        User user = new User();
        user.setName(name);
        user.setUsername(name);
        user.setPassword(password);
        user.setUserRoles(new HashSet<>(singletonList(role)));
        user.setCountry(country);
        return user;
    }
}
