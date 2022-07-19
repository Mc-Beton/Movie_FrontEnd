package com.kodilla.moviePage.post.service;

import com.kodilla.moviePage.post.domain.AddNewPost;
import com.kodilla.moviePage.post.domain.Post;
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
public class PostService {

    RestTemplate restTemplate = new RestTemplate();

    public List<Post> getMoviePosts(String movieId) {
        Post[] moviePosts = restTemplate.getForObject(
                "http://localhost:8080/posts/" + movieId, Post[].class);
        return Arrays.asList(Objects.requireNonNull(moviePosts));
    }

    public void createNewPost(final AddNewPost newPost) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/posts/")
                .queryParam("movieId", newPost.getMovieId())
                .queryParam("username", newPost.getUsername())
                .queryParam("content", newPost.getContent())
                .build().encode().toUri();
        restTemplate.postForObject(url, newPost, AddNewPost.class);
    }
}
