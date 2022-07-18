package com.kodilla.moviePage.security.views;

import com.kodilla.moviePage.security.service.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@Route("")
@PermitAll
public class AfterLoginView extends VerticalLayout {

    public AfterLoginView(@Autowired SecurityService securityService) {
        setAlignItems(Alignment.CENTER);
        add(new H2("You logged in"));
        add(new H3(securityService.getAuthenticatedUser().getUsername()));
        Button button1 = new Button("Go back to main page");
        button1.addClickListener(e ->
                UI.getCurrent().navigate("movie"));
        add(button1);
    }
}
