package utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Handles opening and closing the database connection
 */
public class DBConnection {

    private static final String jdbcURL = "jdbc:mysql://wgudb.ucertify.com/WJ080Og";
    private static final String username = "U080Og";
    private static final String password = "53689190807";
    private static Connection conn = null;

    /**
     * Connects to the database
     * @return the connection to the database
     */
    public static Connection startConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        }
        catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Closes the connection to the database
     */
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed");
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Gets the connection to the database
     * @return the connection to the database
     */
    public static Connection getConn() {
        return conn;
    }

}
