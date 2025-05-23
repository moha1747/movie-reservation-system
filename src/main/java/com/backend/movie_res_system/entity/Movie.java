package com.backend.movie_res_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import com.backend.movie_res_system.entity.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;


@Entity
@Getter
@Setter
public class Movie {
    @Id @GeneratedValue private long mid;
    private String Name;
    private String Genre;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;



}
