package com.kodilla.moviePage.main;

import com.kodilla.moviePage.domain.ImdbMovie;
import com.kodilla.moviePage.domain.ImdbMovieService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("movie")
public class MovieView extends VerticalLayout {

    private ImdbMovieService movieService = new ImdbMovieService();
    private MovieDetailsView detailsView = new MovieDetailsView();
    Grid<ImdbMovie> grid = new Grid<>(ImdbMovie.class);

    public MovieView() {
        setSizeFull();
        configureGrid();
        add(getContent());
        updateList();
    }

    private void updateList() {
        grid.setItems(movieService.getTop250());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureGrid() {
        grid.addClassName("movie-grid");
        setSizeFull();
        grid.setColumns("title", "year");
        grid.addComponentColumn(imdbMovie -> {
            Image image = new Image(imdbMovie.getImage(), imdbMovie.getId());
            image.setWidth("200px");
            return image;
        }).setHeader("Poster");
        grid.addComponentColumn(details -> {
            Button button = new Button("Show details");
            button.addClickListener(clickEvent ->
                    UI.getCurrent().navigate("movie_details/" +
                            details.getId()));
            return button;
        });
        add(grid);
    }
}
