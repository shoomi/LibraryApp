package library.controllers;

import javafx.application.Platform;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.sql.SQLException;


public class MainController {

    // hewe we record what button id has been clicked in MenuItem (take or return book), because previously we run the LoginCheck stage
    public static String someOperationsButtonId;


    public void addNewBook(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
       new RegisterBookController().showAddBookToDbWindow(actionEvent);
    }


    public void addNewUser(javafx.event.ActionEvent actionEvent) throws SQLException {
        new RegisterUserController().showAddUserToDbWindow(actionEvent);
    }


    public void takeBook(javafx.event.ActionEvent actionEvent) {
        checkButtonClick(actionEvent);     // we run LoginCheck stage before opening the List of books
        new LoginCheck().showLoginCheckWindow(actionEvent);
    }

    public void returnBook(javafx.event.ActionEvent actionEvent) {
        checkButtonClick(actionEvent);     // we run LoginCheck stage before opening the List of borrowed books
        new LoginCheck().showLoginCheckWindow(actionEvent);
    }


    // here we get the information what button has been clicked
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
