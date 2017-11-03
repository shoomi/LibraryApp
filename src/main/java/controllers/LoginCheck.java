package controllers;

import Dialogs.Dialogs;
import LibWorker.LibWorker;
import javafx.event.ActionEvent;
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
    public TextField loginCheckField;
    public Label loginCheckLabel;
    public static String userLogin;
    public Button loginCheckButton;

    public void showLoginCheckWindow(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginCheck.fxml"));
            stage.setTitle("Login Check");
            stage.setMinHeight(300);
            stage.setMinWidth(500);
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

        switch (Controller.someOperationsButtonId) {

            case "takeBook":

                try {

                    if (new LibWorker().loginIsValidated(userLogin)) {

                        new TakeBookController().showTakeBookWindow(actionEvent);

                        Node source = (Node) actionEvent.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();

                    } else Dialogs.showInfoDialog("Information", "The login '" + userLogin + "' is not registered");
                    loginCheckLabel.setText("this login is not registered");


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case "returnBook":

                try {

                    if (new LibWorker().loginIsValidated(userLogin)) {

                        if (new LibWorker().haveUserSomethingToReturn(userLogin)) {

                            new ReturnBookController().showReturnBookWindow(actionEvent);

                            Node source = (Node) actionEvent.getSource();
                            Stage stage = (Stage) source.getScene().getWindow();
                            stage.close();

                        } else Dialogs.showInfoDialog("Information", "It's looks like you have nothing to return");

                    } else Dialogs.showInfoDialog("Information", "The login '" + userLogin + "' is not registered");
                    loginCheckLabel.setText("this login is not registered");


                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
