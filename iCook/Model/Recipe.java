package iCook.Model;

/**
 * Recipe creates an object that holds relevant information including the name, id number, a list of
 * RecipeIngredient objects, as well as the number of servings a given recipe will produce
 *
 * @Author: Team 2
 * @date: 12/2/2020
 */
public class Recipe {

    // instance variables
    private int recipeID;           // id number associated with given recipe in database
    private String recipeName;      // name of recipe
    private String instructions;    // instructions for the recipe


    /**
     * Constructor
     * @param recipeID int
     * @param recipeName String
     * @param instructions String
     */
    public Recipe(int recipeID, String recipeName, String instructions) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.instructions = instructions;
    }


    public int getRecipeID() {
        return recipeID;
    }


    public String getRecipeName() {
        return recipeName;
    }


    public String getinstructions() {
        return instructions;
    }


} // end of Recipe class
