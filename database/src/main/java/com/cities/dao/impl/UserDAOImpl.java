package com.cities.dao.impl;

import com.cities.dao.UserDAO;
import com.cities.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        u.setId(1);
        //Transaction trans=session.beginTransaction();
        session.save(u);
        //trans.commit();
    }

    @Override
    public List<User> list() {
        return null;
    }
}
