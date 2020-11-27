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
    public UserDAO() {
    }

    /**
     * Performs SQL statement to add an ingredient to the ingredients table
     */
    public void addUserIngredient(int userID, int ingredientID, int quantity) {
        try {
            statement.execute("INSERT INTO user_ingredients (user_id, ingredient_id, quantity) VALUE (" + userID + ", " + ingredientID + ", " + quantity + ") ");
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs SQL statement to update an ingredient's quantity
     */
    public void updateUserIngredient(int ID, int quantity) {
        try {
            statement.execute("UPDATE user_ingredients SET quantity = '" + quantity + "' WHERE ID = " + ID + " ");
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs SQL statement to update an ingredient's quantity
     */
    public void deleteUserIngredient(int ID) {
        try {
            statement.execute("DELETE FROM user_ingredients WHERE ID = " + ID + " ");
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs SQL statement to determine if a user exists with given login information
     */
    public boolean validUserLogin(String username, String password) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "' LIMIT 1");

            // returns true if a there is a result
            // returns false otherwise
            return rs.next();
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return false;
        }
    }


    /**
     * Performs SQL statement to determine if a username is already taken
     */
    public boolean usernameIsTaken(String username) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '" + username + "' LIMIT 1");

            // returns true if a there is a result (username already taken)
            // returns false otherwise (user name is free)
            return rs.next();
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return false;
        }
    }


    /**
     * Performs SQL statement to create a new user
     */
    public void createAccount(String username, String password) {
        try {
            // determine if an account already exists with the given username
            if (usernameIsTaken(username))
                System.out.println("Username is already in use. Please enter a new one.");
            else {
                // create a new account with the given username and password
                statement.execute("INSERT INTO users (user_name, password) VALUE ('" + username + "', '" + password + "') ");
                System.out.println("Account successfully made!");
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs SQL statement to get the user id and creates the User Singleton object
     */
    public void getUser(String username, String password) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "' LIMIT 1");

            while (rs.next()) {
                int id = rs.getInt("id");

                // establish the user singleton
                User user = User.getUser(id, username, password);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

}
