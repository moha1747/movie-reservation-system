package com.backend.movie_res_system.entity;

import jakarta.persistence.*;

import com.backend.movie_res_system.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class ReservationSeat {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private Seat seat;

    private Double priceAtBooking;

    private String seatType;

    private LocalDateTime reservedAt;
}
