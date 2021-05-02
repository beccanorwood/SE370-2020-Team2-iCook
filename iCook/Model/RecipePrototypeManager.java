package iCook.Model;

import java.util.ArrayList;

/**
 * Manages all RecipeIF objects. Used in the Prototype design pattern.
 *
 * @Author: Team 2
 * @date: 5/2/2021
 */
public class RecipePrototypeManager {
    // instance variables
    private ArrayList<RecipeIF> prototypeRecipes;


    /**
     * Constructor
     */
    public RecipePrototypeManager() {
        prototypeRecipes = new ArrayList<>();
    }


    /**
     * Setter for the list of RecipeIF objects
     *
     * @param prototypeRecipes the list of RecipeIF objects
     */
    public void setPrototypeRecipes(ArrayList<RecipeIF> prototypeRecipes) {
        this.prototypeRecipes = prototypeRecipes;
    }


    /**
     * Getter for the list of RecipeIF objects
     *
     * @return the list of RecipeIF objects
     */
    public ArrayList<RecipeIF> getPrototypeRecipes() {
        return this.prototypeRecipes;
    }


    /**
     * Create a clone of a RecipeIF object based on the recipe's id
     */
    public RecipeIF createRecipePrototype(int id) {
        try {
            // find the corresponding recipe based on id
            for (RecipeIF recipe : prototypeRecipes) {
                // if there is a match, clone the original recipe
                if (recipe.getRecipeID() == id) {
                    return (RecipeIF) recipe.clone();
                }
            }
            // if there is no match return null
            return null;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    } // end of createRecipePrototype


} // end of RecipePrototypeManager
