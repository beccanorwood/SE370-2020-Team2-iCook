package iCook.View.Operations.DisplayObjects;

import java.awt.image.BufferedImage;


/**
 * Decorated RecipeDisplayObject that is decorated with an image.
 * Used in the Decorator design pattern.
 *
 * @author Team 2
 * @version 5/3/2021
 */
public class Recipe_With_Image extends Recipe_With_Decorator {
    // instance variables
    private RecipeDisplayObjectIF decoratee;
    private BufferedImage image;

    /**
     * Constructor
     */
    public Recipe_With_Image(RecipeDisplayObjectIF decoratee, BufferedImage image) {
        super(decoratee);
        this.decoratee = decoratee;
        this.image = image;
    }


    @Override
    public BufferedImage display() {
        return image;
    }


}
