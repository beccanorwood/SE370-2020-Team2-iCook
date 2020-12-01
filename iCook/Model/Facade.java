package iCook.Model;
import iCook.Model.DatabaseAccess.IngredientDAO;
import iCook.Model.DatabaseAccess.RecipeDAO;
import iCook.Model.DatabaseAccess.UserDAO;
import iCook.UsernameTakenException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Central class for all DAO classes. Has access to all DAO classes.
 *
 * @author Team 2
 * @version 11/30/2020
 */
public class Facade {

    // instance variables
    private UserDAO userDAO;
    private IngredientDAO ingredientDAO;
    private RecipeDAO recipeDAO;


    /**
     * Constructor - initializes instance variables
     */
    public Facade() {
        userDAO = new UserDAO();
        ingredientDAO = new IngredientDAO();
        recipeDAO = new RecipeDAO();
    }


    /**
     * Calls the UserDAO to determine if the user's credentials are valid.
     * If true, we request the userDAO to create the user singleton.
     *
     * @param username the username of the user trying to login
     * @param password the password of the user trying to login
     * @return true if the user can login, false otherwise
     */
    public boolean login(String username, String password)
    {
        // determine if the user's login info is valid. If so return true, false otherwise
        if ( userDAO.validUserLogin(username, password) )
        {
            userDAO.getUser(username, password);
            return true;
        }
        else
            return false;
    }


    /**
     * Calls the UserDAO to create a new user with the given credentials.
     * If the username isn't taken, we request the userDAO to create the user singleton.
     *
     * @param username the username of the user trying to sign up
     * @param password the password of the user trying to sign up
     * @throws UsernameTakenException if the username is already taken
     */
    public void signUp(String username, String password) throws UsernameTakenException
    {
        // make sure the username isn't taken
        // NEED TO THROW AN EXCEPTION HERE
        if ( userDAO.usernameIsTaken(username) ) {
            System.out.println("UsernameTakenException thrown");
            throw new UsernameTakenException("\"" + username + "\"" + " is already in use.");
        }
        else {
            // create a new User with the given username and password
            userDAO.addUser(username, password);
            userDAO.getUser(username, password);
        }
    }


    /**
     * Calls the IngredientDAO to return all system ingredients
     *
     * @return an ArrayList of Ingredient objects (all system ingredients)
     */
    public ArrayList<Ingredient> getSystemIngredients()
    {
        return ingredientDAO.getAllIngredients();
    }


    /**
     * Calls the UserDAO to return the user's ingredients
     *
     * @param userID the user's id whose ingredients we want to get
     * @return an ArrayList of UserIngredient objects (user's inventory)
     */
    public ArrayList<UserIngredient> getUserIngredients(int userID)
    {
        return userDAO.getUserIngredients(userID);
    }


    /**
     * Requests the UserDAO to update the user's inventory
     *
     * @param userID the user's id whose ingredients we want to get
     * @param updatedIngredientList the ArrayList of HashMaps that contains the user's inventory information (to be updated)
     */
    public void updateUserInventory(int userID, ArrayList<HashMap<String, String>> updatedIngredientList)
    {
        userDAO.updateUserIngredientTable(userID, updatedIngredientList);
    }


} // end of Facade class