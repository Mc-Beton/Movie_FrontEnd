package com.kodilla.moviePage.user.view;

import com.kodilla.moviePage.user.domain.User;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("user/register")
@AnonymousAllowed
public class UserRegisterView extends VerticalLayout {

    private UserService userService = new UserService();

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField username = new TextField("Username");
    TextField password = new TextField("Password");
    EmailField email = new EmailField("Email");
    TextField phoneNumber = new TextField("PhoneNumber");
    Button save = new Button("Save");
    Button cancel = new Button("Cancel");
    Button back = new Button("Go back");

    public UserRegisterView() {
        add(new H2("JOIN US"));
        add(name,surname,username,password,email,phoneNumber,save,cancel,back);

        saveUserData();
        cancel();
        setSizeFull();

        validate();
    }

    private void saveUserData() {
        save.addClickListener(event -> {
            User user = new User();
            user.setName(name.getValue());
            user.setSurname(surname.getValue());
            user.setUsername(username.getValue());
            user.setPassword(password.getValue());
            user.setPhoneNumber(phoneNumber.getValue());
            user.setEmail(email.getValue());
            userService.updateUser(user);
            UI.getCurrent().navigate("movie");
        });
    }

    private void cancel(){
        cancel.addClickListener(e->{
            name.clear();
            surname.clear();
            username.clear();
            password.clear();
            phoneNumber.clear();
            email.clear();
        });
        back.addClickListener(event -> UI.getCurrent().navigate("movie"));
    }

    private void validate(){
        name.setRequired(true);
        surname.setRequired(true);
        username.setRequired(true);
        password.setRequired(true);
        phoneNumber.setRequired(true);
    }
}
