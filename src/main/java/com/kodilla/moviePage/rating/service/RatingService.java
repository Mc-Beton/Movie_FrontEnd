package com.kodilla.moviePage.rating.service;

import com.kodilla.moviePage.rating.domain.AddRating;
import com.kodilla.moviePage.rating.domain.Rating;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RatingService {

    RestTemplate restTemplate = new RestTemplate();

    public void saveRatingForMovie(final AddRating addRating) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/rating/")
                .queryParam("movieId", addRating.getMovieId())
                .queryParam("username", addRating.getUsername())
                .queryParam("rating", addRating.getRating())
                .build().encode().toUri();
        restTemplate.postForObject(url, addRating, AddRating.class);
    }

    public Rating getRatingByUserOfMovie(String movieId, String username) {
        URI url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/rating/" + username
                + "/movie/" + movieId)
                .build().encode().toUri();
        return restTemplate.getForObject(url, Rating.class);
    }
}
