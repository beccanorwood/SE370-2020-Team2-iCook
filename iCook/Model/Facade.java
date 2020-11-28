package iCook.Model;
import iCook.Model.*;
import iCook.Model.DatabaseAccess.IngredientDAO;
import iCook.Model.DatabaseAccess.UserDAO;

import java.util.ArrayList;

public class Facade {

    /**
     * Constructor. Does nothing.
     */
    public Facade() {
    }


    /**
     * Calls the UserDAO to determine if the user's credentials are valid and
     * returns true if so, false otherwise.
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
               System.out.println("Username is already in use. Please enter a new one.");
        }
        else {
            // create a new User with the given username and password
            userDAO.createAccount(username, password);
            userDAO.getUser(username, password);
        }
    }


    public ArrayList<Ingredient> getSystemIngredients()
    {
        IngredientDAO ingDAO;
        ingDAO = new IngredientDAO();

        return ingDAO.getAllIngredients();
    }

} // end of Facade class