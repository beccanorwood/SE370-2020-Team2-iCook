package iCook.Model;

import iCook.Model.DatabaseAccess.UserDAO;

/**
 * UserIngredient class stores user ingredient ID, user ID, ingredient ID, and quantity of ingredient.
 * This class is responsible for storing an ingredient for a user as well as the quantity they have
 * of the ingredient.
 *
 * @author Team 2
 * @version 11/14/2020
 */
public class UserIngredient {

    // Private class members
    private int userIngredientID;
    private int userID;
    private int ingredientID;
    private double quantity;

    /**
     * Constructor
     * @param userIngredientID int
     * @param userID int
     * @param ingredientID int
     * @param quantity int
     */
    public UserIngredient(int userIngredientID, int userID, int ingredientID, int quantity) {
        this.userIngredientID = userIngredientID;
        this.userID = userID;
        this.ingredientID = ingredientID;
        this.quantity = quantity;
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
    public double getQuantity() {
        return quantity;
    }

    /**
     * Set quantity of ingredient
     * @param quantity int
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserIngredientName() {

        UserDAO userDAO = new UserDAO();

        return  userDAO.getUserIngredientName();
    }
}
