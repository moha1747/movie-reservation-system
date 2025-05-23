package com.backend.movie_res_system.repository;

import com.backend.movie_res_system.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findById (Long seatId);
}
