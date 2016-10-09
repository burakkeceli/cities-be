package com.cities.dao;

import com.cities.model.friend.Friendship;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        cr.add(eq("userFrom.id", userFromId))
          .add(eq("userTo.id", userToId));
        if(cr.list().isEmpty())
            return null;
        return (Friendship)cr.list().get(0);
    }

    public List<Friendship> getFriendsOfUser(Integer userFromId) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        cr.add(eq("userFrom.id", userFromId));
        return cr.list();
    }
}
