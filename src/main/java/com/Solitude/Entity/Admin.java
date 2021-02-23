package com.Solitude.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Admin")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
public class Admin extends AuditModel{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long adminId;
    @Column(name = "adminName")
    private String adminName;
    @Column(name = "adminEmail")
    private String email;
    // make sure to encrypt this into the database
    @Column(name = "adminPasswod")
    private String password;
}
