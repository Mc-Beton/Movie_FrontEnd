package com.kodilla.moviePage.movie.service;

import com.kodilla.moviePage.movie.domain.ImdbMovie;
import com.kodilla.moviePage.movie.domain.ImdbMovieDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ImdbMovieService {

    RestTemplate restTemplate = new RestTemplate();

    public List<ImdbMovie> getPopular() {
        try {
            ImdbMovie[] top250List = restTemplate.getForObject("http://localhost:8080/v1/movies/", ImdbMovie[].class);
            return Arrays.asList(Objects.requireNonNull(top250List));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<ImdbMovie> getTop250() {
        try {
            ImdbMovie[] top250List = restTemplate.getForObject("http://localhost:8080/v1/movies/top250", ImdbMovie[].class);
            return Arrays.asList(Objects.requireNonNull(top250List));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<ImdbMovie> getSoon() {
        try {
            ImdbMovie[] top250List = restTemplate.getForObject("http://localhost:8080/v1/movies/soon", ImdbMovie[].class);
            return Arrays.asList(Objects.requireNonNull(top250List));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<ImdbMovie> getTopTV() {
        try {
            ImdbMovie[] top250List = restTemplate.getForObject("http://localhost:8080/v1/movies/topTV", ImdbMovie[].class);
            return Arrays.asList(Objects.requireNonNull(top250List));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public ImdbMovieDetails getMovieDetails(String id) {
        try {
            URI url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:8080/v1/movies/movieImbd_details/" + id)
                    .build().encode().toUri();
            return restTemplate.getForObject(url, ImdbMovieDetails.class);
        } catch (RestClientException e) {
            return new ImdbMovieDetails();
        }
    }

    public List<ImdbMovie> searchResult(String content) {
        try {
            URI url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:8080/v1/movies/search/" + content)
                    .build().encode().toUri();
            ImdbMovie[] searchList = restTemplate.getForObject(url, ImdbMovie[].class);
            return Arrays.asList(Objects.requireNonNull(searchList));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

}
