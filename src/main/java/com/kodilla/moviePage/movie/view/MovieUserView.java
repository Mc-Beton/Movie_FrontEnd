package com.kodilla.moviePage.movie.view;

import com.kodilla.moviePage.movie.domain.ImdbMovieDetails;
import com.kodilla.moviePage.movie.service.ImdbMovieService;
import com.kodilla.moviePage.post.domain.Post;
import com.kodilla.moviePage.post.form.PostNewForm;
import com.kodilla.moviePage.post.service.PostService;
import com.kodilla.moviePage.rating.form.AddRatingForm;
import com.kodilla.moviePage.rating.service.AvgRatingService;
import com.kodilla.moviePage.security.service.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.text.DecimalFormat;

@Route("movie/details/:movieId")
@AnonymousAllowed
public class MovieUserView extends VerticalLayout implements BeforeEnterObserver {


    private final PostService postService = new PostService();
    private final SecurityService securityService = new SecurityService();
    private final ImdbMovieService movieService = new ImdbMovieService();
    private final AvgRatingService avgRatingService = new AvgRatingService();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    private String movie;

    PostNewForm postNewForm;
    AddRatingForm addRatingForm;

    Grid<Post> postGrid = new Grid<>(Post.class);
    ImdbMovieDetails movieGrid = new ImdbMovieDetails();

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        movie = event.getRouteParameters().get("movieId").get();
        updateData(movie);
        setSizeFull();
        add(configureMovie());
        configurePostGrid();
    }

    public void updateData(String movie) {
        movieGrid = movieService.getMovieDetails(movie);
        postGrid.setItems(postService.getMoviePosts(movie));
    }

    private void configurePostGrid() {
        postGrid.addClassName("post-grid");
        postGrid.setColumns("username", "content");
        postGrid.getColumnByKey("username").setWidth("10em");
        postGrid.getColumnByKey("content").setWidth("30em");
        postGrid.setWidth("40em");
    }

    private Component getPostForm() {
        HorizontalLayout content = new HorizontalLayout(postGrid, postNewForm);
        content.addClassName("content");
        return content;
    }

    private void configurePostForm(String username, String movieId) {
        postNewForm = new PostNewForm(username, movieId);
        postNewForm.setWidth("50em");
    }

    private void configureRateForm(String username, String movieId) {
        addRatingForm = new AddRatingForm(username, movieId);
        addRatingForm.setWidth("30em");
    }

    private Component configureMovie() {
        HorizontalLayout hor = new HorizontalLayout();
        Image image = new Image(movieGrid.getImage(), movieGrid.getTitle());
        image.setWidth(400, Unit.PIXELS);
        hor.add(image);
        HorizontalLayout rates = new HorizontalLayout();
        rates.add(addAvgRating(movieGrid.getId()));
        if (securityService.getAuthenticatedUser() != null) {
            configureRateForm(securityService.getAuthenticatedUser().getUsername(), movieGrid.getId());
            rates.add(addRatingForm);
        }
        rates.setAlignItems(Alignment.CENTER);
        VerticalLayout ver = new VerticalLayout();
        ver.add(addMainViewButton());
        ver.add(rates);
        ver.add(new H2(movieGrid.getTitle()));
        ver.add(new H3(movieGrid.getPlot()));
        if (securityService.getAuthenticatedUser() != null) {
            configurePostForm(securityService.getAuthenticatedUser().getUsername(), movieGrid.getId());
            ver.add(getPostForm());
        } else {
            ver.add(postGrid);
        }
        hor.add(ver);
        hor.setSizeFull();
        return hor;
    }

    private Component addMainViewButton() {
        Button goBack = new Button("Movie site");
        goBack.addClickListener(e-> UI.getCurrent().navigate("movie"));
        return goBack;
    }

    private Component addAvgRating(String movieId) {
        HorizontalLayout hor = new HorizontalLayout();
        hor.add(new H3("This Site Rating"));
        hor.add(new H2(String.valueOf(df.format(avgRatingService.getAvgRatingOfMovie(movieId).getAverageRating()))));
        hor.setAlignItems(Alignment.CENTER);
        return hor;
    }
}
