package com.kodilla.moviePage.service;

import com.kodilla.moviePage.domain.MovieWatchSite;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UtellyService {

    RestTemplate restTemplate = new RestTemplate();

    public List<MovieWatchSite> getWhereToWatch(String id) {
        try {
            URI url = UriComponentsBuilder
                    .fromHttpUrl("http://localhost:8080/watchmovie/" + id)
                    .build().encode().toUri();
            MovieWatchSite[] siteWatchList = restTemplate.getForObject(url, MovieWatchSite[].class);
            return Arrays.asList(siteWatchList);
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }
}
