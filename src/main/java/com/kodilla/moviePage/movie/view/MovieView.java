package com.kodilla.moviePage.movie.view;

import com.kodilla.moviePage.movie.domain.ImdbMovie;
import com.kodilla.moviePage.security.service.SecurityService;
import com.kodilla.moviePage.movie.service.ImdbMovieService;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Objects;

@Route("/:movieCat")
@AnonymousAllowed
public class MovieView extends VerticalLayout implements BeforeEnterObserver {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    private String showList;
    private final ImdbMovieService movieService = new ImdbMovieService();
    Grid<ImdbMovie> grid = new Grid<>(ImdbMovie.class);

    public MovieView(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
        configureGrid();
        add(addMenuButtons());
        add(getContent());
        setSizeFull();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        showList = event.getRouteParameters().get("movieCat").get();
        setSizeFull();
        updateList(showList);
    }

    private void updateList(String showList) {
        if (showList.equals("soon"))
            grid.setItems(movieService.getSoon());
        else if (showList.equals("top250"))
            grid.setItems(movieService.getTop250());
        else if (showList.equals("topTV"))
            grid.setItems(movieService.getTopTV());
        else if (showList.equals("movie"))
            grid.setItems(movieService.getPopular());
        else
            grid.setItems(movieService.searchResult(showList));
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
                    UI.getCurrent().navigate("movie/details/" +
                            details.getId()));
            return button;
        });
        grid.addComponentColumn(details -> {
            Button button2 = new Button("Watch it online");
            button2.addClickListener(clickEvent ->
                    UI.getCurrent().navigate("watch_movie/" +
                            details.getId()));
            return button2;
        });
        if (securityService.getAuthenticatedUser() != null &&
                Objects.equals(securityService.getAuthenticatedUser().getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("USER")))) {
            grid.addComponentColumn(details -> {
                Button button3 = new Button("Add to favorite");
                button3.addClickListener(clickEvent ->
                        userService.addMovieToFavList(securityService.getAuthenticatedUser().getUsername(), details.getId()));
                return button3;
            });
        }
        if (securityService.getAuthenticatedUser() != null && Objects.equals(securityService.getAuthenticatedUser().getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("USER")))) {
            grid.addComponentColumn(details -> {
                Button button4 = new Button("Add to watch");
                button4.addClickListener(clickEvent ->
                        userService.addMovieToWatchList(securityService.getAuthenticatedUser().getUsername(), details.getId()));
                return button4;
            });
        }
        add(grid);
    }

    private Component addMenuButtons() {
        add(addLoginButtons());
        add(new H2("Welcome to this Custom Movie Site"));
        HorizontalLayout catogMovie = new HorizontalLayout();
        Button button1 = new Button("Most Popular");
        button1.addClickListener(e ->
                UI.getCurrent().navigate("movie"));
        Button button2 = new Button("Top 250");
        button2.addClickListener(e ->
                UI.getCurrent().navigate("top250"));
        Button button3 = new Button("Top 250 TV");
        button3.addClickListener(e ->
                UI.getCurrent().navigate("topTV"));
        Button button4 = new Button("Coming Soon");
        button4.addClickListener(e ->
                UI.getCurrent().navigate("soon"));
        Button button5 = new Button("User settings");
        button5.addClickListener(e ->
                UI.getCurrent().navigate("management/user"));
        Button button6 = new Button("User details");
        button6.addClickListener(e ->
                UI.getCurrent().navigate("user/details"));

        catogMovie.add(button1);
        catogMovie.add(button2);
        catogMovie.add(button3);
        catogMovie.add(button4);
        catogMovie.add(addSearch());
        if (securityService.getAuthenticatedUser() != null && Objects.equals(securityService.getAuthenticatedUser().getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("ADMIN"))))
            catogMovie.add(button5);
        if (securityService.getAuthenticatedUser() != null && Objects.equals(securityService.getAuthenticatedUser().getAuthorities(), Collections.singleton(new SimpleGrantedAuthority("USER"))))
            catogMovie.add(button6);
        return catogMovie;
    }

    private Component addSearch() {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(VaadinIcon.SEARCH.create());
        Button button = new Button("Search");
        button.addClickListener(click ->
                UI.getCurrent().navigate(searchField.getValue()));
        HorizontalLayout hl = new HorizontalLayout(searchField, button);
        hl.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        return hl;
    }

    private Component addLoginButtons() {
        HorizontalLayout header;
        if (securityService.getAuthenticatedUser() == null) {
            Button login = new Button("Login", click ->
                    UI.getCurrent().navigate("login"));
            Button register = new Button("Register", click ->
                    UI.getCurrent().navigate("user/register"));
            header = new HorizontalLayout(register, login);
        } else {
            Button logout = new Button("Logout", click -> {
                securityService.logout();
                UI.getCurrent().navigate("movie");
            });
            header = new HorizontalLayout(logout);
        }
        header.setAlignItems(Alignment.END);
        return header;
    }
}