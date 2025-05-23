package com.backend.movie_res_system.entity;

import com.backend.movie_res_system.entity.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seat {
    @Id @GeneratedValue
    private Long seatId;

    private String seatNumber;

    private Boolean isReserved = false;

    @ManyToOne
    @JoinColumn(name = "showtime_id")  // match the column name in DB
    private Showtime showtime;
}
