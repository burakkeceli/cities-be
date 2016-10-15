package com.cities.dao;

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

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class FriendshipDAOITest extends AbstractBaseITest{

    @Autowired
    private FriendshipDAO friendshipDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void shouldAddSeveralUsersAsFriends() {
        // given
        UserRole role = userDAO.getRole(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo1 = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo2 = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userDAO.save(userFrom);
        userDAO.save(userTo1);
        userDAO.save(userTo2);

        // when
        Friendship friendship = new Friendship();
        friendship.setFriendshipStatusEnum(ACTIVE);
        friendship.setUserTo(userTo1);
        friendship.setUserFrom(userFrom);
        friendshipDAO.save(friendship);

        Friendship friendship2 = new Friendship();
        friendship2.setFriendshipStatusEnum(ACTIVE);
        friendship2.setUserTo(userTo2);
        friendship2.setUserFrom(userFrom);
        friendshipDAO.save(friendship2);

        // then
        List<Friendship> friendshipList = friendshipDAO.getAll();
        friendshipList = friendshipDAO.getFriendsOfUser(userFrom.getId());
        List<User> collect = friendshipList.stream().filter(friendShip -> friendShip.getUserFrom().getId() == userFrom.getId()).map(Friendship::getUserTo).collect(toList());
        assertThat(collect).contains(userTo1);
        assertThat(collect).contains(userTo2);
    }

    @Test
    public void shouldNotDeleteUserWhenFriendshipDeleted() {
        // given
        UserRole role = userDAO.getRole(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userDAO.save(userFrom);
        userDAO.save(userTo);

        // and
        Friendship friendship = new Friendship();
        friendship.setFriendshipStatusEnum(ACTIVE);
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        friendshipDAO.save(friendship);

        // when
        friendship = friendshipDAO.getByUserIds(userFrom.getId(), userTo.getId());
        friendshipDAO.delete(friendship);
        friendship = friendshipDAO.getByUserIds(userFrom.getId(), userTo.getId());
        assertThat(friendship).isNull();

        // then
        assertThat(userDAO.getAll()).isNotEmpty();
        User fetchedUserTo = userDAO.get(userTo.getName());
        User fetchedUserFrom = userDAO.get(userFrom.getName());

        assertThat(fetchedUserFrom).isNotNull();
        assertThat(fetchedUserFrom.getName()).isEqualToIgnoringCase(userFrom.getName());
        assertThat(fetchedUserTo).isNotNull();
        assertThat(fetchedUserTo.getName()).isEqualToIgnoringCase(userTo.getName());
    }

    @Test
    public void shouldNotAddTwoSameUserAsFriends() {
        // given
        UserRole role = userDAO.getRole(1);

        User userFrom = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");
        User userTo = createUser(role, UUID.randomUUID().toString(), "123", "Turkey");

        // and
        userDAO.save(userFrom);
        userDAO.save(userTo);

        // and
        Friendship friendship = new Friendship();
        friendship.setFriendshipStatusEnum(ACTIVE);
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        friendshipDAO.save(friendship);

        // when
        Friendship friendship2 = new Friendship();
        friendship2.setFriendshipStatusEnum(ACTIVE);
        friendship2.setUserTo(userTo);
        friendship2.setUserFrom(userFrom);
        try {
            friendshipDAO.save(friendship2);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(DataIntegrityViolationException.class);
        }
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
