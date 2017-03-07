package com.cities.dao;

import com.cities.model.comment.Comment;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
public class CommentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.save(comment);
    }

    public Comment getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Comment.class);
        cr.add(eq("id", id));

        if (cr.list().isEmpty()) {
            return null;
        }
        return (Comment)cr.list().get(0);
    }
}
