package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Login.WelcomeUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeUI extends JFrame implements ActionListener {
    //User Home Page with two buttons
    //Search and My Inventory
    private BufferedImage img;
    private final JFrame homeframe;

    public HomeUI(String userName){
        ServiceDispatcher test = new ServiceDispatcher();

        test.getSatisfiedRecipes();

        homeframe = new JFrame("iCook");
        JPanel homepanel = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(26, 27, 34));

        homeframe.setSize(1024, 768);
        homeframe.setLocationRelativeTo(null);
        homeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeframe.setLayout(new BorderLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        //iCook Logo
        try{
            img = ImageIO.read(new File("iCook_Logo.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //Frame attributes/components
        ImageIcon logo = new ImageIcon(img);
        JLabel pic = new JLabel();
        pic.setIcon(logo);

        JLabel welcome = new JLabel("Hi " + userName + "!"); //Welcome label
        welcome.setFont(new Font("Helvetica", Font.BOLD, 54)); //Size, Color, etc
        welcome.setForeground(Color.WHITE);

        constraints.gridx = 4;
        constraints.gridy = 0;
        homepanel.add(welcome, constraints);

        constraints.gridx = 5;
        constraints.gridy = 0;
        homepanel.add(pic, constraints);

        // Buttons
        JButton myInventory = new JButton("My Inventory");
        myInventory.setFont(new Font("Helvetica", Font.PLAIN, 16));
        myInventory.setPreferredSize(new Dimension(144,32));

        constraints.gridx = 4;
        constraints.gridy = 1;
        homepanel.add(buttonPanel.add(myInventory), constraints);

        JButton recipes = new JButton("Recipes");
        recipes.setFont(new Font("Helvetica", Font.PLAIN, 16));
        recipes.setPreferredSize(new Dimension(144,32));

        constraints.gridx = 4;
        constraints.gridy = 2;
        homepanel.add(recipes, constraints);

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Helvetica", Font.PLAIN, 16));
        logout.setPreferredSize(new Dimension(144,32));

        constraints.gridx = 4;
        constraints.gridy = 3;
        homepanel.add(buttonPanel.add(logout), constraints);

        recipes.addActionListener(this);
        myInventory.addActionListener(this);
        logout.addActionListener(this);

        homepanel.setBackground(new Color(26, 27, 34));
        homeframe.add(homepanel, BorderLayout.CENTER);
        homeframe.add(buttonPanel, BorderLayout.SOUTH);
        homeframe.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn = e.getActionCommand();

        //User goes to view all recipes if search is clicked, otherwise they view their inventory
        //and add, update, or delete ingredients

        if(btn.equals("Logout")){
            homeframe.setVisible(false);
            homeframe.dispose();

            // log the user out of their account
            ServiceDispatcher servDis = new ServiceDispatcher();
            servDis.logUserOut();

            // take the user to the welcome page (start page)
            new WelcomeUI();
        }
        else if(btn.equals("Recipes")){
            homeframe.setVisible(false);
            homeframe.dispose();
            new RecipeUI();
        }
        else{
            homeframe.setVisible(false);
            homeframe.dispose();
            new InventoryUI();
        }


    }
}
