package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Login.LoginUI;
import iCook.View.Login.SignUpUI;

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
    private JPanel panel;
    private BufferedImage img;

    public RecipeUI(){
        frame = new JFrame("iCook");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(30, 30, 30, 30);

        JLabel iCook = new JLabel("Available Recipes");
        iCook.setFont(new Font("ARIAL", Font.BOLD, 30));
        iCook.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 6;
        panel.add(iCook, constraints);

        try{
            img = ImageIO.read(new File("iCook_Logo.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        ImageIcon logo = new ImageIcon(img);

        JLabel iCookLogo = new JLabel();
        iCookLogo.setIcon(logo);

        constraints.gridx = 3;
        constraints.gridy = 5;
        panel.add(iCookLogo, constraints);

        int j = 1;
        for(int i = 0; i < 16; i++){
            JButton login = new JButton("Recipe "+i);
            constraints.gridx = 1;
            constraints.gridy = i + (3/2);
            panel.add(login, constraints);

        }

        JButton login = new JButton("Home");
        JButton signup = new JButton("Inventory");
        JButton guest = new JButton("Guest Mode");

        login.addActionListener(this);
        signup.addActionListener(this);
        guest.addActionListener(this);

        constraints.gridx = 4;
        constraints.gridy = 30;
        panel.add(signup, constraints);


        panel.setBackground(Color.BLACK);
        frame.add(panel);
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
            //Close home page
            //SignUp class
            frame.setVisible(false);
            frame.dispose();

            //Sign Up class will display and user can create account
            SignUpUI userSignUpUI = new SignUpUI();

        }
        else{
            System.out.println("\nYou pressed the Guest Mode button!");

        }
    }
}
