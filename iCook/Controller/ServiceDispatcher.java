package iCook.Controller;

import iCook.Model.*;
import iCook.View.Login.*;
import iCook.View.Operations.*;
import iCook.View.Operations.DisplayObjects.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

/**
 * The main controller class for iCook's MVC design pattern. Communicates between the View and Model packages.
 *
 * @author Team 2
 * @version 5/2/2021
 */
public class ServiceDispatcher {
    // user need to be static (not unique for each ServiceDispatcher object)
    private static User user = null;

    // instance variables
    private Facade facade;
    private ArrayList<Ingredient> systemIngredients;
    private ArrayList<UserIngredient> userIngredients;

    // all UI elements needed
    private static JFrame frame;


    /**
     * Constructor - initializes instance variables.
     * Calls getSystemIngredients to populate systemIngredients.
     * Calls getUserIngredients to populate userIngredients.
     */
    public ServiceDispatcher() {
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
            assert user != null;
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
        return user != null;
    }


    /**
     * Returns the boolean value indicating whether or not the current user
     * is an admin or not.
     *
     * @return true if the user is an admin, false otherwise
     */
    public boolean isUserAdmin()
    {
        return user.isAdmin();
    }


    /**
     * Gets all of the ingredients that the user doesn't already have and stores them into an ArrayList of IngredientDisplayObjects
     *
     * @return an ArrayList of IngredientDisplayObject representing all system ingredients the user doesn't have (no quantity here)
     */
    public ArrayList<IngredientDisplayObject> getAvailableIngredients()
    {
        // make sure we have the most up to date version of the user's ingredients
        getUserIngredients();

        // the available ingredients will be stored in an ArrayList
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

            // create a new IngredientDisplayObject
            IngredientDisplayObject availableIngredient = new IngredientDisplayObject(ingredientID, name, unitOfMeasure);

            // if the userIngredients list is empty, add all ingredients
            if (userIngredients.isEmpty())
                allIngredients.add(availableIngredient);

            // otherwise filter the available ingredients to the user
            else {
                for (int i = 0; i < userIngredients.size(); i++)
                {
                    // if the ingredient is found in the user's inventory, do not add it to the return list
                    if (availableIngredient.getName().equals(userIngredients.get(i).getUserIngredientName()))
                        break;

                    // else if the ingredient is not in the user's inventory && the ingredient isn't already in the returning list, add it
                    else if (i == userIngredients.size() - 1 && !allIngredients.contains(availableIngredient))
                        // add a new IngredientDisplayObject to the ArrayList
                        allIngredients.add(availableIngredient);
                }
            }
        }

        // return the ArrayList
        return allIngredients;
    }


    /**
     * Initializes systemIngredients with an ArrayList containing Ingredient objects
     */
    private void getSystemIngredients() {
        systemIngredients = facade.getSystemIngredients();
    }


    /**
     * Return an ArrayList of IngredientDisplayObjects representing the system's ingredients
     *
     * @return List of IngredientDisplayObjects representing the system's ingredients
     */
    public ArrayList<IngredientDisplayObject> getSystemIngredientDisplayObjects() {
        // list we are going to return
        ArrayList<IngredientDisplayObject> ingredientDisplayObjects = new ArrayList<>();

        // get the list of system ingredients from the system
        ArrayList<Ingredient> ingredients = facade.getSystemIngredients();

        // for every system ingredient, convert it into an IngredientDisplayObject and add it
        // to the array list
        for (Ingredient ing : ingredients) {
            ingredientDisplayObjects.add(new IngredientDisplayObject(ing.getIngredientID(),
                    ing.getIngredientName(), ing.getUnitOfMeasure(), 0));
        }

        return ingredientDisplayObjects;
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


        Collections.sort(inventory);

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
     * @param updatedIngredientList an ArrayList of IngredientDisplayObjects that contains the user's pending inventory information (to be updated)
     */
    public void updateUserInventory(ArrayList<IngredientDisplayObject> updatedIngredientList) {
        // store the needed ingredient information in a HashMap
        ArrayList<UserIngredient> userIngredients = new ArrayList<>();

        // Convert every display object into an ingredient object, then
        // create a new UserIngredient object and append it to userIngredients
        for (IngredientDisplayObject ingredient : updatedIngredientList) {
            UserIngredient ui = new UserIngredient(user, new Ingredient(ingredient.getIngredientID(),
                    ingredient.getName(), ingredient.getUnitOfMeasure()));  // new UserIngredient object

            ui.setQuantity(ingredient.getQuantity());   // set the quantity

            userIngredients.add(ui);    // add to list
        }

        // send to the facade to be processed
        facade.updateUserInventory(user.getId(), userIngredients);
    }


    /**
     * Requests the facade to return an ArrayList of recipes available to the user
     *
     * @return an ArrayList of RecipeDisplayObjects that represent recipes satisfiable to the user, based on their inventory
     */
    public ArrayList<RecipeDisplayObject> getSatisfiedRecipes()
    {
        // send the user's inventory to the facade to be processed
        ArrayList<Recipe> recipes = facade.getSatisfiedRecipes(userIngredients, user.getId());

        // make sure the user has recipes available to them
        if (recipes != null && !recipes.isEmpty())
        {
            // create new ArrayList of RecipeDisplayObjects to be sent to the View
            ArrayList<RecipeDisplayObject> display_recipes = new ArrayList<>();

            // for every Recipe object, create a RecipeDisplayObject and add it to
            // the ArrayList to be returned
            for (Recipe recipe : recipes) {
                display_recipes.add(new RecipeDisplayObject(recipe.getRecipeID(),
                        recipe.getRecipeName(), recipe.getInstructions(), getIngredientDisplayObjects(recipe), recipe.isPublished()));
            }


            //Sorts the recipe display objects alphabetically by recipe name
            Collections.sort(display_recipes);

            // return the list of available recipes
            return display_recipes;
        }

        // return null to indicate that the user cannot make any recipes with their current inventory
        return null;
    }


    /**
     * Logs the user out of their account. Deletes the User Singleton.
     */
    public void logUserOut() {
        user.deleteUserObject();
    }


    /**
     * Sends a Request to the Facade to return a Vector containing iCook's recipes.
     *
     * @return a Vector containing vectors (each inner vector contains a recipe's info).
     */
    public Vector<Vector> getRecipes() {
        return facade.getRecipes();
    }


    /**
     * Sends a Request to the Facade to return a Recipe object which is then converted
     * to a RecipeDisplayObject for the View to access.
     *
     * @return a RecipeDisplayObject corresponding to the passed in recipe id
     */
    public RecipeDisplayObject getRecipeDisplayObject(int id) {
        Recipe recipe = facade.getRecipe(id);

        return new RecipeDisplayObject(id, recipe.getRecipeName(), recipe.getInstructions(), getIngredientDisplayObjects(recipe), recipe.isPublished());
    }


    /**
     * Converts a Recipe object's ingredient list to an ArrayList of IngredientDisplayObjects.
     * Used in conversions between Recipe objects and RecipeDisplayObjects
     *
     * @return an ArrayList of IngredientDisplayObjects
     */
    private ArrayList<IngredientDisplayObject> getIngredientDisplayObjects(Recipe recipe) {
        // list to be returned
        ArrayList<IngredientDisplayObject> ingredientDisplayObjects = new ArrayList<>();

        // convert the recipe's list RecipeIngredients to a list of IngredientDisplayObjects
        for (RecipeIngredient ri : recipe.getIngredients()) {
            ingredientDisplayObjects.add(new IngredientDisplayObject(ri.getIngredient().getIngredientID(),
                    ri.getIngredient().getIngredientName(), ri.getIngredient().getUnitOfMeasure(), ri.getQuantity()));
        }

        return ingredientDisplayObjects;
    }


    /**
     * Converts a RecipeDisplayObject's ingredient list to an ArrayList of RecipeIngredient Objects.
     * Used in conversions between RecipeDisplayObjects and Recipe objects.
     *
     * @return an ArrayList of RecipeIngredient Objects
     */
    private ArrayList<RecipeIngredient> getIngredientList(RecipeDisplayObject recipeDO) {
        // list to be returned
        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();

        // convert the recipe's list of IngredientDisplayObjects to a list of RecipeIngredients
        for (IngredientDisplayObject ingDO : recipeDO.getIngredients()) {
            // ingredient to be passed into the new RecipeIngredient object
            Ingredient i = new Ingredient(ingDO.getIngredientID(), ingDO.getName(), ingDO.getUnitOfMeasure());

            // add a new RecipeIngredient to the ArrayList
            ingredients.add(new RecipeIngredient(0, i, ingDO.getQuantity()));
        }

        return ingredients;
    }


    /**
     * Sends request to the Facade to add a new recipe to the database.
     * Converts the accepted RecipeDisplayObject into a Recipe object for the
     * Facade to handle.
     *
     * @param recipeDO the RecipeDisplayObject to be added to the database
     */
    public void addNewRecipe(RecipeDisplayObject recipeDO) {
        Recipe recipe = new Recipe(recipeDO.getRecipeID(), recipeDO.getName(),
                recipeDO.getInstructions(), getIngredientList(recipeDO), recipeDO.isPublished());

        facade.addNewRecipe(recipe);
    }


    /**
     * Sends request to the Facade to update an existing recipe in the database.
     * Converts the accepted RecipeDisplayObject into a Recipe object for the
     * Facade to handle.
     *
     * @param recipeDO the RecipeDisplayObject to be updated in the database
     */
    public void updateRecipe(RecipeDisplayObject recipeDO) {
        Recipe recipe = new Recipe(recipeDO.getRecipeID(), recipeDO.getName(),
                recipeDO.getInstructions(), getIngredientList(recipeDO), recipeDO.isPublished());

        facade.updateRecipe(recipe);
    }


    /**
     * Clones an existing RecipeIF object for the currently logged in user and
     * requests the facade to insert it into iCook's database.
     * (USES THE PROTOTYPE DESIGN PATTERN)
     *
     * @param id the id of the recipe we want to clones
     * @param newRecipeName the name of this new cloned recipe
     * @param newRecipeInstructions the instructions of this new cloned recipe
     */
    public void cloneRecipe(int id, String newRecipeName, String newRecipeInstructions) {
        RecipePrototypeManager recipe_manager = new RecipePrototypeManager();
        recipe_manager.setPrototypeRecipes(facade.getAllSystemRecipes());    // call the facade to set the manager's list of recipes

        // get the clone of the original recipe and change the variables that need to be different
        RecipeIF cloned_recipe = recipe_manager.createRecipePrototype(id);
        cloned_recipe.setName(newRecipeName);
        cloned_recipe.setInstructions(newRecipeInstructions);

        // request the facade to insert this newly cloned recipe
        facade.insertClonedRecipe(cloned_recipe, user.getId());
    }


    /**
     * Initializes the program's frame and begins iCook. This is the only entry point for iCook.
     * (Starts the application)
     */
    public void startProgram() {
        // set the look and feel to be uniform on all operating systems
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch(Exception e){
            e.printStackTrace();
        }

        // The java frame is initialized here
        frame = new JFrame();
        frame.setTitle("iCook");
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // first panel to be displayed is the welcomeUI
        gotoWelcome(); // CHANGE BACK WHEN DONE WITH TESTING!!!
    }


    /**
     * Ends the program from the WelcomeUI
     * (Used in the WelcomeUI)
     *
     */
    public void quitProgram() {
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }


    /**
     * Sets the frame's contents to the contents of the WelcomeUI
     */
    public void gotoWelcome() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new WelcomeUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the LoginUI
     */
    public void gotoLogin() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new LoginUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the SignUpUI
     */
    public void gotoSignup() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new SignUpUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the HomeUI
     */
    public void gotoHome() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new HomeUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the ManageRecipesUI
     */
    public void gotoManageRecipesUI() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ManageRecipesUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the ModifyRecipeUI
     */
    public void gotoModifyRecipeUI() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ModifyRecipeUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the ModifyRecipeUI
     * (USED FOR EXISTING RECIPES)
     */
    public void gotoModifyRecipeUI(int recipeID) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ModifyRecipeUI(recipeID));
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the ViewRecipesUI
     */
    public void gotoViewRecipes() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ViewRecipesUI());
        frame.setVisible(true);
    }


    /**
     * Sets the frame's contents to the contents of the InventoryUI
     */
    public void gotoInventory() {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new InventoryUI());
        frame.setVisible(true);
    }


} // end of ServiceDispatcher class