package com.backend.movie_res_system.repository;

import com.backend.movie_res_system.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
