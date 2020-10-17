import sun.tools.tree.ReturnStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Connects to the database and allows for SQL specific calls
 *
 * @author Team 2
 * @version 10/16/2020
 */
public class DatabaseConnection {

    private static final String username = "se370";
    private static final String password = "Team2!";
    private static final String database = "icook";
    private static final String url = "jdbc:mysql://izzy-se370.ca3u8x8hrfhy.us-west-1.rds.amazonaws.com:3306/" + database;

    public Statement statement;


    /**
     * Constructor
     */
    DatabaseConnection() throws SQLException {
        // Register the Driver
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

        // Getting the connection
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection established....");

        // Creating a statement object
        statement = connection.createStatement();
    }


    /**
     * Performs SQL statement to add an ingredient to the ingredients table
     */
    public void addIngredient(String name, int quantity) throws SQLException {
        statement.execute("INSERT INTO ingredients (name, quantity) VALUE ('"+name+"', '"+quantity+"') ");
    }


    /**
     * Performs SQL statement to update an ingredient's quantity
     */
    public void updateIngredient(String name, int quantity) throws SQLException {
        statement.execute("UPDATE ingredients SET quantity = '"+quantity+"' WHERE name = '"+name+"' ");
    }


    /**
     * Performs SQL statement to update an ingredient's quantity
     */
    public void deleteIngredient(String name) throws SQLException {
        statement.execute("DELETE FROM ingredients WHERE name = '"+name+"' ");
    }


    /**
     * Performs SQL statement to nicely display all ingredients in the table
     */
    public void displayIngredients() throws SQLException {
        // Perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM ingredients");

        // read each row in the table
        while (rs.next())
        {
            // read each column of the row
            String name = rs.getString("name");
            int quantity = rs.getInt("quantity");

            // print out the row
            System.out.println(name + " " + quantity);
        }
    }


    /**
     * Performs SQL statement to return the quantity field of an ingredient
     */
    public int getIngredientQuantity(String name) throws SQLException {
        // perform the query
        ResultSet rs = statement.executeQuery("SELECT quantity FROM ingredients WHERE name = '"+name+"' ");
        rs.next();  // move the 'cursor' to the first row (ALWAYS NEED THIS WHEN QUERYING)

        // return the quantity field of the specified ingredient
        return rs.getInt("quantity");
    }
}
