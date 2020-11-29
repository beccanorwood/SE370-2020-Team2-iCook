package iCook.View.Operations;

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

        homeframe = new JFrame("iCook");
        JPanel homepanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        homeframe.setSize(500, 500);
        homeframe.setLocationRelativeTo(null);
        homeframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeframe.setLayout(new BorderLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        //iCook Logo
        try{
            img = ImageIO.read(new File("iCook_Logo(125).png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        //Frame attributes/components
        ImageIcon logo = new ImageIcon(img);
        JLabel pic = new JLabel();
        pic.setIcon(logo);

        JLabel welcome = new JLabel("Welcome " + userName + "!"); //Welcome label
        welcome.setFont(new Font("ARIAL", Font.BOLD, 30)); //Size, Color, etc
        welcome.setForeground(Color.WHITE);

        //Search & Inventory Buttons
        JButton search = new JButton("Search All");
        JButton myInventory = new JButton("My Inventory");
        JButton myRecipes = new JButton("My Recipes");
        search.addActionListener(this);
        myInventory.addActionListener(this);
        myRecipes.addActionListener(this);

        //Component Positions
        constraints.gridx = 3;
        constraints.gridy = 0;
        homepanel.add(welcome, constraints);

        constraints.gridx = 4;
        homepanel.add(pic, constraints);

        buttonPanel.add(search);
        buttonPanel.add(myInventory);
        buttonPanel.add(myRecipes);

        homepanel.setBackground(Color.BLACK);
        homeframe.add(homepanel, BorderLayout.CENTER);
        homeframe.add(buttonPanel, BorderLayout.SOUTH);
        homeframe.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn = e.getActionCommand();

        //User goes to view all recipes if search is clicked, otherwise they view their inventory
        //and add, update, or delete ingredients

        if(btn.equals("Search All")){

        }
        else{
            homeframe.setVisible(false);
            homeframe.dispose();
            InventoryUI inventory = new InventoryUI();
        }


    }
}
