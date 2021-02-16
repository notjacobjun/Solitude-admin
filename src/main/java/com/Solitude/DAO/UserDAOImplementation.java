package com.Solitude.DAO;

import com.Solitude.Entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImplementation implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(int userId) {
        Session currentSession = sessionFactory.getCurrentSession();

        // retrieve the user by using hibernate
        return currentSession.get(User.class, userId);
    }

    @Override
    public List<User> getAllUsers() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query query = currentSession.createQuery("SELECT * from Users", User.class);

        List<User> users = query.getResultList();
        return users;
    }
}
