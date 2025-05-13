package com.backend.movie_res_system.seed;

import com.backend.movie_res_system.entity.Movie;
import com.backend.movie_res_system.entity.Showtime;
import com.backend.movie_res_system.repository.MovieRepository;
import com.backend.movie_res_system.repository.ShowtimeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class ShowTimeSeader implements CommandLineRunner {
    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowTimeSeader(ShowtimeRepository showtimeRepository, MovieRepository movieRepository) {
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
    }
//  Your flow would be:

//    Loop through all movies.
//
//    For each movie, loop through the time list: ["12:30", "14:00", "16:45", "18:00", "19:30", "21:00"].
//
//    Convert each string time to LocalDateTime (using today’s date).
//
//    Create a ShowTime with the movie and startTime.
//
//    Save all showtimes.


    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() == 0) {
            throw new IllegalStateException("No movies found. Please seed movies first.");
        }

        // skip if already seeded
        if (showtimeRepository.count() > 0) {
            return;
        }
        List<LocalDateTime> times = Stream.of("12:30", "14:00", "16:45", "18:00", "19:30", "21:00")
                .map(t -> LocalDateTime.of(LocalDate.now(), LocalTime.parse(t)))
                .toList();
        List<Movie> movies = movieRepository.findAll();
        for (Movie movie : movies) {
            Random random = new Random();
            int index = random.nextInt(times.size());
            LocalDateTime randomTime = times.get(index);
            Showtime showtime = new Showtime();

            showtime.setStartTime(randomTime);
            showtime.setMovie(movie);
            showtimeRepository.save(showtime);

        }
        Thread.sleep(200);

    }
}
