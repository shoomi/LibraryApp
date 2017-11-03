package controllers;


import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

public class Controller {

    public static String someOperationsButtonId;

    public void addNewBook(javafx.event.ActionEvent actionEvent) {
        new AddBookController().showAddBookToDbWindow(actionEvent);
    }

    public void addNewUser(javafx.event.ActionEvent actionEvent) {
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

    public void addBookToDb(javafx.event.ActionEvent actionEvent) {

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
