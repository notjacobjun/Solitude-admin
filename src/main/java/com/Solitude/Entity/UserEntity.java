package com.Solitude.Entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserEntity {
    @Id
    private String username;
    private String email;
    private int phoneNumber;
    private boolean status;


    public UserEntity() {
    }

    public UserEntity(String username, String email, int phoneNumber, boolean status) {
        username = this.username;
        email = this.email;
        phoneNumber = this.phoneNumber;
        status = this.status;
    }
}
