package iCook.Model;

import java.util.ArrayList;


/**
 * Builder that constructs RecipeIF objects.
 * Used in the Builder design pattern.
 *
 * @author Team 2
 * @version 5/2/2021
 */
public class RecipeBuilder extends AbstractBuilder {
    // instance variables
    private int recipe_ID;
    private String recipe_name;
    private String recipe_instructions;
    private ArrayList<RecipeIngredient> recipe_ingredients;
    boolean recipe_status;


    /**
     * Constructor
     */
    public RecipeBuilder() {
        recipe_ID = 0;
        recipe_name = "";
        recipe_instructions = "";
        recipe_ingredients = new ArrayList<>();
        recipe_status = false;
    }


    @Override
    public void buildRecipeID(int id) {
        this.recipe_ID = id;
    }


    @Override
    public void buildRecipeName(String name) {
        this.recipe_name = name;
    }


    @Override
    public void buildRecipeInstructions(String instructions) {
        this.recipe_instructions = instructions;
    }


    @Override
    public void buildRecipeIngredients(ArrayList<RecipeIngredient> ingredients) {
        this.recipe_ingredients = ingredients;
    }


    @Override
    public void buildRecipeStatus(boolean is_published) {
        this.recipe_status = is_published;
    }


    @Override
    public RecipeIF getRecipe() {
        return new Recipe(this);
    }


    public int getRecipeID() {
        return recipe_ID;
    }


    public String getRecipeName() {
        return recipe_name;
    }


    public String getRecipeInstructions() {
        return recipe_instructions;
    }


    public ArrayList<RecipeIngredient> getRecipeIngredients() {
        return recipe_ingredients;
    }


    public boolean getRecipeStatus() {
        return recipe_status;
    }


} // end of RecipeBuilder
