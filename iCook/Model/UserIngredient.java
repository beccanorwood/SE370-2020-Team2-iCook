package iCook.Model;

/**
 * UserIngredient class stores user ingredient ID, user ID, ingredient ID, and quantity of ingredient.
 * This class is responsible for storing an ingredient for a user as well as the quantity they have
 * of the ingredient.
 *
 * @author Team 2
 * @version 4/29/2021
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
     *
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
     * Constructor - less parameters (used for converting)
     *
     * @param user that this ingredient belongs to
     * @param ingredient that the user possesses
     */
    public UserIngredient(User user, Ingredient ingredient) {
        this.userIngredientID = 0;  // not yet assigned (0 means nothing)
        this.userID = user.getId();
        this.ingredientID = ingredient.getIngredientID();
        this.quantity = 0;  // not yet assigned
        this.ingredientName = ingredient.getIngredientName();
        this.unit_of_measure = ingredient.getUnitOfMeasure();
    }


    /**
     * Get user ID of this particular UserIngredient
     * @return userIngredientID
     */
    public int getUserIngredientID() {
        return userIngredientID;
    }


    /**
     * Get user ID
     * @return userID
     */
    public int getUserID() {
        return userID;
    }


    /**
     * Get ingredient ID
     * @return ingredientID
     */
    public int getIngredientID() {
        return ingredientID;
    }


    /**
     * Get quantity of ingredient
     * @return int
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * Setter for the UserIngredient's quantity
     *
     * @param quantity to be set
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
