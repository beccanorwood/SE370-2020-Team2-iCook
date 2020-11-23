package iCook.Model;

/**
 * RecipeIngredient represents an ingredient needed by a recipe and links a recipe and ingredient together via the quantity
 * required of the ingredient for said recipe.
 *
 * @Author: Team 2
 * @Date: 11/14/2020
 */
public class RecipeIngredient {

        private int recipeIngredientID; // ID associated with given recipeIngredient within the database
        private int ingredientID;       // ID associated with given ingredient within the database
        private int recipeID;           // ID associated with given recipe within the database
        private double quantity;        // number of ingredient type necessary

    /**
     * Constructor
     * @param ingredientID
     * @param recipeID
     * @param recipeIngredientID
     * @param quantity
     */
    public RecipeIngredient(int ingredientID,  int recipeID, int recipeIngredientID, double quantity) {
        this.ingredientID = ingredientID;
        this.recipeID = recipeID;
        this.recipeIngredientID = recipeIngredientID;
        this.quantity = quantity;
    }

    public int getRecipeIngredientID() {
        return recipeIngredientID;
    }

    public void setRecipeIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
