package library.utils.connection;

import java.sql.Connection;

public class NewConnection {

    public static Connection getConnection() {
        return C3P0DataSource.getInstance().getConnection();   // we use the Connection pool
        // return DBConnector.getConnection();     // we can switch to DBConnector if necessary
    }

}
