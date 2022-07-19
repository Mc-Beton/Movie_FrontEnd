package com.kodilla.moviePage.rating;

import com.kodilla.moviePage.movie.domain.ImdbMovieDetails;
import com.kodilla.moviePage.movie.service.ImdbMovieService;
import com.kodilla.moviePage.post.domain.Post;
import com.kodilla.moviePage.post.form.PostNewForm;
import com.kodilla.moviePage.post.service.PostService;
import com.kodilla.moviePage.rating.form.AddRatingForm;
import com.kodilla.moviePage.security.service.SecurityService;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;


@Route("postTest/:movieId")
@AnonymousAllowed
public class viewer extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private PostService postService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private final ImdbMovieService movieService = new ImdbMovieService();

    private String movie = new String();

    PostNewForm postNewForm;
    AddRatingForm addRatingForm;

    Grid<Post> postGrid = new Grid<>(Post.class);
    Grid<ImdbMovieDetails> movieGrid = new Grid<>(ImdbMovieDetails.class);

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        movie = event.getRouteParameters().get("movieId").get();
        updateData(movie);
    }

    public viewer(PostService postService) {
        this.postService = postService;
        add(new H2("Hello"));

        configurePostGrid();
        configurePostForm("Fifi", "tt123456");
        configureRateForm("Fifi", "tt123456");
        HorizontalLayout hor = new HorizontalLayout();
        hor.add(getContent());
        hor.add(addRatingForm);
        add(hor);

    }

    public void updateData(String movie) {
        //movieGrid.setItems(movieService.getMovieDetails(movie));
        postGrid.setItems(postService.getMoviePosts(movie));
    }

    private void configurePostGrid() {
        postGrid.addClassName("post-grid");
        postGrid.setColumns("username", "content");
        postGrid.setWidth(400, Unit.PIXELS);
    }

    private Component getContent() {
        VerticalLayout content = new VerticalLayout(postGrid, postNewForm);
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
}
