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
    public void addUserIngredient(int userID, int ingredientID, int quantity) throws SQLException {
        statement.execute("INSERT INTO user_ingredients (user_id, ingredient_id, quantity) VALUE ("+userID+", "+ingredientID+", "+quantity+") ");
    }


    /**
     * Performs SQL statement to update an ingredient's quantity
     */
    public void updateUserIngredient(int ID, int quantity) throws SQLException {
        statement.execute("UPDATE user_ingredients SET quantity = '"+quantity+"' WHERE ID = "+ID+" ");
    }


    /**
     * Performs SQL statement to update an ingredient's quantity
     */
    public void deleteUserIngredient(int ID) throws SQLException {
        statement.execute("DELETE FROM user_ingredients WHERE ID = "+ID+" ");
    }


    /**
     * Performs SQL statement to nicely display all ingredients in the table
     */
    public void displayIngredientsTable() throws SQLException {
        // Perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM ingredients");

        // read each row in the table
        while (rs.next())
        {
            // read each column of the row
            String name = rs.getString("name");
            String unit_of_measure = rs.getString("unit_of_measure");

            // print out the row
            System.out.println(name + " measured in " + unit_of_measure );
        }
    }


    /**
     * Performs SQL statement to return the quantity field of an ingredient
     */
    public String getIngredientMeasurement(int ID) throws SQLException {
        // perform the query
        ResultSet rs = statement.executeQuery("SELECT unit_of_measure FROM ingredients WHERE ID = "+ID+" ");
        rs.next();  // move the 'cursor' to the first row (ALWAYS NEED THIS WHEN QUERYING)

        // return the quantity field of the specified ingredient
        return rs.getString("unit_of_measure");
    }
}
