package com.cities.dao;

import com.cities.model.User;
import com.cities.model.UserRole;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    public UserRole get(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        return (UserRole) session.get(UserRole.class, id);
    }

    public List<User> list() {
        return null;
    }
}
