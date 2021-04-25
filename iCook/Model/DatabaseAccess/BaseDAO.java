package iCook.Model.DatabaseAccess;

import java.sql.*;

/**
 * Superclass for DAO classes that establishes the connection to iCook's database
 *
 * @author Team 2
 * @version 4/25/2021
 */
public class BaseDAO {

    // connection to the database is independent of classes
    private static Connection connection = null;

    // initialize instance variables
    private final String username = "se370";
    private final String password = "Team2!";
    private final String database = "icook";
    private final String url = "jdbc:mysql://izzy-se370.ca3u8x8hrfhy.us-west-1.rds.amazonaws.com:3306/" + database;


    /**
     * Constructor that establishes a connection to iCook's database
     * (only DAO classes have access to it)
     */
    protected BaseDAO() {
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
     *
     * @throws SQLException if there is a problem connecting to the database
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
    }


    /**
     * Returns a newly created Statement object. This allows DAO classes who extend BaseDAO to
     * create their own statements to perform SQL statements.
     * Method is available only to classes that extend BaseDAO.
     *
     * @return a Statement object created from the established connection
     * @throws SQLException if there is a problem with the connection
     */
    protected Statement createStatement() throws SQLException
    {
        // make the statement traversable and not one direction only
        return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }


} // end of BaseDAO class