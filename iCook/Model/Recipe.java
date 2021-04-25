package iCook.Model;

import java.util.ArrayList;

/**
 * Recipe creates an object that holds relevant information including the name, id number, a list of
 * RecipeIngredient objects, as well as the number of servings a given recipe will produce
 *
 * @Author: Team 2
 * @date: 4/25/2021
 */
public class Recipe {
    // instance variables
    private int recipeID;           // id number associated with given recipe in database
    private String recipeName;      // name of recipe
    private String instructions;    // instructions for the recipe
    private ArrayList<RecipeIngredient> ingredients;
    boolean isPublished;            // publish status of this recipe


    /**
     * Constructor - 5 parameters
     * @param recipeID int
     * @param recipeName String
     * @param instructions String
     * @param ingredients ArrayList<RecipeIngredient>
     * @param isPublished boolean
     */
    public Recipe(int recipeID, String recipeName, String instructions, ArrayList<RecipeIngredient> ingredients, boolean isPublished) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.isPublished = isPublished;

        // set this recipe as the recipe ingredient's recipe object
        for (RecipeIngredient ing : ingredients)
            ing.setRecipe(this);
    }


    public int getRecipeID() {
        return recipeID;
    }


    public String getRecipeName() {
        return recipeName;
    }


    public String getInstructions() {
        return instructions;
    }


    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }


    public boolean isPublished() {
        return isPublished;
    }


} // end of Recipe class
