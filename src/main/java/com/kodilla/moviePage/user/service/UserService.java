package com.kodilla.moviePage.user.service;

import com.kodilla.moviePage.movie.domain.ImdbMovie;
import com.kodilla.moviePage.security.domain.AdminPrincipal;
import com.kodilla.moviePage.security.domain.UserPrincipal;
import com.kodilla.moviePage.user.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserService implements UserDetailsService {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${admin.login}")
    private String adminName;

    @Value("${admin.password}")
    private String adminPass;

    public void createUser(final AddUserDto addUserDto) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user")
                .queryParam("name", addUserDto.getName())
                .queryParam("surname", addUserDto.getSurname())
                .queryParam("username", addUserDto.getUsername())
                .queryParam("phoneNumber", addUserDto.getPhoneNumber())
                .queryParam("email", addUserDto.getEmail())
                .build().encode().toUri();
        restTemplate.postForObject(url, addUserDto, AddUserDto.class);
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
            try {
                User[] response2 = restTemplate.getForObject("http://localhost:8080/user/all-users" + value, User[].class);
                return Arrays.asList(ofNullable(response2).orElse(new User[0]));
            } catch (RestClientException e) {
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

    public void addMovieToFavList(String userId, String movieId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/addMovieToFav/" + movieId)
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public void addMovieToWatchList(String userId, String movieId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/addMovieToWatch/" + movieId)
                .build().encode().toUri();
        restTemplate.put(url, null);
    }

    public List<ImdbMovie> getFavoriteList(String userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/favoriteList")
                .build().encode().toUri();
        try {
            ImdbMovie[] response = restTemplate.getForObject(url, ImdbMovie[].class);
            return Arrays.asList(ofNullable(response).orElse(new ImdbMovie[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public List<ImdbMovie> getToWatchList(String userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/" + userId + "/toWatchList")
                .build().encode().toUri();
        try {
            ImdbMovie[] response = restTemplate.getForObject(url, ImdbMovie[].class);
            return Arrays.asList(ofNullable(response).orElse(new ImdbMovie[0]));
        } catch (RestClientException e) {
            return new ArrayList<>();
        }
    }

    public void deleteUser(Long userId) {
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/delete/" + userId)
                .build().encode().toUri();
        restTemplate.delete(url);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userPrincipal = null;
        try {
            if ("admin".equalsIgnoreCase(username)) {
                AdminPrincipal admin = new AdminPrincipal();
                admin.setUsername(adminName);
                admin.setPassword(adminPass);
                userPrincipal = admin;
            } else {
                URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/user/getUserDataLogin/" + username)
                        .build().encode().toUri();
                User user = restTemplate.getForObject(url, User.class);
                if (restTemplate.getForObject(url, User.class) == null)
                    throw new UsernameNotFoundException("User 404");
                userPrincipal = new UserPrincipal(user);
            }
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
        }
        return userPrincipal;
    }
}
