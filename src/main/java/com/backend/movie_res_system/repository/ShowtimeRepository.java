package com.backend.movie_res_system.repository;

import com.backend.movie_res_system.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    Optional<Showtime> findByShowtimeId(Long showTimeId);
}
