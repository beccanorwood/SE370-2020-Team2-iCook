package iCook.Model;

/**
 * RecipeIngredient creates ingredient objects that hold information about type, and quantity of ingredients required
 * for a given recipe
 *
 * @Author: Team 2
 * @Date: 11/14/2020
 */
public class RecipeIngredient {

        private int ingredientID;   // ID associated with given ingredient within the database
        private int recipeID;       // ID associated with given recipe within the database
        private int rcpIngredientID;// ID associated with given recipeIngredient within the database
        private float quantity;     // number of ingredient type necessary

    /**
     * Constructor
     * @param ingredientID
     * @param recipeID
     * @param rcpIngredientID
     * @param quantity
     */
    public RecipeIngredient(int ingredientID,  int recipeID, int rcpIngredientID, float quantity) {
        this.ingredientID = ingredientID;
        this.recipeID = recipeID;
        this.rcpIngredientID = rcpIngredientID;
        this.quantity = quantity;
    }

    public int getRecipeIngredientID() {
        return ingredientID;
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

    public int getRcpIngredientID() {
        return rcpIngredientID;
    }

    public void setRcpIngredientID(int rcpIngredientID) {
        this.rcpIngredientID = rcpIngredientID;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
