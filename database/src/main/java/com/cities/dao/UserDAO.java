package com.cities.dao;

import com.cities.model.user.User;
import com.cities.model.user.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
    }

    public User get(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(User.class);
        cr.add(Restrictions.eq("name", name));
        if(cr.list().isEmpty())
            return null;
        return (User)cr.list().get(0);
    }

    public UserRole getRole(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (UserRole) session.get(UserRole.class, id);
    }

    public UserRole getRole(String role) {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(UserRole.class);
        cr.add(Restrictions.eq("role", role));
        if(cr.list().isEmpty())
            return null;
        return (UserRole)cr.list().get(0);
    }

    public List<User> list() {
        return null;
    }

    public void delete (User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(user);
    }
}
