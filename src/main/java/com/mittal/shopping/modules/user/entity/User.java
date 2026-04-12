package com.mittal.shopping.modules.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class User {

    @Id
    int id;

    String name;

    @Column(unique = true)
    String email;

    String password;
    String role;
    String createdAt;

}
