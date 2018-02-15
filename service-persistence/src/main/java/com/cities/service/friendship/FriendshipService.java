package com.cities.service.friendship;

import com.cities.dao.FriendshipDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.friend.FriendshipStatusEnum;
import com.cities.model.user.User;
import com.cities.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class FriendshipService {

    @Autowired
    private FriendshipDAO friendshipDAO;
    @Autowired
    private UserService userService;

    private void save(Friendship friendship, FriendshipStatusEnum statusEnum) {
        friendship.setFriendshipStatusEnum(statusEnum);
        friendshipDAO.save(friendship);
    }

    public void saveFriendship(Integer userFromId, Integer userToId) {
        Friendship friendship = getFriendship(userFromId, userToId);
        save(friendship, FriendshipStatusEnum.ACTIVE);
    }

    public Friendship getFriendship(Integer userFromId, Integer userToId) {
        return friendshipDAO.getByUserIds(userFromId, userToId);
    }

    public void savePendingRequest(Friendship friendship) {
        save(friendship, FriendshipStatusEnum.PENDING);
    }

    public void savePendingRequest(Integer userFromId, Integer userToId) {
        Friendship friendship = createFriendship(userFromId, userToId);
        savePendingRequest(friendship);
    }

    public List<User> getPendingRequests(Integer userId) {
        List<Friendship> friendshipList = friendshipDAO.getFriendRequestsOfUser(userId);
        return friendshipList.stream().map(Friendship::getUserFrom).collect(toList());
    }

    public boolean doesUserHaveFriend(Integer userFromId, Integer userToId) {
        Friendship friendship = friendshipDAO.getByUserIds(userFromId, userToId, FriendshipStatusEnum.ACTIVE);
        return friendship != null;
    }

    public boolean isUserBlocked(Integer userId, Integer checkedUserId) {
        Friendship friendship = friendshipDAO.getByUserIds(userId, checkedUserId, FriendshipStatusEnum.BLOCKED);
        return friendship != null;
    }

    public void acceptFriendshipRequest(Integer userFromId, Integer userToId) {
        Friendship friendship = getFriendship(userFromId, userToId);
        save(friendship, FriendshipStatusEnum.ACTIVE);
    }

    public void rejectFriendshipRequest(Integer userFromId, Integer userToId) {
        Friendship friendship = getFriendship(userFromId, userToId);
        save(friendship, FriendshipStatusEnum.REJECTED);
    }

    public void processFriendshipRequest(Integer userFromId, Integer userToId, boolean accept) {
        if (accept) {
            acceptFriendshipRequest(userFromId, userToId);
        } else {
            rejectFriendshipRequest(userFromId, userToId);
        }
    }

    private Friendship createFriendship(Integer userFromId, Integer userToId) {
        Friendship friendship = new Friendship();
        User userFrom = userService.getUserById(userFromId);
        User userTo = userService.getUserById(userToId);
        friendship.setUserTo(userTo);
        friendship.setUserFrom(userFrom);
        return friendship;
    }
}