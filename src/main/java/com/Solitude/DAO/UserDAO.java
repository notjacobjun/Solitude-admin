package com.Solitude.DAO;

import com.Solitude.Entity.User;

import java.util.List;

public interface UserDAO {
    public User getUser(int userId);

    public List<User> getAllUsers();
}
