package com.cities.dao;

import com.cities.model.friend.Friendship;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FriendshipDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Friendship friendship) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(friendship);
    }

    public Friendship get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Friendship.class);
        cr.add(Restrictions.eq("id", id));
        if(cr.list().isEmpty())
            return null;
        return (Friendship)cr.list().get(0);
    }
}
