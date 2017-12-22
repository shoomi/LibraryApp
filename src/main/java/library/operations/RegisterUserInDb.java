package library.operations;


import library.libitems.LibUser;
import library.LibWorker;
import library.libitems.User;
import library.Password;

public class RegisterUserInDb {

    private LibWorker libWorker;

    public RegisterUserInDb() {
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

        libWorker.addNewUserToDbUsers(user);

    }


}