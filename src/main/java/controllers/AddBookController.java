package controllers;

import formvalidations.BookValuesControl;
import javafx.event.ActionEvent;
import operations.RegBookInDb;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;


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

    private RegBookInDb regBookInDb;

    public AddBookController (){
        regBookInDb = new RegBookInDb();
    }


    public void showAddBookToDbWindow(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addBook.fxml"));
            stage.setTitle("Register new book in Library");
            stage.setMinHeight(180);
            stage.setMinWidth(450);
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

        addingBookConfirmLabel.setText(null);

        if (new BookValuesControl().checkValues(titleField, authorField, yearBookField, numberOfBooksField, titleLabel, authorLabel, numberOfBooksLabel, yearBookLabel)) {

            regBookInDb.addNewBook(titleField.getText(), authorField.getText(), yearBookField.getText(), Integer.parseInt(numberOfBooksField.getText()));
            addingBookConfirmLabel.setText(String.format("The book '%s' '%s' was successfully added to database ", titleField.getText(), authorField.getText()));

            titleField.setText(null);
            authorField.setText(null);
            yearBookField.setText(null);
            numberOfBooksField.setText(null);
        }
    }


}