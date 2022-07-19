package com.kodilla.moviePage.rating.form;

import com.kodilla.moviePage.post.domain.AddNewPost;
import com.kodilla.moviePage.rating.domain.AddRating;
import com.kodilla.moviePage.rating.service.RatingService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.Arrays;

public class AddRatingForm extends FormLayout {

    ComboBox<Integer> rates = new ComboBox<Integer>();
    RatingService ratingService = new RatingService();
    Button save = new Button("Rate");

    public AddRatingForm(String username, String movieId) {
        addClassName("add-rating");
        HorizontalLayout hor = new HorizontalLayout();
        rates.setItems(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        rates.setWidth("5em");
        VerticalLayout ver = new VerticalLayout();
        ver.add(new H4("Rank this movie"));
        hor.add(rates);
        hor.add(formButtons());
        add(hor);
        saveRate(username, movieId);

    }

    private void saveRate(String username, String movieId) {
        save.addClickListener(event -> {
            AddRating newRate = new AddRating();
            newRate.setUsername(username);
            newRate.setMovieId(movieId);
            newRate.setRating(rates.getValue());
            ratingService.saveRatingForMovie(newRate);
            UI.getCurrent().getPage().reload();
        });
    }

    private Component formButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return save;
    }
}
