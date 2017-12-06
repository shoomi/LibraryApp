package operations;


import libitems.LibUser;
import libworker.LibWorker;
import libitems.User;
import password.Password;

import java.sql.SQLException;

public class RegUserInDb {

    private LibWorker libWorker;

    public RegUserInDb() {
        libWorker = new LibWorker();
    }

    public void addNewUser(String login, String firstName, String lastName, String telephone, String dateOfBirth, String password) {

        User user = new LibUser();

        user.setLogin(login);
        user.setPassword(Password.hashPassword(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTelephone(telephone);
        user.setDate_of_birth(dateOfBirth);

        try {
            libWorker.addNewUserToDbUsers(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}