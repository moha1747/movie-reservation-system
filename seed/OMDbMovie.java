package com.backend.movie_res_system.seed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OMDbMovie {
    @JsonProperty("Title")
    private String title;
}
