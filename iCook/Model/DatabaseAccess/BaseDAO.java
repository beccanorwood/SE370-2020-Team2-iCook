package iCook.Model.DatabaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Superclass for DAO connections
 *
 * @author Team 2
 * @version 11/7/2020
 */
public class BaseDAO {
    private static final String username = "se370";
    private static final String password = "Team2!";
    private static final String database = "icook";
    private static final String url = "jdbc:mysql://izzy-se370.ca3u8x8hrfhy.us-west-1.rds.amazonaws.com:3306/" + database;

    public static Connection connection = null;
    public Statement statement;


    /**
     * Constructor
     */
    BaseDAO() throws SQLException {
        // Register the iCook.Controller.Driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        // Getting the connection
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection established....");

        // Creating a statement object
        statement = connection.createStatement();
    }

}