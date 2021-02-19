package com.Solitude.DAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventDAOImplementation implements EventDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    // don't think this is necessary either
    public void RegisterUser(String name, String email, int phoneNumber) {
    }

}
