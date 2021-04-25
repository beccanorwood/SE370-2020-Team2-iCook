package iCook.View.Operations.DisplayObjects;

import java.util.ArrayList;

/**
 * Display object for a recipe used in the RecipeUI
 *
 * @Author: Team 2
 * @Date: 4/24/2021
 */
public class RecipeDisplayObject {

    // instance variables
    private int recipeID;
    private String name;
    private String instructions;
    private ArrayList<IngredientDisplayObject> ingredients;


    /**
     * Constructor - 3 parameters
     *
     * @param recipeID the id of the recipe
     * @param name the name of the recipe
     * @param instructions the instructions of the recipe
     */
    public RecipeDisplayObject(int recipeID, String name, String instructions, ArrayList<IngredientDisplayObject> ingredients)
    {
        this.recipeID = recipeID;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
    }


    /**
     * Getter for the id of the recipe
     *
     * @return the id
     */
    public int getRecipeID()
    {
        return recipeID;
    }


    /**
     * Getter for the name of the recipe
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Getter for the instructions of the recipe
     *
     * @return the instructions
     */
    public String getInstructions()
    {
        return instructions;
    }


    /**
     * Getter for the ingredients of the recipe
     *
     * @return an ArrayList containing the corresponding ingredients for this recipe
     */
    public ArrayList<IngredientDisplayObject> getIngredients()
    {
        return ingredients;
    }


} // end of RecipeDisplayObject class

