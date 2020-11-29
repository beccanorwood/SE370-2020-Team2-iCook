package iCook.Model;
import iCook.Model.DatabaseAccess.IngredientDAO;
import iCook.Model.DatabaseAccess.UserDAO;
import iCook.UsernameTakenException;

import java.util.ArrayList;

/**
 * Central class for all DAO objects. Has access to all DAO objects.
 *
 * @author Team 2
 * @version 11/28/2020
 */
public class Facade {

    /**
     * Constructor. Does nothing.
     */
    public Facade() {
    }


    /**
     * Calls the UserDAO to determine if the user's credentials are valid and
     * returns true if so, false otherwise.
     *
     * If true, we request the userDAO to create the user singleton
     */
    public boolean login(String username, String password)
    {
        // create a UserDAO object
        UserDAO userDAO;

        // initialize connection to DB
        userDAO = new UserDAO();

        // determine if the user's login info is valid, if so return true, false otherwise
        if ( userDAO.validUserLogin(username, password) )
        {
            userDAO.getUser(username, password);
            return true;
        }
        else
            return false;
    }


    /**
     * Calls the UserDAO to create a new user with the given credentials
     *
     * If the username isn't taken, we request the userDAO to create the user singleton
     */
    public void signUp(String username, String password)
    {
        // create a UserDAO object
        UserDAO userDAO;

        // initialize connection to DB
        userDAO = new UserDAO();

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
     * Calls the IngredientDAO to return an ArrayList of Ingredient objects (all system objects)
     */
    public ArrayList<Ingredient> getSystemIngredients()
    {
        IngredientDAO ingDAO;
        ingDAO = new IngredientDAO();

        return ingDAO.getAllIngredients();
    }


    /**
     * Calls the UserDAO to return an ArrayList of UserIngredient objects (all user's ingredients)
     */
    public ArrayList<UserIngredient> getUserIngredients(int userID)
    {
        UserDAO userDAO;
        userDAO = new UserDAO();

        return userDAO.getUserIngredients(userID);
    }

} // end of Facade class