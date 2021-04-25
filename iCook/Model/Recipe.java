package iCook.Model;

import iCook.View.Operations.DisplayObjects.IngredientDisplayObject;

import java.util.ArrayList;

/**
 * Recipe creates an object that holds relevant information including the name, id number, a list of
 * RecipeIngredient objects, as well as the number of servings a given recipe will produce
 *
 * @Author: Team 2
 * @date: 4/24/2021
 */
public class Recipe {
    // instance variables
    private int recipeID;           // id number associated with given recipe in database
    private String recipeName;      // name of recipe
    private String instructions;    // instructions for the recipe
    private ArrayList<RecipeIngredient> ingredients;


    /**
     * Constructor
     * @param recipeID int
     * @param recipeName String
     * @param instructions String
     */
    public Recipe(int recipeID, String recipeName, String instructions, ArrayList<RecipeIngredient> ingredients) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredients = ingredients;
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


    public ArrayList<IngredientDisplayObject> getIngredientDisplayObjects() {
        // list to be returned
        ArrayList<IngredientDisplayObject> ingredientDisplayObjects = new ArrayList<>();

        // convert the recipe's list RecipeIngredients to a list of IngredientDisplayObjects
        for (RecipeIngredient ri : ingredients) {
            ingredientDisplayObjects.add(new IngredientDisplayObject(ri.getIngredient().getIngredientID(),
                    ri.getIngredient().getIngredientName(), ri.getIngredient().getUnitOfMeasure(), ri.getQuantity()));
        }

        return ingredientDisplayObjects;
    }


} // end of Recipe class
