package com.kodilla.moviePage.movie.view;

import com.kodilla.moviePage.rating.service.AvgRatingService;
import com.kodilla.moviePage.movie.service.ImdbMovieService;
import com.kodilla.moviePage.post.domain.Post;
import com.kodilla.moviePage.post.form.PostNewForm;
import com.kodilla.moviePage.post.service.PostService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;

@Route("movie_details/:movieId")
@AnonymousAllowed
public class MovieDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private final ImdbMovieService movieService = new ImdbMovieService();

    Grid<Post> postGrid = new Grid<>(Post.class);
    TextField postField = new TextField();
    Button savePost = new Button("Send");

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    PostService postService;
    PostNewForm postNewForm;

    @Autowired
    AvgRatingService avgRatingService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String movieId = event.getRouteParameters().get("movieId").get();
        add(configureData(movieId, movieService, postService, avgRatingService));
    }

    public MovieDetailsView() {
    }

    private Component configureData(String movieId, ImdbMovieService service, PostService postService, AvgRatingService avgRatingService) {
        HorizontalLayout hor = new HorizontalLayout();
//        Image image = new Image(service.getMovieDetails(movieId).getImage(), service.getMovieDetails(movieId).getTitle());
//        image.setWidth(400, Unit.PIXELS);
//        hor.add(image);
        HorizontalLayout rates = new HorizontalLayout();
        //rates.add(addRateMovie());
        rates.add(addAvgRating(movieId));
        VerticalLayout ver = new VerticalLayout();
        ver.add(addMainViewButton());
        ver.add(rates);
        ver.add(new H2("Hello " + movieId));
        //ver.add(new H2(service.getMovieDetails(movieId).getTitle()));
        //ver.add(new H3(service.getMovieDetails(movieId).getPlot()));
        //ver.add(addPostList(movieId, postService));
        hor.add(ver);
        return hor;
    }

    private Component addMainViewButton() {
        Button goBack = new Button("Movie site");
        goBack.addClickListener(e-> UI.getCurrent().navigate("movie"));
        return goBack;
    }

    private Component addPostList(String movieId, PostService postService) {
        postGrid.setColumns("username", "content");
        VerticalLayout ver = new VerticalLayout(postGrid, postNewForm, savePost);
        ver.setWidth(400, Unit.PIXELS);
        ver.addClassName("post");
        return ver;
    }

    private Component addAvgRating(String movieId) {
        HorizontalLayout hor = new HorizontalLayout();
        hor.add(new H3("This Site Rating"));
        hor.add(new H2(String.valueOf(df.format(avgRatingService.getAvgRatingOfMovie(movieId).getAverageRating()))));
        hor.setAlignItems(Alignment.CENTER);
        return hor;
    }

    private Component addRateMovie() {
        return null;
    }
}
