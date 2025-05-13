package com.backend.movie_res_system.seed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class OMDbSearchResponse {
    @JsonProperty("Search")
    private List<OMDbMovie> search;

}
