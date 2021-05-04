package iCook.View.Operations.DisplayObjects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Abstract class that defines the methods needed in extending decorated classes.
 * Used in the Decorator design pattern.
 *
 * @Author: Team 2
 * @date: 5/3/2021
 */
public abstract class Recipe_With_Decorator implements RecipeDisplayObjectIF {
    // instance variables
    private RecipeDisplayObjectIF decoratee;

    /**
     * Constructor
     */
    public Recipe_With_Decorator(RecipeDisplayObjectIF decoratee) {
        this.decoratee = decoratee;
    }

    /**
     * Comparable Interface Method that will compare
     * the names of RDO and sorts them accordingly
     */
    @Override
    public int compareTo(RecipeDisplayObjectIF o) {
        return this.getName().charAt(0) - o.getName().charAt(0);
    }


    /**
     * Implemented from the RecipeDisplayObjectIF, but left to be
     * defined by classes that implement this abstract class.
     */
    @Override
    public abstract BufferedImage display();


    @Override
    public int getRecipeID() {
        return decoratee.getRecipeID();
    }

    @Override
    public String getName() {
        return decoratee.getName();
    }

    @Override
    public String getInstructions() {
        return decoratee.getInstructions();
    }

    @Override
    public ArrayList<IngredientDisplayObject> getIngredients() {
        return decoratee.getIngredients();
    }

    @Override
    public boolean isPublished() {
        return decoratee.isPublished();
    }


}
