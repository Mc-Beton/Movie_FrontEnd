package com.kodilla.moviePage.domain;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImdbMovieService {

    RestTemplate restTemplate = new RestTemplate();

    public List<ImdbMovie> getTop250() {
        try {
            ImdbMovie[] top250List = restTemplate.getForObject("http://localhost:8080/v1/movies/", ImdbMovie[].class);
            return Arrays.asList(top250List);
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public ImdbMovieDetails getMovieDetails(String id) {
        System.out.println(id);
        try {
            URI url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:8080/v1/movies/movieImbd_details/" + id)
                    .build().encode().toUri();
            System.out.println(url);
            ImdbMovieDetails movie = restTemplate.getForObject(url, ImdbMovieDetails.class);
            return movie;
        } catch (RestClientException e) {
            return new ImdbMovieDetails();
        }
    }
}
