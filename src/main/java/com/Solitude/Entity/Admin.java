package com.Solitude.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin {
    @Id
    @Column(name = "adminName")
    private String adminName;
    @Column(name = "email")
    private String email;
    // make sure to encrypt this into the database
    @Column(name = "adminPasswod")
    private String password;

    public String getName() {
        return adminName;
    }

    public void setName(String username) {
        this.adminName = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String username, String email, String password) {
        this.adminName = username;
        this.email = email;
        this.password = password;
    }

    public Admin() {
    }

}
