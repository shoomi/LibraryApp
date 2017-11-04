package controllers;

import FormValidations.FormValidadtion;
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
            stage.setMinWidth(500);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) actionEvent.getSource()).getScene().getWindow());

            stage.show();

            System.out.println(((Node) actionEvent.getSource()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUserToDb(javafx.event.ActionEvent actionEvent) {

        if (usersValuesControl()) {

            new RegUserInDb().addNewUser(loginField.getText(), firstNameField.getText(), lastNameField.getText(), telephoneField.getText(), dateField.getText());
            addingConfirmLabel.setText(String.format("user  '%s'  was successfully added to database", loginField.getText()));

            loginField.setText(null);
            firstNameField.setText(null);
            lastNameField.setText(null);
            telephoneField.setText(null);
            dateField.setText(null);
        }
    }

    private boolean usersValuesControl() {

        addingConfirmLabel.setText(null);
        boolean telephoneFieldIsValid = false;
        boolean loginIsNotBusy = false;
        boolean dateIsValid = false;

        boolean firstNameFieldIsNotEmpty = FormValidadtion.textFieldNotEmpty(firstNameField, firsNameLabel, "enter Firs Name");
        boolean lastNameFieldIsNotEmpty = FormValidadtion.textFieldNotEmpty(lastNameField, lastNameLabel, "enter Last Name");
        boolean loginFieldIsNotEmpty = FormValidadtion.textFieldNotEmpty(loginField, loginLabel, "enter login");
        boolean telephoneFieldIsNotEmpty = FormValidadtion.textFieldNotEmpty(telephoneField, telLabel, "enter telephone");
        boolean dateFieldIsNotEmpty = FormValidadtion.textFieldNotEmpty(dateField, dateLabel, "enter date in format yyyy-MM-dd");

        if (telephoneFieldIsNotEmpty) {
            telephoneFieldIsValid = FormValidadtion.telephoneIsValid(telephoneField, telLabel, "invalid value");
        }

        if (loginFieldIsNotEmpty) {
            loginIsNotBusy = FormValidadtion.loginIsNotBusy(loginField, loginLabel, "this login is busy");
        }

        if (dateFieldIsNotEmpty) {
            dateIsValid = FormValidadtion.dateIsValide(dateField, dateLabel, "wrong date format");
        }

        return firstNameFieldIsNotEmpty && lastNameFieldIsNotEmpty && loginFieldIsNotEmpty && telephoneFieldIsNotEmpty && telephoneFieldIsValid && loginIsNotBusy && dateFieldIsNotEmpty && dateIsValid;
    }

}
