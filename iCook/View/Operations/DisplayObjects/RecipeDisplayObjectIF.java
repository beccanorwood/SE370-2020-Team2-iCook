package iCook.View.Operations.DisplayObjects;

import javax.swing.*;
import java.util.ArrayList;


/**
 * Interface for RecipeDisplayObjects.
 *
 * @Author: Team 2
 * @date: 5/3/2021
 */
public interface RecipeDisplayObjectIF extends Comparable<RecipeDisplayObjectIF> {
    int compareTo(RecipeDisplayObjectIF o);
    void display(JLabel label);

    int getRecipeID();
    String getName();
    String getInstructions();
    ArrayList<IngredientDisplayObject> getIngredients();
    boolean isPublished();
}
