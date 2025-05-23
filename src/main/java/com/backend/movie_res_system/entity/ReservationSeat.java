package com.backend.movie_res_system.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ReservationSeat {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private Seat seat;

    public ReservationSeat(Reservation reservation, Seat seat) {
        this.reservation = reservation;
        this.seat = seat;
    }


}
