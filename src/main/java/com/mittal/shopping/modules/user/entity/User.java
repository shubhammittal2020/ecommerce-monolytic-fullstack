package com.mittal.shopping.modules.user.entity;

import com.mittal.shopping.modules.auth.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    @Column(unique = true, nullable = false)
    private String email;
    
    private String password;

    private Role role;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
