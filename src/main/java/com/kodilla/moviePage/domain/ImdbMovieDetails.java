package com.kodilla.moviePage.domain;

import java.util.Objects;

public class ImdbMovieDetails {

    private String id;
    private String title;
    private String image;
    private String plot;

    public ImdbMovieDetails() {
    }

    public ImdbMovieDetails(String id, String title, String image, String plot) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.plot = plot;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getPlot() {
        return plot;
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

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImdbMovieDetails that = (ImdbMovieDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(image, that.image) && Objects.equals(plot, that.plot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, image, plot);
    }
}
