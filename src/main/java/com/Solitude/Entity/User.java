package com.Solitude.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "display_name")
    private String displayName;
}
