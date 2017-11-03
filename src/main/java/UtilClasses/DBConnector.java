package UtilClasses;

import Dialogs.Dialogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/mylibrary";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static Connection connection;

  static   {

        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
            if (connection.isClosed()) {
                System.out.println("connection is established");
            }
        } catch (SQLException e) {
            Dialogs.showErrorDialog("ERROR!!!", "Can't connect to the server");
        }
    }

    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}