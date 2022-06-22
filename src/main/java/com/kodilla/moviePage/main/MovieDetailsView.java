package com.kodilla.moviePage.main;


import com.kodilla.moviePage.domain.ImdbMovieService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

@Route("movie_details/:movieId")
public class MovieDetailsView extends HorizontalLayout implements BeforeEnterObserver {

    private String movieId;
    private ImdbMovieService movieService = new ImdbMovieService();
    Grid grid = new Grid<>();


    public void beforeEnter(BeforeEnterEvent event) {
        movieId = event.getRouteParameters().get("movieId").get();
        configureGrid();
    }

    public MovieDetailsView () {
        setSizeFull();
    }

    private void configureGrid() {
        grid.addClassName("movie-details-grid");
        Image image = new Image(movieService.getMovieDetails(movieId).getImage(), "movie");
        add(image);
        VerticalLayout details = new VerticalLayout();
        details.add(new H2(movieService.getMovieDetails(movieId).getTitle()));
        details.add(new H4(movieService.getMovieDetails(movieId).getPlot()));
        add(details);
        add(grid);
    }
}
