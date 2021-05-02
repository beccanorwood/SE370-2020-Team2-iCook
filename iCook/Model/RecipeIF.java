package iCook.Model;

import java.util.ArrayList;

/**
 * RecipeIF
 *
 * @Author: Team 2
 * @date: 5/2/2021
 */
public interface RecipeIF extends Cloneable {
    int getRecipeID();
    String getRecipeName();
    String getInstructions();
    ArrayList<RecipeIngredient> getIngredients();
    boolean isPublished();
    RecipeIF clone() throws CloneNotSupportedException;  // this is for the Prototype design pattern
    void setName(String name);
    void setInstructions(String instructions);
}
