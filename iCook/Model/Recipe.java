package iCook.Model;

import java.util.ArrayList;

/**
 * Recipe creates an object that holds relevant information including the name, id number, a list of
 * RecipeIngredient objects, as well as the number of servings a given recipe will produce
 *
 * @author Team 2
 * @version 5/2/2021
 */
public class Recipe implements RecipeIF {
    // instance variables
    private int recipeID;           // id number associated with given recipe in database
    private String recipeName;      // name of recipe
    private String instructions;    // instructions for the recipe
    private ArrayList<RecipeIngredient> ingredients;
    boolean isPublished;            // publish status of this recipe


    /**
     * Constructor - 5 parameters
     * @param recipeID int
     * @param recipeName String
     * @param instructions String
     * @param ingredients ArrayList<RecipeIngredient>
     * @param isPublished boolean
     */
    public Recipe(int recipeID, String recipeName, String instructions, ArrayList<RecipeIngredient> ingredients, boolean isPublished) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.isPublished = isPublished;

        // set this recipe as the recipe ingredient's recipe object
        for (RecipeIngredient ing : ingredients)
            ing.setRecipe(this);
    }


    /**
     * Constructor that takes in a recipe builder.
     * Used for the Builder design pattern.
     *
     * @param builder the builder that will initialize this recipe object
     */
    public Recipe(RecipeBuilder builder) {
        // use the builder to get all the parts needed for the recipe
        this.recipeID = builder.getRecipeID();
        this.recipeName = builder.getRecipeName();
        this.instructions = builder.getRecipeInstructions();
        this.ingredients = builder.getRecipeIngredients();
        this.isPublished = builder.getRecipeStatus();

        // set this recipe as the recipe ingredient's recipe object
        for (RecipeIngredient ing : ingredients)
            ing.setRecipe(this);
    }


    public int getRecipeID() {
        return recipeID;
    }


    public String getRecipeName() {
        return recipeName;
    }


    public String getInstructions() {
        return instructions;
    }


    public ArrayList<RecipeIngredient> getIngredients() {
        return ingredients;
    }


    public boolean isPublished() {
        return isPublished;
    }


    /**
     * Returns a clone of this object.
     * Used for the Prototype design pattern.
     *
     * @return a RecipeIF clone of this recipe
     */
    public RecipeIF clone() {
        try {
            return (RecipeIF) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }


    public void setName(String name) {
        this.recipeName = name;
    }


    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


} // end of Recipe class
