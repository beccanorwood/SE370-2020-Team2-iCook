package iCook.View.DisplayObjects;

/**
 * Display object for a recipe used in the RecipeUI
 *
 * @Author: Team 2
 * @Date: 12/1/2020
 */
public class RecipeDisplayObject {

    // instance variables
    private int recipeID;
    private String name;
    private String instructions;


    /**
     * Constructor - 3 parameters
     *
     * @param recipeID the id of the recipe
     * @param name the name of the recipe
     * @param instructions the instructions of the recipe
     */
    public RecipeDisplayObject(int recipeID, String name, String instructions)
    {
        this.recipeID = recipeID;
        this.name = name;
        this.instructions = instructions;
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


} // end of RecipeDisplayObject class

