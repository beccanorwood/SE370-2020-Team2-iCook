package iCook.Model;
import java.util.List;

/**
 * Recipe creates an object that holds relevant information including the name, id number, a list of
 * RecipeIngredient objects, as well as the number of servings a given recipe will produce
 *
 * @Author: Team 2
 * @date: 11/14/2020
 */
public class Recipe {

    private int recipeID;                           // id number associated with given recipe in database
    private String recipeName;                      // name of recipe
    private List<RecipeIngredient> rcpIngredients;  // a list of RecipeIngredient objects required for recipe
    private int numServings;                        // number of servings recipe will produce

    public Recipe(int recipeID, String recipeName, List<RecipeIngredient> rcpIngredients, int numServings) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.rcpIngredients = rcpIngredients;
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

    public List<RecipeIngredient> getRcpIngredients() {
        return rcpIngredients;
    }

    public void setRcpIngredients(List<RecipeIngredient> rcpIngredients) {
        this.rcpIngredients = rcpIngredients;
    }

    public int getNumServings() {
        return numServings;
    }

    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }
}
