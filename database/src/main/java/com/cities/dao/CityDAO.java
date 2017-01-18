package com.cities.dao;

import com.cities.model.city.City;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;

@Repository
public class CityDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(City city) {
        Session session = sessionFactory.getCurrentSession();
        session.save(city);
    }

    public City getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(City.class);
        cr.add(eq("id", id));

        if (cr.list().isEmpty()) {
            return null;
        }
        return (City)cr.list().get(0);
    }

    public List<City> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(City.class).list();
    }
}
