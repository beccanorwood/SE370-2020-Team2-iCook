package iCook.View.Operations.DisplayObjects;

import iCook.Model.Ingredient;

/**
 * Display object for an ingredient used in the View package
 *
 * @Author: Team 2
 * @Date: 4/25/2020
 */
public class IngredientDisplayObject implements Comparable<IngredientDisplayObject> {

    // instance variables
    private int ingredientID;
    private String name;
    private String unitOfMeasure;
    private int quantity;


    /**
     * Constructor - 4 parameters
     *
     * @param ingredientID the id of the ingredient
     * @param name the name of the ingredient
     * @param unitOfMeasure the unit of measure of the ingredient
     * @param quantity the quantity of the ingredient
     */
    public IngredientDisplayObject(int ingredientID, String name, String unitOfMeasure, int quantity) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = quantity;
    }


    /**
     * Constructor - 3 parameters
     *
     * @param ingredientID the id of the ingredient
     * @param name the name of the ingredient
     * @param unitOfMeasure the unit of measure of the ingredient
     */
    public IngredientDisplayObject(int ingredientID, String name, String unitOfMeasure){
        this.ingredientID = ingredientID;
        this.name = name;
        this.unitOfMeasure = unitOfMeasure;
        this.quantity = 0;
    }


    /**
     * Getter for the id of the ingredient
     *
     * @return the id
     */
    public int getIngredientID()
    {
        return ingredientID;
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
    public int getQuantity()
    {
        return quantity;
    }


    /**
     * Setter for the quantity of the ingredient
     *
     * @param quantity the quantity to be set
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }


    @Override
    public int compareTo(IngredientDisplayObject o) {
        return this.getName().charAt(0) - o.getName().charAt(0);
    }
} // end of IngredientDisplayObject class
