package com.Solitude.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "admin")
// constructor, getter, setter, toString EqualsAndHashCode
@Data
public class Admin extends AuditModel{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long adminId;
    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "admin_email")
    private String email;
    // make sure to encrypt this into the database
    @Column(name = "admin_password")
    private String password;
}
