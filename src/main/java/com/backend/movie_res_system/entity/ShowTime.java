package com.backend.movie_res_system.entity;

import com.backend.movie_res_system.entity.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ShowTime {
    @Id @GeneratedValue
    private Long showtimeId;

    private LocalDateTime startTime;

    @ManyToOne
    private Movie movie;

    @OneToMany(mappedBy = "showtime")
    private List<Seat> seats;
}
