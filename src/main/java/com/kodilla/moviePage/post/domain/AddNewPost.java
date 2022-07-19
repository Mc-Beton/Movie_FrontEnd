package com.kodilla.moviePage.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddNewPost {

    private String movieId;
    private String username;
    private String content;
}
