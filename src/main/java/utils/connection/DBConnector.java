package utils.connection;

import dialogs.Dialogs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector implements Connector {

    private Connection connection;

    @Override
    public Connection getConnection() {
        final String URL = "jdbc:mysql://localhost:3306/mylibrary";
        final String USER = "root";
        final String PASS = "root";

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

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}