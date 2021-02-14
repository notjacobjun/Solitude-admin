package com.Solitude.DAO;

public interface EventDAO {
    public void BookEvent(int startTime, int endTime, int numberOfUsers);

    public void RegisterUser(String name, String email, int phoneNumber);
}
