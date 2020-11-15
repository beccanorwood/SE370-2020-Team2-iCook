package iCook.Model;

/**
 * Ingredient class stores ingredient ID, ingredient name, and ingredient unit of measure.
 * This class represents the system's ingredients that a user can choose from.
 *
 * @author Team 2
 * @version 11/14/2020
 */
public class Ingredient {

    // Private class members
    private int ingredientID;
    private String ingredientName;
    private String unitOfMeasure;

    /**
     * Constructor
     * @param ingredientID int
     * @param ingredientName String
     * @param unitOfMeasure String
     */
    public Ingredient(int ingredientID, String ingredientName, String unitOfMeasure) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Get ingredient ID
     * @return ingredientID
     */
    public int getIngredientID() {
        return ingredientID;
    }

    /**
     * Set the ID of ingredient
     * @param ingredientID int
     */
    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    /**
     * Get the name of ingredient
     * @return ingredientName
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Set the name of ingredient
     * @param ingredientName String
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    /**
     * Get ingredient unit of measure
     * @return unitOfMeasure
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Set ingredient unit of measure
     * @param unitOfMeasure String
     */
    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
