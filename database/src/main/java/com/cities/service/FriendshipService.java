package com.cities.service;

import com.cities.dao.FriendshipDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.friend.FriendshipStatusEnum;
import com.cities.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static com.cities.model.friend.FriendshipStatusEnum.PENDING;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class FriendshipService {

    @Autowired
    private FriendshipDAO friendshipDAO;

    private void save(Friendship friendship) {
        save(friendship, ACTIVE);
        //changeFriendships(friendship);
        //friendshipDAO.save(friendship);
    }

    public void createFriendship(Integer userFromId, Integer userToId) {
        Friendship friendship = getFriendship(userFromId, userToId);
        save(friendship);
    }

    public Friendship getFriendship(Integer userFromId, Integer userToId) {
        return friendshipDAO.getByUserIds(userFromId, userToId);
    }

    public void savePendingRequest(Friendship friendship) {
        save(friendship, PENDING);
    }

    private void save(Friendship friendship, FriendshipStatusEnum statusEnum) {
        friendship.setFriendshipStatusEnum(statusEnum);
        friendshipDAO.save(friendship);
    }

    public List<User> getPendingRequests(Integer userId) {
        List<Friendship> friendshipList = friendshipDAO.getFriendRequestsOfUser(userId);
        return friendshipList.stream().map(Friendship::getUserTo).collect(toList());
    }

    public boolean doesUserHaveFriend(Integer userFromId, Integer userToId) {
        Friendship friendship = friendshipDAO.getByUserIds(userFromId, userToId, ACTIVE);
        return friendship != null;
    }

    private void changeFriendships(Friendship friendship) {
        User userFrom = friendship.getUserFrom();
        friendship.setUserFrom(friendship.getUserTo());
        friendship.setUserTo(userFrom);
    }
}