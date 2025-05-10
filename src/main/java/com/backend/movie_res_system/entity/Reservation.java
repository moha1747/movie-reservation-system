package com.backend.movie_res_system.entity;

import jakarta.persistence.*;

import com.backend.movie_res_system.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Reservation {
    @Id @GeneratedValue
    private Long rid;

    private LocalDateTime timestamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private ShowTime showtime;

    @ManyToMany
    private List<Seat> seats;
}
