package iCook.Model;

import iCook.Model.DatabaseAccess.*;
import iCook.View.Operations.DisplayObjects.RecipeDisplayObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Central class for the Model package. Has access to all DAO classes and has
 * access to the AbstractBuilder and RecipePrototypeManager.
 *
 * @author Team 2
 * @version 5/7/2021
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
    public boolean login(String username, String password) {
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
    public void signUp(String username, String password) throws UsernameTakenException {
        // make sure the username isn't taken
        // NEED TO THROW AN EXCEPTION HERE
        if ( userDAO.usernameIsTaken(username) ) {
            System.out.println("UsernameTakenException thrown");
            throw new UsernameTakenException("\"" + username + "\"" + " is already in use!");
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
    public ArrayList<Ingredient> getSystemIngredients() {
        return ingredientDAO.getAllIngredients();
    }


    /**
     * Calls the UserDAO to return the user's ingredients
     *
     * @param userID the user's id whose ingredients we want to get
     * @return an ArrayList of UserIngredient objects (user's inventory)
     */
    public ArrayList<UserIngredient> getUserIngredients(int userID) {
        return userDAO.getUserIngredients(userID);
    }


    /**
     * Requests the UserDAO to update the user's inventory
     *
     * @param userID the user's id whose ingredients we want to update
     * @param userIngredients an ArrayList of UserIngredient objects to be updated
     */
    public void updateUserInventory(int userID, ArrayList<UserIngredient> userIngredients) {
        userDAO.updateUserIngredientTable(userID, userIngredients);
    }


    /**
     * Sends a Request to the RecipeDAO to get a list of recipes available to the user based on their inventory
     *
     * @param userIngredients the ArrayList containing UserIngredient objects (the user's inventory)
     * @param owner_id the user's id so we can include their modified recipes
     * @return an ArrayList of Recipe objects satisfiable to the user, based on their inventory
     */
    public ArrayList<Recipe> getSatisfiedRecipes(ArrayList<UserIngredient> userIngredients, int owner_id) {
        return recipeDAO.getSatisfiedRecipes(userIngredients, owner_id);
    }


    /**
     * Sends a Request to the RecipeDAO to get a list of iCook's recipes.
     *
     * @return a Vector containing vectors (each inner vector contains a recipe's info).
     */
    public Vector<Vector> getRecipes() {
        return recipeDAO.getRecipes();
    }


    /**
     * Sends a Request to the RecipeDAO to get the desired Recipe object
     *
     * @param id the id of the recipe we want to retrieve
     * @return a Recipe corresponding to the passed in id
     */
    public Recipe getRecipe(int id) {
        return recipeDAO.getRecipe(id);
    }


    /**
     * Builds a new RecipeIF object which is then sent
     * to the RecipeDAO to be added as a new recipe in the database.
     * (Uses the Builder design pattern)
     *
     * @param recipeDO the RecipeDisplayObject containing info of the recipe to be built
     * @param ingredients the ArrayList of RecipeIngredients corresponding to the passed in recipeDO
     */
    public void buildNewRecipe(RecipeDisplayObject recipeDO, ArrayList<RecipeIngredient> ingredients) {
        AbstractBuilder builder = AbstractBuilder.getInstance();
        builder.buildRecipeID(recipeDO.getRecipeID());
        builder.buildRecipeName(recipeDO.getName());
        builder.buildRecipeInstructions(recipeDO.getInstructions());
        builder.buildRecipeIngredients(ingredients);
        builder.buildRecipeStatus(recipeDO.isPublished());

        RecipeIF recipe = builder.getRecipe();
        addNewRecipe(recipe);
    }


    /**
     * Sends a Request to the RecipeDAO to insert a new Recipe
     *
     * @param recipe to be inserted into the database
     */
    public void addNewRecipe(RecipeIF recipe) {
        recipeDAO.addNewRecipe(recipe);
    }


    /**
     * Builds a new RecipeIF object from an existing recipe which is then sent
     * to the RecipeDAO to update the corresponding recipe in the database.
     * (Uses the Builder design pattern)
     *
     * @param recipeDO the RecipeDisplayObject containing the updated info of the recipe to be built
     * @param ingredients the ArrayList of RecipeIngredients corresponding to the passed in recipeDO
     */
    public void buildUpdateRecipe(RecipeDisplayObject recipeDO, ArrayList<RecipeIngredient> ingredients) {
        AbstractBuilder builder = AbstractBuilder.getInstance();
        builder.buildRecipeID(recipeDO.getRecipeID());
        builder.buildRecipeName(recipeDO.getName());
        builder.buildRecipeInstructions(recipeDO.getInstructions());
        builder.buildRecipeIngredients(ingredients);
        builder.buildRecipeStatus(recipeDO.isPublished());

        RecipeIF recipe = builder.getRecipe();
        updateRecipe(recipe);
    }


    /**
     * Sends a Request to the RecipeDAO to update the passed in recipe
     *
     * @param recipe to be updated in the database
     */
    public void updateRecipe(RecipeIF recipe) {
        recipeDAO.updateRecipe(recipe);
    }


    /**
     * Sends a Request to the RecipeDAO to get an ArrayList of all recipes
     *
     * @return ArrayList containing RecipeIF objects
     */
    public ArrayList<RecipeIF> getAllSystemRecipes() {
        return recipeDAO.getAllSystemRecipes();
    }


    /**
     * Clones an existing RecipeIF object for the currently logged in user and
     * sends a request to the RecipeDAO to insert it into iCook's database.
     * (USES THE PROTOTYPE DESIGN PATTERN)
     *
     * @param id the id of the recipe we want to clones
     * @param newRecipeName the name of this new cloned recipe
     * @param newRecipeInstructions the instructions of this new cloned recipe
     * @param userID the id of the user this cloned recipe belongs to
     */
    public void cloneRecipe(int id, String newRecipeName, String newRecipeInstructions, int userID) {
        RecipePrototypeManager recipe_manager = new RecipePrototypeManager(); // initialize a prototype manager
        recipe_manager.setPrototypeRecipes(getAllSystemRecipes()); // set the recipe manager's list of recipes

        // get a clone of the original recipe and change the variables that need to be different
        RecipeIF cloned_recipe = recipe_manager.createRecipePrototype(id);
        cloned_recipe.setName(newRecipeName);
        cloned_recipe.setInstructions(newRecipeInstructions);

        // request the recipeDAO to insert this newly cloned recipe
        insertClonedRecipe(cloned_recipe, userID);
    }


    /**
     * Sends a Request to the RecipeDAO to insert a cloned recipe
     *
     * @param cloned_recipe the cloned recipe to be inserted into the database
     * @param owner_id owner of this cloned recipe
     */
    public void insertClonedRecipe(RecipeIF cloned_recipe, int owner_id) {
        recipeDAO.insertClonedRecipe(cloned_recipe, owner_id);
    }


    /**
     * Sends a Request to the RecipeDAO to retrieve the recipe's image URL
     *
     * @param recipe_ID the id of the recipe we are interested in
     * @return a string containing the recipe's image URL
     */
    public String getRecipeImageURL(int recipe_ID) {
        return recipeDAO.getRecipeImageURL(recipe_ID);
    }


} // end of Facade class