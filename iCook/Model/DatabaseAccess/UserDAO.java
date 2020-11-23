package iCook.Model.DatabaseAccess;
import java.sql.ResultSet;
import java.sql.SQLException;

import iCook.Model.User;


/**
 * DAO for users table in DB
 *
 * @author Team 2
 * @version 11/22/2020
 */
public class UserDAO extends BaseDAO {

    /**
     * Constructor. Does nothing.
     */
    public UserDAO() throws SQLException {
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
     * Performs SQL statement to determine if a user exists with given login information
     */
    public boolean validUserLogin(String username, String password) throws SQLException {
        // perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '"+username+"' AND password = '"+password+"' LIMIT 1");

        // returns true if a there is a result
        // returns false otherwise
        return rs.next();
    }


    /**
     * Performs SQL statement to determine if a username is already taken
     */
    public boolean usernameIsTaken(String username) throws SQLException {
        // perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '"+username+"' LIMIT 1");

        // returns true if a there is a result (username already taken)
        // returns false otherwise (user name is free)
        return rs.next();
    }


    /**
     * Performs SQL statement to create a new user
     */
    public void createAccount(String username, String password) throws SQLException {
        // determine if an account already exists with the given username
        if ( usernameIsTaken(username) )
            System.out.println("Username is already in use. Please enter a new one.");
        else
        {
            // create a new account with the given username and password
            statement.execute("INSERT INTO users (user_name, password) VALUE ('"+username+"', '"+password+"') ");
            System.out.println("Account successfully made!");
        }
    }


    /**
     * Performs SQL statement to get the user id and creates the User Singleton object
     */
    public void getUser(String username, String password) throws SQLException {

        // perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '"+username+"' AND password = '"+password+"' LIMIT 1");

        while (rs.next())
        {
            int id = rs.getInt("id");
            User user = User.getUser(id, username, password);
        }

    }


}
