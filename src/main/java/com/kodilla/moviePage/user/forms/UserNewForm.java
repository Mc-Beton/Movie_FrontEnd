package com.kodilla.moviePage.user.forms;

import com.kodilla.moviePage.user.domain.AddUserDto;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class UserNewForm extends FormLayout {

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField username = new TextField("Username");
    TextField password = new TextField(("Password"));
    EmailField email = new EmailField("Email");
    TextField phoneNumber = new TextField("Phone");
    Button save = new Button("Save");
    Button cancel = new Button("Cancel");

    UserService userService = new UserService();

    public UserNewForm() {
        addClassName("user-add-form");
        validate();

        add(new H2("Join us!"));
        add(name,surname,username,password,email,phoneNumber,formButtons());

        saveUser();
        cancelForm();
    }

    private Component formButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return new VerticalLayout(save,cancel);
    }

    private void saveUser(){
        save.addClickListener(event ->{
            AddUserDto newUser = new AddUserDto();
            newUser.setName(name.getValue());
            newUser.setSurname(surname.getValue());
            newUser.setUsername(username.getValue());
            newUser.setPassword(password.getValue());
            newUser.setPhoneNumber(phoneNumber.getValue());
            newUser.setEmail(email.getValue());
            userService.createUser(newUser);
            UI.getCurrent().getPage().reload();
        });
    }

    private void validate(){
        name.setRequired(true);
        surname.setRequired(true);
        username.setRequired(true);
    }

    private void cancelForm(){
        cancel.addClickListener(event -> {
            name.clear();
            surname.clear();
            username.clear();
            email.clear();
            phoneNumber.clear();
        });
    }
}

