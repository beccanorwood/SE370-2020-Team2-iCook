package iCook.Controller;

import iCook.Model.*;
import iCook.View.DisplayObjects.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main controller class for iCook's MVC design pattern. Communicates between the View and Model packages.
 *
 * @author Team 2
 * @version 12/1/2020
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
     * Calls getSystemIngredients to populate systemIngredients.
     * Calls getUserIngredients to populate userIngredients.
     */
    public ServiceDispatcher()
    {
        facade = new Facade();
        systemIngredients = new ArrayList<>();
        userIngredients = new ArrayList<>();
        getSystemIngredients();
        getUserIngredients();
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
     * @return the username if sign up was successful, error message if the username is taken
     */
    public String signUp(String username, String password)
    {
        try
        {
            facade.signUp(username, password);
            user = User.getUser();
            userIngredients = null;
            return username;
        }
        catch (UsernameTakenException error)
        {
            return error.toString();
        }
    }


    /**
     * Gets the username of the user SINGLETON
     *
     * @return the username of the logged in user
     */
    public String getUserName()
    {
        return user.getUserName();
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
     * Gets all of the system ingredients and stores them into an ArrayList of IngredientDisplayObjects
     *
     * @return an ArrayList of IngredientDisplayObject representing all system ingredients (no quantity here)
     */
    public ArrayList<IngredientDisplayObject> getAllSystemIngredients()
    {
        // the ingredients will be stored in an ArrayList
        ArrayList<IngredientDisplayObject> allIngredients = new ArrayList<>();

        // for every ingredient in the list of system ingredients
        for(Ingredient ingredient : systemIngredients)
        {
            // store the Ingredient's id
            int ingredientID = ingredient.getIngredientID();

            // store the Ingredient's name
            String name = ingredient.getIngredientName();

            // store the Ingredient's unit of measure
            String unitOfMeasure = ingredient.getUnitOfMeasure();

            // add a new IngredientDisplayObject to the ArrayList
            allIngredients.add(new IngredientDisplayObject(ingredientID, name, unitOfMeasure));
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
     * Returns an ArrayList of IngredientDisplayObjects representing the user's inventory
     *
     * @return an ArrayList of IngredientDisplayObjects representing all user ingredients
     */
    public ArrayList<IngredientDisplayObject> getUserInventory()
    {
        // initialize the user's ingredients
        // ** this will change so need call here **
        getUserIngredients();

        // the user's inventory will be stored in an ArrayList
        ArrayList<IngredientDisplayObject> inventory = new ArrayList<>();

        // for every user ingredient in the list of user ingredients
        for (UserIngredient userIngredient : userIngredients)
        {
            // store the Ingredient's id
            int ingredientID = userIngredient.getIngredientID();

            // store the Ingredient's name
            String name = userIngredient.getUserIngredientName();

            // store the Ingredient's unit of measure
            String unitOfMeasure = userIngredient.getUserIngredientUnitOfMeasure();

            // store the Ingredient's quantity
            int quantity = userIngredient.getQuantity();

            // add a new IngredientDisplayObject to the ArrayList
            inventory.add(new IngredientDisplayObject(ingredientID, name, unitOfMeasure, quantity));
        }

        // return the ArrayList
        return inventory;
    }


    /**
     * Initializes userIngredients with an ArrayList containing UserIngredient objects
     */
    private void getUserIngredients()
    {
        // only call the facade if the user singleton has been initialized
        if (user != null)
            userIngredients = facade.getUserIngredients(user.getId());
    }


    /**
     * Requests the facade to update the user's inventory with a given ArrayList
     *
     * @param updatedIngredientList an ArrayList of IngredientDisplayObject that contains the user's pending inventory information (to be updated)
     */
    public void updateUserInventory(ArrayList<IngredientDisplayObject> updatedIngredientList)
    {
        // store the needed ingredient information in a HashMap
        HashMap<Integer, Integer> updatedInventory = new HashMap<>();

        // for every display object received from the Model, put the id and quantity in the HashMap
        for (IngredientDisplayObject ingredient : updatedIngredientList)
        {
            // key = ingredientID / value = quantity
            updatedInventory.put(ingredient.getIngredientID(), ingredient.getQuantity());
        }

        // send the HashMap to the facade to be processed
        facade.updateUserInventory(user.getId(), updatedInventory);
    }


    /**
     * Requests the facade to return an ArrayList of recipes available to the user
     *
     * @return an ArrayList of RecipeDisplayObjects that represent recipes satisfiable to the user, based on their inventory
     */
    public ArrayList<RecipeDisplayObject> getSatisfiedRecipes()
    {
        // send the user's inventory to the facade to be processed
        ArrayList<Recipe> recipes = facade.getSatisfiedRecipes(userIngredients);

        // make sure the user has recipes available to them
        if (recipes != null && !recipes.isEmpty())
        {
            // create new ArrayList of RecipeDisplayObjects to be sent to the View
            ArrayList<RecipeDisplayObject> display_recipes = new ArrayList<>();

            // for every Recipe object, create a RecipeDisplayObject and add it to
            // the ArrayList to be returned
            for (Recipe recipe : recipes)
                display_recipes.add(new RecipeDisplayObject(recipe.getRecipeID(), recipe.getRecipeName(), recipe.getinstructions()));

            // return the list of available recipes
            return display_recipes;
        }

        // return null to indicate that the user cannot make any recipes with their current inventory
        return null;
    }


    /**
     * Logs the user out of their account. Deletes the User Singleton.
     */
    public void logUserOut()
    {
        user.deleteUserObject();
    }


} // end of ServiceDispatcher class