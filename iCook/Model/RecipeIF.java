package iCook.Model;

import java.util.ArrayList;

/**
 * RecipeIF
 *
 * @Author: Team 2
 * @date: 4/30/2021
 */
public interface RecipeIF extends Cloneable {
    int getRecipeID();
    String getRecipeName();
    String getInstructions();
    void setInstructions(String instructions);
    ArrayList<RecipeIngredient> getIngredients();
    boolean isPublished();
    RecipeIF clone() throws CloneNotSupportedException;  // this is for the Prototype design pattern
}
