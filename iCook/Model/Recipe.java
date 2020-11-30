package iCook.Model;

/**
 * Recipe creates an object that holds relevant information including the name, id number, a list of
 * RecipeIngredient objects, as well as the number of servings a given recipe will produce
 *
 * @Author: Team 2
 * @date: 11/29/2020
 */
public class Recipe {

    // instance variables
    private int recipeID;       // id number associated with given recipe in database
    private String recipeName;  // name of recipe
    private int numServings;    // number of servings recipe will produce


    /**
     * Constructor
     * @param recipeID int
     * @param recipeName String
     * @param numServings int
     */
    public Recipe(int recipeID, String recipeName, int numServings) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.numServings = numServings;
    }


    public int getRecipeID() {
        return recipeID;
    }


    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }


    public String getRecipeName() {
        return recipeName;
    }


    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }


    public int getNumServings() {
        return numServings;
    }


    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }


} // end of Recipe class
