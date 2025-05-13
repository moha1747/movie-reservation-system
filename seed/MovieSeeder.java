package com.backend.movie_res_system.seed;

import com.backend.movie_res_system.entity.Movie;
import com.backend.movie_res_system.repository.MovieRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class MovieSeeder implements CommandLineRunner {

    @Value("${omdb.api.key}")
    private String apiKey;

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MovieSeeder(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Skip if already seeded
        if (movieRepository.count() > 0) {
            return;
        }

        List<String> genres = List.of("horror", "romance", "comedy", "action", "drama");
        for (String genre : genres) {

            // Search OMDb by genre keyword (max 10 results)
            String searchUrl = String.format(
                    "https://www.omdbapi.com/?apikey=%s&s=%s&type=movie",
                    apiKey,
                    URLEncoder.encode(genre, StandardCharsets.UTF_8));

            JsonNode searchResponse = objectMapper.readTree(
                    restTemplate.getForObject(searchUrl, String.class));

            if (!searchResponse.path("Response").asText().equalsIgnoreCase("True")) {
                continue; // nothing found for this genre
            }

            for (JsonNode item : searchResponse.path("Search")) {
                String imdbID = item.path("imdbID").asText();

                // Fetch full details
                String detailUrl = String.format(
                        "https://www.omdbapi.com/?apikey=%s&i=%s&plot=short",
                        apiKey, imdbID);

                JsonNode detail = objectMapper.readTree(
                        restTemplate.getForObject(detailUrl, String.class));

                if (detail.path("Response").asText().equalsIgnoreCase("True")) {
                    // Map entity
                    Movie m = new Movie();
                    m.setName(detail.path("Title").asText());
                    m.setGenre(detail.path("Genre").asText());
                    movieRepository.save(m);
                }
                Thread.sleep(200);
            }
        }
    }
}
