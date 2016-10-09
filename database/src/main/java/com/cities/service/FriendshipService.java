package com.cities.service;

import com.cities.dao.FriendshipDAO;
import com.cities.model.friend.Friendship;
import com.cities.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendshipService {

    @Autowired
    private FriendshipDAO friendshipDAO;

    public void save(Friendship friendship) {
        friendshipDAO.save(friendship);
        changeFriendships(friendship);
        friendshipDAO.save(friendship);
    }

    private void changeFriendships(Friendship friendship) {
        User userFrom = friendship.getUserFrom();
        friendship.setUserFrom(friendship.getUserTo());
        friendship.setUserTo(userFrom);
    }
}
