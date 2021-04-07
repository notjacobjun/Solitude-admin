package com.Solitude.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "app_user", schema = "public")
public class AppUser extends AuditModel {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email")
    private String email;
    @Column(name = "display_name")
    private String displayName;
}