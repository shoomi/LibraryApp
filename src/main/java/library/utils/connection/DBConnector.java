package library.utils.connection;

import library.Dialogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {


    public static Connection getConnection() {

        Connection connection = null;

        final String URL = "jdbc:mysql://localhost:3306/mylibrary?useSSL=false";
        final String USER = "shoomi";
        final String PASS = "Infinitron4";

        try {
            System.out.println("I am connected");
            connection = DriverManager.getConnection(URL, USER, PASS);

            if (!connection.isClosed()) {
                System.out.println("connection is established");
            }
        } catch (SQLException e) {
            Dialogs.showInfoDialog("ERROR!!!", "Can't connect to the server");
        }

        return connection;
    }

}