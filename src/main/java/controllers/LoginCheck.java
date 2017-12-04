package controllers;

import dialogs.Dialogs;
import libWorker.LibWorker;
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
import java.sql.SQLException;

public class LoginCheck {

    @FXML
    private TextField loginCheckField;
    @FXML
    private Label loginCheckLabel;

    public static String userLogin;
    @FXML
    public Button loginCheckButton;

    private LibWorker libWorker;

    public LoginCheck() {
        libWorker = new LibWorker();
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

        userLogin = loginCheckField.getText();

        switch (MainController.someOperationsButtonId) {

            case "takeBook":

                try {

                    if (libWorker.loginIsValidated(userLogin)) {

                        new TakeBookController().showTakeBookWindow(actionEvent);
                        closeLoginStage(actionEvent);

                    } else Dialogs.showInfoDialog("Information", "The login '" + userLogin + "' is not registered");

                    loginCheckLabel.setText("this login is not registered");

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "returnBook":

                try {
                    ReturnBookController returnBookController = new ReturnBookController();

                    if (libWorker.loginIsValidated(userLogin)) {

                        returnBookController.showReturnBookWindow(actionEvent);
                        closeLoginStage(actionEvent);

                    } else {
                        Dialogs.showInfoDialog("Information", "The login '" + userLogin + "' is not registered");
                        loginCheckLabel.setText("this login is not registered");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void closeLoginStage(ActionEvent actionEvent) throws SQLException {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
