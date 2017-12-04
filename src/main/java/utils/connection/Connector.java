package utils.connection;

import java.sql.Connection;

public interface Connector {
    Connection getConnection();
    void closeConnection();
}
