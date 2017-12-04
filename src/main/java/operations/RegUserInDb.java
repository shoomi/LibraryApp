package operations;


import libraryitem.LibUser;
import libWorker.LibWorker;
import libraryitem.User;

import java.sql.SQLException;

public class RegUserInDb {

    public void addNewUser(String login, String firstName, String lastName, String telephone, String dateOfBirth) {

        User user = new LibUser();

        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTelephone(telephone);
        user.setDate_of_birth(dateOfBirth);

        try {
            new LibWorker().addNewUserToDbUsers(user);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}