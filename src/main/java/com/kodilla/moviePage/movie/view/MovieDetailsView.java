package com.kodilla.moviePage.movie.view;


import com.kodilla.moviePage.movie.service.ImdbMovieService;
import com.kodilla.moviePage.security.service.SecurityService;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

@Route("movie_details/:movieId")
@AnonymousAllowed
public class MovieDetailsView extends HorizontalLayout implements BeforeEnterObserver {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private String movieId;
    private ImdbMovieService movieService = new ImdbMovieService();
    Grid grid = new Grid<>();

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        movieId = event.getRouteParameters().get("movieId").get();
    }

    public MovieDetailsView (SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
        add(addMovieListButtons());
        configureGrid();
    }

    private void configureGrid() {
        grid.addClassName("movie-details-grid");
        Image image = new Image(movieService.getMovieDetails(movieId).getImage(), "movie");
        add(image);
        VerticalLayout details = new VerticalLayout();
        HorizontalLayout addButtons = new HorizontalLayout();

        details.add(new HorizontalLayout());
        details.add(new H2(movieService.getMovieDetails(movieId).getTitle()));
        details.add(new H4(movieService.getMovieDetails(movieId).getPlot()));
        add(details);
        add(grid);
    }

    private Component addMovieListButtons() {
        HorizontalLayout header = new HorizontalLayout();
        if (securityService.getAuthenticatedUser() == null) {
            Button button1 = new Button("Add to favorite");
            button1.addClickListener(clickEvent ->
                    userService.addMovieToFavList(securityService.getAuthenticatedUser().getUsername(), movieId));
            Button button2 = new Button("Add to watch");
            button2.addClickListener(clickEvent ->
                    userService.addMovieToWatchList(securityService.getAuthenticatedUser().getUsername(), movieId));
            header.add(button1);
            header.add(button2);
            header.setAlignItems(Alignment.END);
        }
        return header;
    }
}
