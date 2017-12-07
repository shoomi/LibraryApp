package controllers;

import formvalidations.LoginValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginCheck {

    @FXML
    private TextField loginField;
    @FXML
    public TextField passwordField;
    @FXML
    private Label loginLabel;
    @FXML
    public Label passwordLabel;

    @FXML
    public Button loginButton;

    public static String userLogin;

    private LoginValidation loginValidation;

    public LoginCheck()  {
        loginValidation = new LoginValidation();
    }


    public void showLoginCheckWindow(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginCheck.fxml"));
            stage.setTitle("Login Check");
            stage.setMinHeight(200);
            stage.setMinWidth(400);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void checkLogin(ActionEvent actionEvent) {

        userLogin = loginField.getText();
        String userPassword = passwordField.getText();

        switch (MainController.someOperationsButtonId) {

            case "takeBook":

                if (loginValidation.loginAndPasswordCheck(userLogin, loginLabel, userPassword, passwordLabel)) {
                    new TakeBookController().showTakeBookWindow(actionEvent);
                    closeLoginStage(actionEvent);
                }
                break;

            case "returnBook":

                if (loginValidation.loginAndPasswordCheck(userLogin, loginLabel, userPassword, passwordLabel)) {

                    new ReturnBookController().showReturnBookWindow(actionEvent);
                    closeLoginStage(actionEvent);

                }
                break;
        }
    }

    private void closeLoginStage(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}