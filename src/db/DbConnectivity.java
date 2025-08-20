package db;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class DbConnectivity {
    public Connection connect(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully established connection");
        } catch (SQLException e) {
            System.out.println("Error connecting" + e.getMessage());
        }
        return connection;
    }

}
