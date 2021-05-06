package iCook.View.Operations.DisplayObjects;

import java.awt.image.BufferedImage;
import javax.swing.*;

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
    public void display(JLabel label) {
        // resize the image to fit the label and then add it
        //Image resized_img = image.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.removeAll();
        label.setIcon(new ImageIcon(image));
        label.setVisible(true);


        //button.setIcon(new ImageIcon(image));

//        PointerInfo a = MouseInfo.getPointerInfo();
//        Point b = a.getLocation();
//        int x = (int) b.getX();
//        int y = (int) b.getY();
//        Popup popup;
//        JLabel label = new JLabel(new ImageIcon(image));
//        popup = PopupFactory.getSharedInstance().getPopup(button, label, button.getX(), button.getY()+5);
//        popup.show();

//        JFrame frame = new JFrame();
//        frame.setSize(300, 300);
//        JLabel label = new JLabel(new ImageIcon(image));
//        frame.add(label);
//        frame.setVisible(true);
    }


}
