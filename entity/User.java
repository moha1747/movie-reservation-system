package com.backend.movie_res_system.entity;

import com.backend.movie_res_system.entity.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long uid;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
