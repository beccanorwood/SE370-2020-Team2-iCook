package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Login.LoginUI;
import iCook.View.Login.SignUpUI;
import javafx.scene.layout.Border;

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
 *
 * @author Team 2
 * @version 11/29/2020
 */


public class RecipeUI extends JFrame implements ActionListener{
    private JFrame frame;
    private JPanel toppanel;
    private JPanel recipe_panel;
    private JPanel center_panel;
    private JPanel bottom_panel;
    private BufferedImage img;
    private JButton[] recipes;

    public RecipeUI(){
        frame = new JFrame("iCook");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        JLabel iCook = new JLabel("Available Recipes");
        iCook.setFont(new Font("ARIAL", Font.BOLD, 30));
        iCook.setForeground(Color.WHITE);


        try{
            img = ImageIO.read(new File("iCook_Logo.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        ImageIcon logo = new ImageIcon(img);


        JLabel iCookLogo = new JLabel();
        iCookLogo.setIcon(logo);

        toppanel = new JPanel();
        toppanel.setBackground(Color.BLACK);
        toppanel.add(iCook);
        toppanel.add(iCookLogo);


        int row = 16;
        recipe_panel = new JPanel(new GridLayout(row, 1));
        recipe_panel.setBackground(Color.BLACK);

        recipes = new JButton[row];

        for(int i = 0; i < 16; i++){
            recipes[i] = new JButton();
            recipes[i].setText("Recipe " + i);
            recipes[i].addActionListener(this);
            recipe_panel.add(recipes[i]);
            recipe_panel.add(Box.createVerticalStrut(10));
        }


        center_panel = new JPanel();
        center_panel.setBackground(Color.BLACK);


        JButton login = new JButton("Home");
        JButton signup = new JButton("Inventory");
        login.addActionListener(this);
        signup.addActionListener(this);

        bottom_panel = new JPanel();
        bottom_panel.setBackground(Color.BLACK);
        bottom_panel.add(login);
        bottom_panel.add(signup);

        frame.add(toppanel, BorderLayout.NORTH);
        frame.add(recipe_panel, BorderLayout.WEST);
        frame.add(center_panel, BorderLayout.CENTER);
        frame.add(bottom_panel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        //get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        //Create 3 separate classes for each actionEvent to populate the proper JFrame

        if(buttonChosen.equals("Home")){
            //Close initial login page
            frame.setVisible(false);
            frame.dispose();

            //Instantiate Login Class to display login GUI
            LoginUI userLoginUI = new LoginUI();

        }
        else if(buttonChosen.equals("Inventory")){
            frame.setVisible(false);
            frame.dispose();
            InventoryUI inventory = new InventoryUI();

        }
        else{
            for(int i = 0; i < recipes.length; i++){
                if(buttonChosen.equals("Recipe " + i)){
                    System.out.println("You pressed Button: " + i);
                }
            }

        }
    }
}
