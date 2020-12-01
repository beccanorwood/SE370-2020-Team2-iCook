package iCook.View.DisplayObjects;

/**
 * Display object for an ingredient used exclusively in the model package.
 *
 * @Author: Team 2
 * @Date: 12/1/2020
 */
public class IngredientDisplayObject {

    // instance variables
    private String name;
    private String unitOfMeasure;
    private double quantity;


    /**
     * Constructor - 3 parameters
     *
     * @param name the name of the ingredient
     * @param unitOfMeasure the unit of measure of the ingredient
     * @param quantity the quantity of the ingredient
     */
    public IngredientDisplayObject(String name, String unitOfMeasure, double quantity){
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
    }


    /**
     * Constructor - 2 parameters
     *
     * @param name the name of the ingredient
     * @param unitOfMeasure the unit of measure of the ingredient
     */
    public IngredientDisplayObject(String name, String unitOfMeasure){
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
    }


    /**
     * Getter for the name of the ingredient
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Getter for the unit of measure of the ingredient
     *
     * @return the unit of measure
     */
    public String getUnitOfMeasure()
    {
        return unitOfMeasure;
    }


    /**
     * Getter for the quantity of the ingredient
     *
     * @return the quantity
     */
    public double getQuantity()
    {
        return quantity;
    }


    /**
     * Setter for the quantity of the ingredient
     *
     * @param quantity the quantity to be set
     */
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }


} // end of IngredientDisplayObject class
