package com.kodilla.moviePage.user.view;

import com.kodilla.moviePage.movie.domain.ImdbMovie;
import com.kodilla.moviePage.movie.service.ImdbMovieService;
import com.kodilla.moviePage.security.service.SecurityService;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@Route("user/details")
@PermitAll
public class UserDetailsView extends VerticalLayout {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    Grid<ImdbMovie> favGrid = new Grid<>(ImdbMovie.class);
    Grid<ImdbMovie> watGrid = new Grid<>(ImdbMovie.class);

    public UserDetailsView(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
        add(userHeader());
        HorizontalLayout hor = new HorizontalLayout();
        hor.add(getContent(favGrid), getContent(watGrid));
        add(hor);
        configureFavGrid();
        configureWatGrid();
        updateList();

    }

    private Component getContent(Grid grid) {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureFavGrid() {
        favGrid.addClassName("user-movie-grid");
        configureMovieGrid(favGrid);
    }

    private void configureWatGrid() {
        watGrid.addClassName("user-toSee-grid");
        configureMovieGrid(watGrid);
    }

    private void configureMovieGrid(Grid<ImdbMovie> grid) {
        grid.setColumns();
        grid.addComponentColumn(imdbMovie -> {
            Image image = new Image(imdbMovie.getImage(), imdbMovie.getId());
            image.setWidth("200px");
            return image;
        }).setHeader("Poster");
        grid.addComponentColumn(details -> {
            VerticalLayout ver = new VerticalLayout();
            Button button = new Button(details.getTitle());
            button.addClickListener(clickEvent ->
                    UI.getCurrent().navigate("movie_details/" +
                            details.getId()));
            Button button2 = new Button("Watch it online");
            button2.addClickListener(clickEvent ->
                    UI.getCurrent().navigate("watch_movie/" +
                            details.getId()));
            ver.add(button, button2);
            return ver;
        });
        add(grid);
    }

    private void updateList() {
        favGrid.setItems(userService.getFavoriteList(securityService.getAuthenticatedUser().getUsername()));
        watGrid.setItems(userService.getToWatchList(securityService.getAuthenticatedUser().getUsername()));
    }

    private Component userHeader() {
        VerticalLayout ver = new VerticalLayout();
        HorizontalLayout hor = new HorizontalLayout();
        ver.add(new H2(securityService.getAuthenticatedUser().getUsername() + " Movie Details"));
        Button goBack = new Button("Movie site");
        goBack.addClickListener(e->
                UI.getCurrent().navigate("movie"));
        Button logout = new Button("Logout", click -> {
            securityService.logout();
            UI.getCurrent().navigate("movie");
        });
        hor.add(goBack, logout);
        ver.add(hor);
        return ver;
    }
}
