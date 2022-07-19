package com.kodilla.moviePage.post.form;

import com.kodilla.moviePage.post.domain.AddNewPost;
import com.kodilla.moviePage.post.service.PostService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PostNewForm extends FormLayout {

    TextField content = new TextField("Content");
    Button save = new Button("Send");

    PostService postService = new PostService();

    public PostNewForm(String username, String movieId) {
        addClassName("post-add-form");
        validate();
        VerticalLayout ver = new VerticalLayout();
        ver.add(new H4("Comment this movie"));
        content.setSizeFull();
        ver.add(content);
        ver.add(formButtons());
        add(ver);
        savePost(username, movieId);
    }

    private void savePost(String username, String movieId) {
        save.addClickListener(event -> {
            AddNewPost newPost = new AddNewPost();
            newPost.setUsername(username);
            newPost.setMovieId(movieId);
            newPost.setContent(content.getValue());
            postService.createNewPost(newPost);
            UI.getCurrent().getPage().reload();
        });
    }

    private Component formButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return new VerticalLayout(save);
    }

    private void validate(){
        content.setRequired(true);
    }
}
