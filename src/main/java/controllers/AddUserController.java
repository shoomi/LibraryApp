package controllers;

import formvalidations.UserValuesControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import operations.RegUserInDb;

import java.io.IOException;

public class AddUserController {

    @FXML
    private TextField loginField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField dateField;

    @FXML
    private Label loginLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label firsNameLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label telLabel;
    @FXML
    private Label addingConfirmLabel;


    public void showAddUserToDbWindow(javafx.event.ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addUser.fxml"));
            stage.setTitle("Register new book in Library");
            stage.setMinHeight(300);
            stage.setMinWidth(415);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addUserToDb(javafx.event.ActionEvent actionEvent) {

        addingConfirmLabel.setText(null);

        if (new UserValuesControl().checkValues(loginField, firstNameField, lastNameField, telephoneField, dateField, loginLabel, firsNameLabel, lastNameLabel, telLabel, dateLabel)) {

            new RegUserInDb().addNewUser(loginField.getText(), firstNameField.getText(), lastNameField.getText(), telephoneField.getText(), dateField.getText());

            addingConfirmLabel.setText(String.format(" LibUser  '%s'  was successfully added to database", loginField.getText()));

            loginField.setText(null);
            firstNameField.setText(null);
            lastNameField.setText(null);
            telephoneField.setText(null);
            dateField.setText(null);
        }
    }


}
