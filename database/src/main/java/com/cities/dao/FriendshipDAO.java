package com.cities.dao;

import com.cities.model.friend.Friendship;
import com.cities.model.friend.FriendshipStatusEnum;
import com.cities.model.user.User;
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
        Session session = this.sessionFactory.getCurrentSession();
        session.save(friendship);
    }

    public void delete (Friendship friendship) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(friendship);
    }

    public List<Friendship> getAll(){
        Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Friendship.class).list();
    }

    public Friendship get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        cr.add(eq("id", id));
        if(cr.list().isEmpty())
            return null;
        return (Friendship)cr.list().get(0);
    }

    public Friendship getByUserIds(Integer userFromId, Integer userToId) {
        Criteria cr = getCriteria(userFromId, eq("userTo.id", userToId));
        if(cr.list().isEmpty())
            return null;
        return (Friendship)cr.list().get(0);
    }

    public List<Friendship> getFriendsOfUser(Integer userFromId) {
        Criteria cr = getCriteria(userFromId, eq("friendshipStatusEnum", ACTIVE));
        return cr.list();
    }

    public List<Friendship> getFriendRequestsOfUser(Integer userFromId) {
        Criteria cr = getCriteria(userFromId, eq("friendshipStatusEnum", PENDING));
        return cr.list();
    }

    private Criteria getCriteria(Integer userFromId, SimpleExpression expression) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        cr.add(eq("userFrom.id", userFromId))
                .add(expression);
        return cr;
    }
}
