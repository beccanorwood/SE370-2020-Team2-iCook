package iCook.Model;

/**
 * Ingredient class stores ingredient ID, ingredient name, and ingredient unit of measure.
 * This class represents the system's ingredients that a user can choose from.
 *
 * @author Team 2
 * @version 12/5/2020
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
     * Get the name of ingredient
     * @return ingredientName
     */
    public String getIngredientName() {
        return ingredientName;
    }


    /**
     * Get ingredient unit of measure
     * @return unitOfMeasure
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }


} // end of Ingredient class
