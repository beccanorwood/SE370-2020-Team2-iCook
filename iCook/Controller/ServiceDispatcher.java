package iCook.Controller;
import iCook.Model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main controller class for iCook's MVC design pattern. Communicates between the View and Model packages.
 *
 * @author Team 2
 * @version 11/30/2020
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
     * Requests the facade to log the user in.
     * If the facade successfully logs the user in, initialize the user singleton.
     *
     * @param username the username of the user trying to login
     * @param password the password of the user trying to login
     * @return true if the login was successful, false otherwise
     */
    public boolean login(String username, String password)
    {
        if ( facade.login(username, password) ) {
            // initialize the user SINGLETON here
            // initialize the user's list of ingredient here (1st time)
            user = User.getUser();
            userIngredients = facade.getUserIngredients(user.getId());

//            // TESTING THE UPDATE DB
//            ArrayList<HashMap<String, String>> test = new ArrayList<>();
//            HashMap<String, String> map = new HashMap<>();
//            map.put("name", "milk");
//            map.put("quantity", "2");
//            test.add(map);
//            HashMap<String, String> map2 = new HashMap<>();
//            map2.put("name", "flour");
//            map2.put("quantity", "2");
//            test.add(map2);
//            HashMap<String, String> map3 = new HashMap<>();
//            map3.put("name", "vegetable oil");
//            map3.put("quantity", "2");
//            test.add(map3);
//            HashMap<String, String> map4 = new HashMap<>();
//            map4.put("name", "egg");
//            map4.put("quantity", "0");
//            test.add(map4);
//            updateUserInventory(test);

            return true;
        }
        else
            return false;
    }


    /**
     * Requests the facade to create a new user with the given credentials.
     * Initializes the user singleton with the newly created account and sets the
     * user's list of ingredients to null.
     *
     * @param username the username of the user trying to sign up
     * @param password the password of the user trying to sign up
     */
    public void signUp(String username, String password)
    {
        facade.signUp(username, password);
        user = User.getUser();
        userIngredients = null;
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
     * Determines if the user is logged in by looking at the user SINGLETON object
     *
     * @return true if the singleton object is not null, false otherwise
     */
    public boolean isLoggedIn()
    {
        return ( user == null ) ? false : true;
    }


    /**
     * Gets all of the system ingredients and stores them into an ArrayList of HashMaps
     *
     * @return an ArrayList of HashMaps containing all system ingredients (contains the name and unit of measure)
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
     *
     * @return an ArrayList of HashMaps containing all user ingredients (NOT UserIngredient objects)
     */
    public ArrayList<HashMap<String, String>> getUserInventory()
    {
        // initialize the user's ingredients
        // ** this will change so need call here **
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
     * Requests the facade to update the user's inventory with a given ArrayList
     *
     * @param updatedIngredientList the ArrayList of HashMaps that contains the user's inventory information (to be updated)
     */
    public void updateUserInventory(ArrayList<HashMap<String, String>> updatedIngredientList)
    {
        facade.updateUserInventory(user.getId(), updatedIngredientList);
    }


    /**
     * Logs the user out of their account. Deletes the User Singleton.
     */
    public void logUserOut()
    {
        user.deleteUserObject();
    }


} // end of ServiceDispatcher class