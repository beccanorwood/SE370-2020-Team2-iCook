package iCook.View.Operations.DisplayObjects;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Display object for a recipe used in the View package
 *
 * @Author: Team 2
 * @Date: 5/3/2021
 */
public class RecipeDisplayObject implements RecipeDisplayObjectIF {

    // instance variables
    private int recipeID;
    private String name;
    private String instructions;
    private ArrayList<IngredientDisplayObject> ingredients;
    private boolean isPublished;


    /**
     * Constructor - 5 parameters
     *
     * @param recipeID the id of the recipe
     * @param name the name of the recipe
     * @param instructions the instructions of the recipe
     * @param ingredients the ingredients for this object
     * @param isPublished boolean value determining if this recipe is published
     */
    public RecipeDisplayObject(int recipeID, String name, String instructions, ArrayList<IngredientDisplayObject> ingredients, boolean isPublished) {
        this.recipeID = recipeID;
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.isPublished = isPublished;
    }


    /**
     * Getter for the id of the recipe
     *
     * @return the id
     */
    public int getRecipeID()
    {
        return recipeID;
    }


    /**
     * Getter for the name of the recipe
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * Getter for the instructions of the recipe
     *
     * @return the instructions
     */
    public String getInstructions()
    {
        return instructions;
    }


    /**
     * Getter for the ingredients of the recipe
     *
     * @return an ArrayList containing the corresponding ingredients for this recipe
     */
    public ArrayList<IngredientDisplayObject> getIngredients()
    {
        return ingredients;
    }


    /**
     * Getter for the publish status of the recipe
     *
     * @return the published variables boolean value
     */
    public boolean isPublished() {
        return isPublished;
    }


    /**
     * Comparable Interface Method that will compare
     * the names of RDO and sorts them accordingly
     */
    @Override
    public int compareTo(RecipeDisplayObjectIF o) {
        return this.getName().charAt(0) - o.getName().charAt(0);
    }


    /**
     * Used for the Decorator design pattern. A normal RecipeDisplayObject
     * will not display anything.
     */
    @Override
    public void display(JLabel label) {
        label.removeAll();
        label.setVisible(false);
    }


} // end of RecipeDisplayObject class

