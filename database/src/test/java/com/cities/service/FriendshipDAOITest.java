package com.cities.service;

import com.cities.config.PersistenceConfig;
import com.cities.dao.FriendshipDAO;
import com.cities.dao.UserDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.friend.FriendshipStatusEnum;
import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class FriendshipDAOITest {

    @Autowired
    private FriendshipDAO friendshipDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldCreateUserWithUserRole() {
        // given
        UserRole role = userDAO.getRole(1);
        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // when
        userDAO.save(userFrom);
        userDAO.save(userTo);

        Friendship friendship = new Friendship();
        friendship.setFriendshipStatusEnum(ACTIVE);
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);

        // when
        friendshipDAO.save(friendship);

        // then
        friendship = friendshipDAO.get(1);
        assertThat(friendship.getUserFrom().getName()).isEqualToIgnoringCase(userFrom.getName());
        assertThat(friendship.getUserTo().getName()).isEqualToIgnoringCase(userTo.getName());
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
