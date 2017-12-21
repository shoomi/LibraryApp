package utils.connection;

import java.sql.Connection;

public class NewConnection {

    public static Connection getConnection() {
        return C3P0DataSource.getInstance().getConnection();
// return DBConnector.getConnection();
    }

}
