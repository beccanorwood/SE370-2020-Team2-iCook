package iCook.Model;

/**
 * UserIngredient class stores user ingredient ID, user ID, ingredient ID, and quantity of ingredient.
 * This class is responsible for storing an ingredient for a user as well as the quantity they have
 * of the ingredient.
 *
 * @author Team 2
 * @version 12/1/2020
 */
public class UserIngredient {

    // Private class members
    private int userIngredientID;
    private int userID;
    private int ingredientID;
    private int quantity;
    private String ingredientName;
    private String unit_of_measure;

    /**
     * Constructor
     * @param userIngredientID int
     * @param userID int
     * @param ingredientID int
     * @param quantity int
     * @param ingredientName String
     * @param unit_of_measure String
     */
    public UserIngredient(int userIngredientID, int userID, int ingredientID, int quantity, String ingredientName, String unit_of_measure) {
        this.userIngredientID = userIngredientID;
        this.userID = userID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
        this.ingredientName = ingredientName;
        this.unit_of_measure = unit_of_measure;
    }


    /**
     * Get user ID
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Set user ID
     * @param userID int
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }


    /**
     * Get user ingredient ID
     * @return userIngredientID
     */
    public int getUserIngredientID() {
        return userIngredientID;
    }


    /**
     * Set user ingredient ID
     * @param userIngredientID int
     */
    public void setUserIngredientID(int userIngredientID) {
        this.userIngredientID = userIngredientID;
    }


    /**
     * Get ingredient ID
     * @return ingredientID
     */
    public int getIngredientID() {
        return ingredientID;
    }


    /**
     * Set ingredient ID
     * @param ingredientID int
     */
    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }


    /**
     * Get quantity of ingredient
     * @return int
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * Set quantity of ingredient
     * @param quantity int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    /**
     * Getter for the ingredient's name
     */
    public String getUserIngredientName() {
        return ingredientName;
    }


    /**
     * Getter for the ingredient's unit of measure
     */
    public String getUserIngredientUnitOfMeasure() {
        return unit_of_measure;
    }


} // end of UserIngredient class
