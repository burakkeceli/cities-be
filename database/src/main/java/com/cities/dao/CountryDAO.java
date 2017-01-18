package com.cities.dao;

import com.cities.model.country.Country;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
public class CountryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Country country) {
        Session session = sessionFactory.getCurrentSession();
        session.save(country);
    }

    public List<Country> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Country.class).list();
    }

    public Country getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Country.class);
        cr.add(eq("id", id));

        if (cr.list().isEmpty()) {
            return null;
        }
        return (Country)cr.list().get(0);
    }
}
