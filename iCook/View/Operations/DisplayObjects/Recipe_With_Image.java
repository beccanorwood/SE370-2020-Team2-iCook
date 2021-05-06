package iCook.View.Operations.DisplayObjects;

import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 * Decorated RecipeDisplayObject that is decorated with an image.
 * Used in the Decorator design pattern.
 *
 * @author Team 2
 * @version 5/6/2021
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
    public void display(JLabel label) {
        // add the image to the label
        label.removeAll();
        label.setIcon(new ImageIcon(image));
        label.setVisible(true);
    }


}
