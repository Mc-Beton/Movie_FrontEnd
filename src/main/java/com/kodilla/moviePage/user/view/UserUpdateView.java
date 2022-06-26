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

@Route("updateUserData")
public class UserUpdateView extends VerticalLayout {

    private UserService userService = new UserService();

    TextField id = new TextField("Id");
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField username = new TextField("Username");
    EmailField email = new EmailField("Email");
    TextField phoneNumber = new TextField("PhoneNumber");
    Button save = new Button("Save");
    Button cancel = new Button("Cancel");
    Button back = new Button("Go back");

    public UserUpdateView() {
        add(new H2("Update your data"));
        add(id,name,surname,username,email,phoneNumber,save,cancel,back);

        updateUserData();
        cancel();
        setSizeFull();

        validate();
    }

    private void updateUserData() {
        save.addClickListener(event -> {
            User user = new User();
            user.setId(Long.valueOf(id.getValue()));
            user.setName(name.getValue());
            user.setSurname(surname.getValue());
            user.setUsername(username.getValue());
            user.setPhoneNumber(phoneNumber.getValue());
            user.setEmail(email.getValue());
            userService.updateUser(user);
            UI.getCurrent().navigate("user");
        });
    }

    private void cancel(){
        cancel.addClickListener(e->{
            id.clear();
            name.clear();
            surname.clear();
            username.clear();
            phoneNumber.clear();
            email.clear();
        });
        back.addClickListener(event -> UI.getCurrent().navigate("user"));
    }

    private void validate(){
        id.setRequired(true);
        name.setRequired(true);
        surname.setRequired(true);
        username.setRequired(true);
        phoneNumber.setRequired(true);
    }
}
