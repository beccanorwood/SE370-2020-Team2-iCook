package iCook.Controller;
import iCook.Model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main controller class for iCook's MVC design pattern. Communicates between the View and Model packages.
 *
 * @author Team 2
 * @version 11/29/2020
 */
public class ServiceDispatcher {

    // user need to be static (not unique for each ServiceDispatcher object)
    private static User user = null;


    // instance variables
    private Facade facade;
    private ArrayList<Ingredient> systemIngredients;
    private ArrayList<UserIngredient> userIngredients;


    /**
     * Constructor - initializes instance variables.
     * Calls getSystemIngredients to populate systemIngredients
     */
    public ServiceDispatcher()
    {
        facade = new Facade();
        systemIngredients = new ArrayList<>();
        userIngredients = new ArrayList<>();
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
     * Returns an ArrayList of HashMaps containing all system ingredients (contains the name and unit of measure)
     */
    public ArrayList<HashMap<String, String>> getAllSystemIngredients()
    {
        // the ingredients will be stored in an ArrayList
        ArrayList<HashMap<String, String>> allIngredients = new ArrayList<>();

        // for every ingredient, add a key/value
        for(int i = 0; i < systemIngredients.size(); i++)
        {
            // use a hashmap to store the info of the Ingredient object
            HashMap<String, String> ingredientMap = new HashMap<>();

            // put the Ingredient name in the map
            ingredientMap.put("name", systemIngredients.get(i).getIngredientName());

            // put the Ingredient unit of measure in the map
            ingredientMap.put("unit_of_measure", systemIngredients.get(i).getUnitOfMeasure());

            // add the map to the ArrayList
            allIngredients.add(ingredientMap);
        }

        // return the ArrayList
        return allIngredients;
    }


    /**
     * Initializes systemIngredients with an ArrayList containing Ingredient objects
     */
    private void getSystemIngredients()
    {
        systemIngredients = facade.getSystemIngredients();
    }


    /**
     * Returns an ArrayList of HashMaps of the user's inventory (contains their ingredients)
     */
    public ArrayList<HashMap<String, String>> getUserInventory()
    {
        // initialize the user's ingredients (inventory)
        getUserIngredients();

        // the user's inventory will be stored in an ArrayList
        ArrayList<HashMap<String, String>> inventory = new ArrayList<>();

        // for every user ingredient, add a key/value
        for (UserIngredient userIngredient : userIngredients)
        {
            // use a hashmap to store the info of the UserIngredient object
            HashMap<String, String> userIngMap = new HashMap<>();

            // put the UserIngredient ID in the map
            userIngMap.put("id", Integer.toString(userIngredient.getUserIngredientID()));

            // put the Ingredient's name in the map
            userIngMap.put("name", userIngredient.getUserIngredientName());

            // put the Ingredient's quantity in the map
            userIngMap.put("quantity", Double.toString(userIngredient.getQuantity()));

            // put the Ingredient's unit of measure in the map
            userIngMap.put("unit_of_measure", userIngredient.getUserIngredientUnitOfMeasure());

            // add the HashMap to the ArrayList
            inventory.add(userIngMap);
        }

        // return the ArrayList
        return inventory;
    }


    /**
     * Initializes userIngredients with an ArrayList containing UserIngredient objects
     */
    private void getUserIngredients()
    {
        userIngredients = facade.getUserIngredients(user.getId());
    }


    /**
     * Requests the facade to update the user's inventory
     */
    public void updateUserInventory(ArrayList<HashMap<String, String>> updatedIngredientList)
    {
        facade.updateUserInventory(user.getId(), updatedIngredientList);
    }


    /**
     * Sets the User Singleton to Null (A new User Singleton can be created after this call)
     */
    public void logUserOut()
    {
        user.deleteUserObject();
    }


} // end of ServiceDispatcher class