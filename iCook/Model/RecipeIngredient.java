package iCook.Model;

/**
 * RecipeIngredient represents an ingredient needed by a recipe and links a recipe and ingredient together via the quantity
 * required of the ingredient for said recipe.
 *
 * @Author: Team 2
 * @Date: 4/25/2021
 */
public class RecipeIngredient {
    // instance variables
    private int recipeIngredientID; // ID associated with given recipeIngredient within the database
    private Ingredient ingredient;  // Ingredient associated with the recipeIngredient
    private Recipe recipe;          // Recipe associated with the recipeIngredient
    private int quantity;           // required quantity of this ingredient for the recipe


    /**
     * Constructor - 3 parameters
     *
     * @param recipeIngredientID recipe ingredient id
     * @param ingredient ingredient object
     * @param quantity quantity of this ingredient
     */
    public RecipeIngredient(int recipeIngredientID, Ingredient ingredient, int quantity) {
        this.recipeIngredientID = recipeIngredientID;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }


    public int getRecipeIngredientID() {
        return recipeIngredientID;
    }


    public Ingredient getIngredient() {
        return ingredient;
    }


    public Recipe getRecipe() {
        return recipe;
    }


    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }


    public int getQuantity() {
        return quantity;
    }


} // end of RecipeIngredient class
