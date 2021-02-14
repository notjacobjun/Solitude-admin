package com.Solitude.DAO;

import com.Solitude.Entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDAOImplementation implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUser(int userId) {
        Session currentSession = sessionFactory.getCurrentSession();

        // retrieve the user by using hibernate
        return currentSession.get(User.class, userId);
    }

}
