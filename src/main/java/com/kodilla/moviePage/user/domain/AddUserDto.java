package com.kodilla.moviePage.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AddUserDto {

    private String name;
    private String surname;
    private String username;
    private String phoneNumber;
    private String email;
}
