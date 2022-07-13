package com.kodilla.moviePage.user.service;

import com.kodilla.moviePage.user.domain.AddUserDto;
import com.kodilla.moviePage.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

@Service
public class UserService {

    private RestTemplate restTemplate = new RestTemplate();

    public void createUser(final AddUserDto addUserDto) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user")
                .queryParam("name",addUserDto.getName())
                .queryParam("surname",addUserDto.getSurname())
                .queryParam("username",addUserDto.getUsername())
                .queryParam("phoneNumber",addUserDto.getPhoneNumber())
                .queryParam("email",addUserDto.getEmail())
                .build().encode().toUri();
        restTemplate.postForObject(url,addUserDto,AddUserDto.class);
    }

    public List<User> getUsers(String value) {
        if (value == null || value.isEmpty()) {
            try {
                User[] response = restTemplate.getForObject("http://localhost:8080/management/all-users", User[].class);
                return Arrays.asList(ofNullable(response).orElse(new User[0]));
            } catch (RestClientException e) {
                return new ArrayList<>();
            }
        } else {
            try{
                User[] response2 = restTemplate.getForObject("http://localhost:8080/user/all-users" + value, User[].class);
                return Arrays.asList(ofNullable(response2).orElse(new User[0]));
            }catch (RestClientException e){
                return new ArrayList<>();
            }
        }
    }

    public void updateUser(User user) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user")
                .build().encode().toUri();
        restTemplate.put(url, user);
    }

    public User getUserById(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId)
                .build().encode().toUri();
        return restTemplate.getForObject(url, User.class);
    }

    public void addFriend(Long userId, Long friendId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/addFriend/" + friendId)
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public List<User> getFriends(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/friends/" + userId)
                .build().encode().toUri();
        try {
            User[] response = restTemplate.getForObject(url, User[].class);
            return Arrays.asList(ofNullable(response).orElse(new User[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void addMovieToFavList(Long userId, String movieId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/addMovieToFav/" + movieId)
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public void addMovieToWatchList(Long userId, String movieId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/addMovieToWatch/" + movieId)
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public Set getFavoriteList(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/favoriteList")
                .build().encode().toUri();
        return restTemplate.getForObject(url, Set.class);
    }

    public Set getToWatchList(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/toWatchList")
                .build().encode().toUri();
        return restTemplate.getForObject(url, Set.class);
    }

    public void deleteUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/delete/" + userId)
                .build().encode().toUri();
        restTemplate.delete(url);
    }
}
