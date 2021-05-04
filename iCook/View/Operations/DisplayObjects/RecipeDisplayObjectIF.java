package iCook.View.Operations.DisplayObjects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Interface for RecipeDisplayObjects.
 *
 * @Author: Team 2
 * @date: 5/3/2021
 */
public interface RecipeDisplayObjectIF extends Comparable<RecipeDisplayObjectIF> {
    int compareTo(RecipeDisplayObjectIF o);
    BufferedImage display();

    int getRecipeID();
    String getName();
    String getInstructions();
    ArrayList<IngredientDisplayObject> getIngredients();
    boolean isPublished();
}
