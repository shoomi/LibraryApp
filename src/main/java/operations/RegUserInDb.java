package operations;

import LibWorker.LibWorker;
import UtilClasses.DBConnector;
import userAndBookClasses.User;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUserInDb {

    public void addNewUser(String login, String firstName, String lastName, String telephone, String dateOfBirth) {
        User user = new User();

        user.setLogin(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setTelephone(telephone);
        user.setDate_of_birth(dateOfBirth);


        try {
            new LibWorker().addNewUserToDbUsers(user);
            System.out.println("\nYou were successfully added to the database!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            dbConnector.closeConnection();
        }
    }



}
