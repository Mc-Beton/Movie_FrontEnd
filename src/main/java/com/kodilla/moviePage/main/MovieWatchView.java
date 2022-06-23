package com.kodilla.moviePage.main;

import com.kodilla.moviePage.domain.MovieWatchSite;
import com.kodilla.moviePage.service.UtellyService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;


@Route("watch_movie/:movieId")
public class MovieWatchView extends VerticalLayout implements BeforeEnterObserver {

    private String movieId;
    private UtellyService utellyService = new UtellyService();
    Grid<MovieWatchSite> grid = new Grid<>(MovieWatchSite.class);

    public void beforeEnter(BeforeEnterEvent event) {
        movieId = event.getRouteParameters().get("movieId").get();
        configureGrid();
        setSizeFull();
        updateList();
    }

    private void updateList() {
        grid.setItems(utellyService.getWhereToWatch(movieId));
    }

    private void configureGrid() {
        grid.addClassName("movie-watch-grid");
        add(new H2("WATCH IT ONLINE NOW ON:"));
        setSizeFull();
        grid.setColumns();
        grid.addComponentColumn(movie -> new Image(movie.getIcon(), "logo"))
                .setHeader("Logo");
        grid.addColumn(MovieWatchSite::getDisplay_name).setHeader("Site");
        grid.addComponentColumn(movie -> {
            Anchor link = new Anchor(movie.getUrl(), movie.getDisplay_name());
            addClickListener(e -> UI.getCurrent().navigate(link.getHref()));
            return link;
        });
        add(grid);
    }
}
