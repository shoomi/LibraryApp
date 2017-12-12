package utils.connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3P0DataSource {

    private static C3P0DataSource dataSource;
    private ComboPooledDataSource comboPooledDataSource;

    private C3P0DataSource() {

            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/mylibrary?useSSL=false");
            comboPooledDataSource.setUser("root");
            comboPooledDataSource.setPassword("root");
    }

    public static C3P0DataSource getInstance() {
        if (dataSource == null)
            dataSource = new C3P0DataSource();
        return dataSource;
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
