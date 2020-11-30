package iCook.Model.DatabaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Superclass for DAO classes that establishes the connection to iCook's database
 *
 * @author Team 2
 * @version 11/28/2020
 */
public class BaseDAO {

    // connection to the database needs to be static
    private static Connection connection = null;

    // initialize instance variables
    private final String username = "se370";
    private final String password = "Team2!";
    private final String database = "icook";
    private final String url = "jdbc:mysql://izzy-se370.ca3u8x8hrfhy.us-west-1.rds.amazonaws.com:3306/" + database;

    // variables available to classes that extend BaseDAO
    protected Statement statement;


    /**
     * Constructor that establishes a connection to iCook's database
     */
    BaseDAO() {
        try {
            connect();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Establishes a connection to the iCook database and throws a SQLException
     * if there is a problem getting the connection.
     */
    private void connect() throws SQLException
    {
        // only establish a connection to the database if the connection is null
        if (connection == null) {
            // Register the iCook.Controller.Driver
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            // Getting the connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established....");
        }

        // Creating a statement object
        statement = connection.createStatement();
    }


} // end of BaseDAO class