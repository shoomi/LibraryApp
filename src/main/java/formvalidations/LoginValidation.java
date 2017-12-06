package formvalidations;

import javafx.scene.control.Label;
import libworker.LibWorker;
import password.Password;

import java.sql.SQLException;

public class LoginValidation {

    private LibWorker libWorker;

    public LoginValidation() {
        libWorker = new LibWorker();
    }

    public boolean loginAndPasswordCheck(String login, Label loginLabel, String password, Label passwordLabel) {
        boolean loginExists = false;
        boolean passwordIsValid = false;

        try {
            loginExists = libWorker.loginExists(login);
            if (loginExists) {
                loginLabel.setText(null);
                passwordIsValid = Password.checkPassword(password, libWorker.getUserPassword(login));
                if (!passwordIsValid) {
                    passwordLabel.setText("You entered incorrect password");
                }
            } else
                loginLabel.setText("this login is not registered");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginExists && passwordIsValid;
    }


}
