package library.controllers;

import library.formvalidations.BookValuesControl;
import javafx.event.ActionEvent;
import library.operations.RegisterBookInDb;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import library.utils.NumberFieldSetter;

import java.io.IOException;


public class RegisterBookController {

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

    private RegisterBookInDb registerBookInDb;
    private BookValuesControl bookValuesControl;

    public RegisterBookController() {
        registerBookInDb = new RegisterBookInDb();
        bookValuesControl = new BookValuesControl();
    }


    public void showAddBookToDbWindow(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/registerBook.fxml"));
            stage.setTitle("Register new book");
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

        if (bookValuesControl.checkValues(titleField, authorField, yearBookField, numberOfBooksField, titleLabel, authorLabel, numberOfBooksLabel, yearBookLabel)) {

            registerBookInDb.addNewBook(titleField.getText(), authorField.getText(), yearBookField.getText(), Integer.parseInt(numberOfBooksField.getText()));
            addingBookConfirmLabel.setText(String.format("The book '%s' '%s' was successfully added to database ", titleField.getText(), authorField.getText()));

            titleField.setText(null);
            authorField.setText(null);
            yearBookField.setText(null);
            numberOfBooksField.setText(null);


        }
    }


    @FXML
    public void initialize() {

        NumberFieldSetter.setTextField(numberOfBooksField);
    }


}