package com.kodilla.moviePage.main;

import com.kodilla.moviePage.domain.ImdbMovie;
import com.kodilla.moviePage.service.ImdbMovieService;
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

@Route("/:movieCat")
public class MovieView extends VerticalLayout implements BeforeEnterObserver {

    private String showList;
    private final ImdbMovieService movieService = new ImdbMovieService();
    Grid<ImdbMovie> grid = new Grid<>(ImdbMovie.class);

    public void beforeEnter(BeforeEnterEvent event) {
        showList = event.getRouteParameters().get("movieCat").get();
        configureGrid();
        add(addMenuButtons());
        add(getContent());
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
                    UI.getCurrent().navigate("movie_details/" +
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
        add(grid);
    }

    private Component addMenuButtons() {
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
                UI.getCurrent().navigate("user"));
        catogMovie.add(button1);
        catogMovie.add(button2);
        catogMovie.add(button3);
        catogMovie.add(button4);
        catogMovie.add(addSearch());
        catogMovie.add(button5);
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
}
