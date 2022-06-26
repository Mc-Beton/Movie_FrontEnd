package com.kodilla.moviePage.user.view;

import com.kodilla.moviePage.user.domain.User;
import com.kodilla.moviePage.user.domain.UserForm;
import com.kodilla.moviePage.user.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("user")
public class UserMainView extends VerticalLayout {

    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();
    TextField userToDelete = new TextField();
    Button goToMainMenu = new Button("Main menu");
    TextField name = new TextField();

    UserService userService;
    UserForm userForm;

    public UserMainView(UserService userService){
        this.userService = userService;
        addClassName("user-view");
        setSizeFull();

        configureGrid();
        configureCustomerForm();

        add(getToolbar(), getContent());
        updateList();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, userForm);
        content.setFlexGrow(15,grid);
        content.setFlexGrow(1,userForm);
        content.addClassName("content");
        content.setSizeFull();
        return content;
    }

    private void configureCustomerForm() {
        userForm = new UserForm();
        userForm.setWidth("25em");
    }

    private void updateList() {
        grid.setItems(userService.getUsers(filterText.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e-> updateList());
        name.setPlaceholder("Filter by name...");
        name.setClearButtonVisible(true);
        name.setValueChangeMode(ValueChangeMode.LAZY);
        name.addValueChangeListener(e->grid.setItems(userService.getUsers(name.getValue())));

        userToDelete.setPlaceholder("User to delete - ID");
        Button delete = new Button("Delete");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(event -> {
            userService.deleteUser(Long.valueOf(userToDelete.getValue()));
            UI.getCurrent().getPage().reload();
        });

        Button updateView = new Button("Update");
        updateView.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        updateView.addClickListener(e-> UI.getCurrent().navigate("updateUserData"));

        goToMainMenu.addClickListener(event -> UI.getCurrent().navigate("movie"));

        HorizontalLayout toolbar = new HorizontalLayout(filterText, name, userToDelete, delete, updateView, goToMainMenu);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void configureGrid() {
        grid.addClassName("user-grid");
        setSizeFull();
        grid.setColumns("id","name","surname","username");
        grid.getColumns().forEach(col->col.setAutoWidth(true));
        grid.addColumn(User::getEmail).setHeader("Email");
    }
}
