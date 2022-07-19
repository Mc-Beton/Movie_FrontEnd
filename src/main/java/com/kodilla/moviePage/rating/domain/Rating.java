package com.kodilla.moviePage.rating.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rating {

    private String id;
    private String movieId;
    private String username;
    private Integer rating;
}
