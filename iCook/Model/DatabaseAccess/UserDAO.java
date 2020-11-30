package iCook.Model.DatabaseAccess;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import iCook.Model.User;
import iCook.Model.UserIngredient;

/**
 * DAO class for the User table in iCook's database.
 *
 * @author Team 2
 * @version 11/28/2020
 */
public class UserDAO extends BaseDAO {

    /**
     * Constructor. Does nothing.
     */
    public UserDAO() {
    }


    /**
     * Performs a SQL statement to add an ingredient to the user_ingredient table
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
     * Performs a SQL statement to update a user ingredient's quantity
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
     * Performs a SQL statement to remove a user ingredient
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
     * Performs a SQL statement to determine if a user exists with the given login information
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
     * Performs a SQL statement to create a new user in the user table
     */
    public void addUser(String username, String password) {
        try {
            // if an account does not already exists with the given username
            if ( !usernameIsTaken(username) ) {
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
     * Performs a SQL statement to get the user id and creates the User Singleton object
     */
    public void getUser(String username, String password) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '" + username + "' AND password = '" + password + "' LIMIT 1");

            while (rs.next()) {
                int id = rs.getInt("id");

                // create the user singleton (SHOULD BE THE ONLY PLACE THIS HAPPENS)
                User user = User.getUser(id, username, password);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs a SQL statement to return an Arraylist of the user's ingredients
     */
    public ArrayList<UserIngredient> getUserIngredients(int userID) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT UI.id AS thisID, UI.quantity, I.id as ingID, I.name, I.unit_of_measure FROM user_ingredients UI, ingredients I WHERE UI.user_id = '" + userID + "' AND UI.ingredient_id = I.id");
            ArrayList<UserIngredient> userIngredients = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("thisID");
                int ingredientID = rs.getInt("ingID");
                int quantity = rs.getInt("quantity");
                String name = rs.getString("name");
                String unit_of_measure = rs.getString("unit_of_measure");

                // add the user ingredient to the array list
                userIngredients.add(new UserIngredient(id, userID, ingredientID, quantity, name, unit_of_measure));
            }

            return userIngredients;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }


    /**
     * Performs a SQL statement to return the name of the ingredient from a user ingredient object
     */
    public String getUserIngredientName(int ingredientID) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT I.name FROM user_ingredients UI, ingredients I WHERE ingredient_id = '" + ingredientID + "' AND UI.ingredient_id = I.id");

            if (rs.next())
                return rs.getString("name");
            else
                return null;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }


    /**
     * Performs a SQL statement to return the unit of measure of the ingredient from a user ingredient object
     */
    public String getUserIngredientUnitOfMeasure(int ingredientID) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT I.unit_of_measure FROM user_ingredients UI, ingredients I WHERE ingredient_id = '" + ingredientID + "' AND UI.ingredient_id = I.id");

            if (rs.next())
                return rs.getString("unit_of_measure");
            else
                return null;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }


} // end of UserDAO class
