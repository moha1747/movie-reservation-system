package com.backend.movie_res_system.seed;

import com.backend.movie_res_system.entity.Movie;
import com.backend.movie_res_system.entity.Showtime;
import com.backend.movie_res_system.repository.MovieRepository;
import com.backend.movie_res_system.repository.ShowtimeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Order(2)
@Component
@Transactional
public class ShowtimeSeeder implements CommandLineRunner {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    public ShowtimeSeeder(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("▶ ShowtimeSeeder starting...");

        if (movieRepository.count() == 0) {
            throw new IllegalStateException("No movies found. Please seed movies first.");
        }

        // skip if already seeded
        if (showtimeRepository.count() > 0) {
            System.out.println("▶ Skipping seeding, showtimes already exist.");

            return;
        }
        List<LocalDateTime> times = Stream.of("12:30", "14:00", "16:45", "18:00", "19:30", "21:00")
                .map(t -> LocalDateTime.of(LocalDate.now(), LocalTime.parse(t)))
                .toList();
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            for (LocalDateTime time : times) {
                Showtime showtime = new Showtime();
                showtime.setStartTime(time);
                showtime.setMovie(movie);
                showtimeRepository.save(showtime);
            }
        }

        System.out.println("Seeding showtimes for movies: " + movies.size());
        System.out.println("Showtimes in DB after seeding: " + showtimeRepository.count());


        Thread.sleep(200);

    }
}
