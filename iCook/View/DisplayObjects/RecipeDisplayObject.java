package iCook.View.DisplayObjects;

/**
 * Display object for a recipe used exclusively in the model package.
 *
 * @Author: Team 2
 * @Date: 12/1/2020
 */
public class RecipeDisplayObject {

    // instance variables
    private String name;
    private String instructions;


    /**
     * Constructor - 2 parameters
     *
     * @param name the name of the recipe
     * @param instructions the instructions of the recipe
     */
    public RecipeDisplayObject(String name, String instructions)
    {
        this.name = name;
        this.instructions = instructions;
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
    public String getinstructions()
    {
        return instructions;
    }


} // end of RecipeDisplayObject class

