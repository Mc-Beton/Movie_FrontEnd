package com.kodilla.moviePage.rating.service;

import com.kodilla.moviePage.rating.domain.AvgRating;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class AvgRatingService {

    RestTemplate restTemplate = new RestTemplate();

    public AvgRating getAvgRatingOfMovie(String movieId) {
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/avgRating/" + movieId)
                .build().encode().toUri();
        return restTemplate.getForObject(url, AvgRating.class);
    }
}
