package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.DisplayObjects.RecipeDisplayObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * User interface for user's to view recipes they can cook based on their inventory. Users can click on a recipe they
 * wish to create and view its instructions. If a user does not have a sufficient inventory for any recipes, a message will be displayed
 *
 * @author Team 2
 * @version 12/3/2020
 */
public class RecipeUI extends JFrame implements ActionListener
{
    // instance variables
    private JFrame frame;
    private JPanel toppanel;

    private JPanel recipe_panel;
    private JScrollPane recipe_scrollable;

    private JPanel center_panel;
    private JScrollPane center_scrollable;

    private JPanel bottom_panel;
    private BufferedImage img;
    private JButton[] recipes;
    private ServiceDispatcher serviceDispatcher;

    public RecipeUI()
    {
        // use the service dispatcher to get the recipe list
        serviceDispatcher = new ServiceDispatcher();

        // set the frame up
        frame = new JFrame("iCook");
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        //
        JLabel iCook = new JLabel("Recipes in your market");
        iCook.setFont(new Font("Helvetica", Font.BOLD, 40));
        iCook.setForeground(new Color(249,250,244));
        ImageIcon logo;

        try
        {
            img = ImageIO.read(new File("iCook_Logo.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        logo = new ImageIcon(img);

        // get the logo
        JLabel iCookLogo = new JLabel();
        iCookLogo.setIcon(logo);

        // populate the header
        toppanel = new JPanel();
        toppanel.setBackground(new Color(26, 27, 34));
        toppanel.add(iCook);
        //toppanel.add(iCookLogo);

        // row will be the size of the array returned from the controller containing recipes
        int row = 40;
        recipe_panel = new JPanel(new GridLayout(row, 1));
        recipe_panel.setBackground(new Color(26, 27, 34));

        // create an array of buttons
        recipes = new JButton[row];

        // idk if this does anything
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;

        // populate the left scrollable with recipe buttons
        for(int i = 0; i < row; i++)
        {
            recipes[i] = new JButton();
            recipes[i].setText("Recipe " + i);
            recipes[i].addActionListener(this);
            recipes[i].setFont(new Font("Helvetica", Font.PLAIN, 16));
            recipes[i].setPreferredSize(new Dimension(123,32));
            recipe_panel.add(recipes[i], gbc);
            recipe_panel.add(Box.createVerticalStrut(32));
        }

        // set the center panel
        center_panel = new JPanel();
        center_panel.setBackground(new Color(26, 27, 34));

        // create button to go back to home page
        JButton home = new JButton("Home");
        home.setFont(new Font("Helvetica", Font.PLAIN, 16));
        home.setPreferredSize(new Dimension(144,32));

        // create button to go to inventory page
        JButton inv = new JButton("Inventory");
        inv.setFont(new Font("Helvetica", Font.PLAIN, 16));
        inv.setPreferredSize(new Dimension(144,32));

        // make them work
        home.addActionListener(this);
        inv.addActionListener(this);

        // set the bottom panel to contain the navigation buttons
        bottom_panel = new JPanel();
        bottom_panel.setBackground(new Color(26, 27, 34));
        bottom_panel.add(home);
        bottom_panel.add(inv);

        // add the header to the frame
        frame.add(toppanel, BorderLayout.NORTH);

        // add the left scroll panel to the frame
        recipe_scrollable = new JScrollPane(recipe_panel);
        recipe_scrollable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(recipe_scrollable, BorderLayout.WEST);

        // add the center scroll panel to the frame
        center_scrollable = new JScrollPane(center_panel);
        frame.add(center_scrollable, BorderLayout.CENTER);

        // add the navigation buttons to the frame
        frame.add(bottom_panel, BorderLayout.SOUTH);

        // set frame to visible
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        //get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        //Create 3 separate cases for each actionEvent to populate the proper JFrame
        if(buttonChosen.equals("Home"))
        {
            //Close initial login page
            frame.setVisible(false);
            frame.dispose();

            //Instantiate home Class to display home GUI
            new HomeUI(serviceDispatcher.getUserName());
        }

        else if(buttonChosen.equals("Inventory"))
        {
            frame.setVisible(false);
            frame.dispose();
            new InventoryUI();
        }

        // this is where we will figure out what button they pressed
        // and display the corresponding recipe's instructions
        else
        {
            for(int i = 0; i < recipes.length; i++){
                if(buttonChosen.equals("Recipe " + i))
                {
                    System.out.println("You pressed Button: " + i);
                }
            }

        }
    }


}
