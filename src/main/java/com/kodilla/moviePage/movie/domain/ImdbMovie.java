package com.kodilla.moviePage.movie.domain;

import java.util.Base64;
import java.util.Objects;

public class ImdbMovie {

    private String id;
    private String title;
    private String image;
    private String year;


    public ImdbMovie() {
    }

    public ImdbMovie(String id, String title, String image, String year) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImdbMovie imdbMovie = (ImdbMovie) o;
        return Objects.equals(id, imdbMovie.id) && Objects.equals(title, imdbMovie.title) && Objects.equals(image, imdbMovie.image) && Objects.equals(year, imdbMovie.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, image, year);
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getYear() {
        return year;
    }

    public byte[] showImage() {
        return Base64.getDecoder().decode(image);
    }


}
