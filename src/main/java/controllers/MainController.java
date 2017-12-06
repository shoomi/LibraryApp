package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.sql.SQLException;


public class MainController {

    public static String someOperationsButtonId;


    public void addNewBook(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
       new AddBookController().showAddBookToDbWindow(actionEvent);
    }


    public void addNewUser(javafx.event.ActionEvent actionEvent) throws SQLException {
        new AddUserController().showAddUserToDbWindow(actionEvent);
    }


    public void takeBook(javafx.event.ActionEvent actionEvent) {

        checkButtonClick(actionEvent);
        System.out.println(someOperationsButtonId);

        new LoginCheck().showLoginCheckWindow(actionEvent);
    }

    public void returnBook(javafx.event.ActionEvent actionEvent) {

        checkButtonClick(actionEvent);
        System.out.println(someOperationsButtonId);
        new LoginCheck().showLoginCheckWindow(actionEvent);
    }

    private void checkButtonClick(javafx.event.ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (!(source instanceof MenuItem)) {
            return;
        }
        MenuItem clickedButton = (MenuItem) source;
        someOperationsButtonId = clickedButton.getId();
    }

    public void exit(javafx.event.ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }
}
