package controllers;

import FormValidations.FormValidadtion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import operations.RegBookInDb;

import java.io.IOException;
import java.sql.SQLException;

public class AddBookController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField yearBookField;
    @FXML
    private TextField numberOfBooksField;

    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label numberOfBooksLabel;
    @FXML
    private Label yearBookLabel;
    @FXML
    private Label addingBookConfirmLabel;

    public void showAddBookToDbWindow(javafx.event.ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addBook.fxml"));
            stage.setTitle("Register new book in Library");
            stage.setMinHeight(300);
            stage.setMinWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addBookToDb(javafx.event.ActionEvent actionEvent) {

        if(bookValuesControl()){
            try {
                new RegBookInDb().addNewBook(titleField.getText(),authorField.getText(), yearBookField.getText(), Integer.parseInt(numberOfBooksField.getText()));
                addingBookConfirmLabel.setText(String.format("The book '%s' '%s' was successfully added to database ",titleField.getText(),authorField.getText()));
                titleField.setText(null);
                authorField.setText(null);
                yearBookField.setText(null);
                numberOfBooksField.setText(null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean bookValuesControl() {

        boolean yaerIsValid = false;
        boolean amountOfBooksIsValid = false;
        boolean titleIsNotEmpty = FormValidadtion.textFieldNotEmpty(titleField, titleLabel, "enter Title");
        boolean authorIsNotEmpty = FormValidadtion.textFieldNotEmpty(authorField, authorLabel, "enter Author");
        boolean yearIsNotEmpty = FormValidadtion.textFieldNotEmpty(yearBookField, yearBookLabel, "enter year in format YYYY");
        boolean amountIsNotEmpty = FormValidadtion.textFieldNotEmpty(numberOfBooksField, numberOfBooksLabel, "enter number of books");

        if (yearIsNotEmpty) {
            yaerIsValid = FormValidadtion.bookYearIsValid(yearBookField, yearBookLabel, "invalid value");
        }
        if (amountIsNotEmpty) {
            amountOfBooksIsValid = FormValidadtion.numberOfBooksIsValide(numberOfBooksField, numberOfBooksLabel, "invalid value");
        }

        return titleIsNotEmpty && authorIsNotEmpty && yearIsNotEmpty && amountIsNotEmpty && yaerIsValid && amountOfBooksIsValid;

    }

}
