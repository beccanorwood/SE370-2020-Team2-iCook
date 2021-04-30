package iCook.Model;

import java.util.ArrayList;

/**
 * RecipePrototypeManager
 *
 * @Author: Team 2
 * @date: 4/30/2021
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


    public void setPrototypeRecipes(ArrayList<RecipeIF> prototypeRecipes) {
        this.prototypeRecipes = prototypeRecipes;
    }


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
