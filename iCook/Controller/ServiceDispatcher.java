package iCook.Controller;
import iCook.Model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The main controller class for iCook's MVC design pattern. Connects the View with the Model.
 *
 * @author Team 2
 * @version 11/29/2020
 */
public class ServiceDispatcher {

    // instance variables
    private Facade facade;
    private User user = null;
    private ArrayList<Ingredient> systemIngredients = new ArrayList<>();
    private ArrayList<UserIngredient> userIngredients = new ArrayList<>();


    /**
     * Constructor - initializes instance variables (except for the user singleton && userIngredients)
     */
    public ServiceDispatcher()
    {
        facade = new Facade();
        getSystemIngredients();
    }


    /**
     * Returns true if the user's credentials are valid, false otherwise.
     * Requests the facade to try to log the user in.
     *
     * If the facade successfully logs the user in, initialize the user singleton.
     */
    public boolean login(String username, String password)
    {
        if ( facade.login(username, password) ) {
            user = User.getUser();
            return true;
        }
        else
            return false;
    }


    /**
     * Requests the facade to create a new user with the given username and password.
     * Initializes the user singleton with the newly created account.
     */
    public void signUp(String username, String password)
    {
        facade.signUp(username, password);
        user = User.getUser();
    }


    /**
     * TESTING --- displays the singleton's variables
     */
    public void displayUser()
    {
        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
    }


    /**
     * Returns true if the singleton object is not null
     */
    public boolean isLoggedIn()
    {
        return ( user == null ) ? false : true;
    }


    /**
     * Returns a SORTED ArrayList of all system ingredient's names
     */
    public ArrayList<String> getAllSystemIngredients()
    {
        ArrayList<String> nameList = new ArrayList<>();

        for(int i = 0; i < systemIngredients.size(); i++)
        {
            nameList.add(systemIngredients.get(i).getIngredientName());
        }

        // sort the list of ingredient names
        Collections.sort(nameList);
        return nameList;
    }


    /**
     * Initializes systemIngredients with an ArrayList containing Ingredient objects
     */
    private void getSystemIngredients()
    {
        systemIngredients = facade.getSystemIngredients();
    }


    /**
     * Returns a HashMap of the user's ingredients
     */
    public HashMap<String, String> getUserInventory()
    {
        // initialize the user's ingredients (inventory)
        getUserIngredients();

        // use a hashmap to store the info of the UserIngredient object
        HashMap<String, String> userIngList = new HashMap<String, String>();

        // for every user ingredient, add a key/value
        for (UserIngredient userIngredient : userIngredients)
        {
            // put the UserIngredient ID in the map
            userIngList.put("id", Integer.toString(userIngredient.getUserIngredientID()));

            // put the Ingredient's name in the map
            userIngList.put("name", userIngredient.getUserIngredientName());

            // put the Ingredient's quantity in the map
            userIngList.put("quantity", Double.toString(userIngredient.getQuantity()));

            // put the Ingredient's unit of measure in the map
            userIngList.put("unit_of_measure", userIngredient.getUserIngredientUnitOfMeasure());
        }

        // return the hashmap
        return userIngList;
    }


    /**
     * Initializes userIngredients with an ArrayList containing UserIngredient objects
     */
    private void getUserIngredients()
    {
        userIngredients = facade.getUserIngredients(user.getId());
    }


} // end of ServiceDispatcher class