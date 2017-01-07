package com.cities.dao;

import com.cities.model.friend.Friendship;
import com.cities.model.friend.FriendshipStatusEnum;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cities.model.friend.FriendshipStatusEnum.ACTIVE;
import static com.cities.model.friend.FriendshipStatusEnum.PENDING;
import static org.hibernate.criterion.Restrictions.eq;

@Repository
public class FriendshipDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Friendship friendship) {
        Session session = sessionFactory.getCurrentSession();
        session.save(friendship);
    }

    public void delete (Friendship friendship) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(friendship);
    }

    public List<Friendship> getAll(){
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Friendship.class).list();
    }

    public Friendship get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        cr.add(eq("id", id));
        return getFriendship(cr);
    }

    public Friendship getByUserIds(Integer userFromId, Integer userToId, FriendshipStatusEnum status) {
        Criteria cr = getCriteria(eq("userTo.id", userToId), eq("userFrom.id", userFromId), eq("friendshipStatusEnum", status));
        return getFriendship(cr);
    }

    public Friendship getByUserIds(Integer userFromId, Integer userToId) {
        Criteria cr = getCriteria(eq("userTo.id", userToId), eq("userFrom.id", userFromId));
        return getFriendship(cr);
    }

    private Friendship getFriendship(Criteria cr) {
        if (cr.list().isEmpty()) {
            return null;
        }
        return (Friendship)cr.list().get(0);
    }

    public List<Friendship> getFriendsOfUser(Integer userFromId) {
        Criteria cr = getCriteria(eq("friendshipStatusEnum", ACTIVE), eq("userFrom.id", userFromId));
        return cr.list();
    }

    public List<Friendship> getFriendRequestsOfUser(Integer userFromId) {
        Criteria cr = getCriteria(eq("friendshipStatusEnum", PENDING), eq("userFrom.id", userFromId));
        return cr.list();
    }

    private Criteria getCriteria(SimpleExpression... expressions) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        for (SimpleExpression expression : expressions) {
            cr.add(expression);
        }
        return cr;
    }
}
