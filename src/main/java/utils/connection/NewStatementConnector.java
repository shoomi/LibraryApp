package utils.connection;

import libworker.LibWorker;

import java.sql.SQLException;
import java.sql.Statement;

public class NewStatementConnector {

    public Statement returnStatement() {
        Connector dbConnector = new DBConnector();
        Statement statement = null;

        try {
            statement = dbConnector.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return statement;
    }


}
