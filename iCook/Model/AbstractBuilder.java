package iCook.Model;

import java.util.ArrayList;


/**
 * Abstract class that defines the methods needed in extending builder classes.
 * Used in the Builder design pattern.
 *
 * @author Team 2
 * @version 5/2/2021
 */
public abstract class AbstractBuilder {
    // returns an instance of a new RecipeBuilder
    public static AbstractBuilder getInstance() {
        return new RecipeBuilder();
    }

    // to be implemented in extending classes
    public abstract void buildRecipeID(int id);
    public abstract void buildRecipeName(String name);
    public abstract void buildRecipeInstructions(String instructions);
    public abstract void buildRecipeIngredients(ArrayList<RecipeIngredient> ingredients);
    public abstract void buildRecipeStatus(boolean is_published);
    public abstract RecipeIF getRecipe();

} // end of AbstractBuilder
