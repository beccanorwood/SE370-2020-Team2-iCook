package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User interface for iCook's "home" page (Displayed when a user successfully logs in).
 * A user can chose to go to the inventory page, recipes page, or logout from this screen.
 *
 * @author Team 2
 * @version 04/16/2021
 */
public class HomeUI extends JPanel implements ActionListener {
    //User Home Page with two buttons
    //Search and My Inventory
    private BufferedImage img;
    private JPanel homepanel;
    private ServiceDispatcher serviceDispatcher;

    public HomeUI() {
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());
        homepanel = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(26,27,34));

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

        JLabel welcome = new JLabel("Hi " + serviceDispatcher.getUserName() + "!"); //Welcome label
        welcome.setFont(new Font("Helvetica", Font.BOLD, 60)); //Size, Color, etc
        welcome.setForeground(Color.WHITE);

        constraints.gridx = 4;
        constraints.gridy = 0;
        homepanel.add(welcome, constraints);

        constraints.gridx = 5;
        constraints.gridy = 0;
        homepanel.add(pic, constraints);

        // Buttons
        JButton manageBtn = new JButton("Manage");
        manageBtn.setFont(new Font("Helvetica", Font.PLAIN, 20));
        manageBtn.setPreferredSize(new Dimension(144,35));

        /*
        Admin button will be displayed for certain users
         */
        if (serviceDispatcher.isUserAdmin()) {
            constraints.gridx = 4;
            constraints.gridy = 1;
            manageBtn.setVisible(true);
            homepanel.add(buttonPanel.add(manageBtn), constraints);
        }

        JButton myInventory = new JButton("Inventory");
        myInventory.setFont(new Font("Helvetica", Font.PLAIN, 20));
        myInventory.setPreferredSize(new Dimension(144,35));

        constraints.gridx = 4;
        constraints.gridy = 2;
        homepanel.add(buttonPanel.add(myInventory), constraints);

        JButton recipes = new JButton("Recipes");
        recipes.setFont(new Font("Helvetica", Font.PLAIN, 20));
        recipes.setPreferredSize(new Dimension(144,35));

        constraints.gridx = 4;
        constraints.gridy = 3;
        homepanel.add(buttonPanel.add(recipes), constraints);

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Helvetica", Font.PLAIN, 20));
        logout.setPreferredSize(new Dimension(144,35));

        constraints.gridx = 4;
        constraints.gridy = 4;
        homepanel.add(buttonPanel.add(logout), constraints);

        manageBtn.addActionListener(this);
        recipes.addActionListener(this);
        myInventory.addActionListener(this);
        logout.addActionListener(this);

        homepanel.setBackground(new Color(26, 27, 34));
        this.add(homepanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn = e.getActionCommand();

        //User goes to view all recipes if search is clicked, otherwise they view their inventory
        //and add, update, or delete ingredients

        if (btn.equals("Logout")) {
            // log the user out of their account && take them back to WelcomeUI
            serviceDispatcher.logUserOut();
            serviceDispatcher.gotoWelcome(); //WelcomeUI with JPanel & JFrame as parameters
        }
        // take the user to RecipeUI
        else if (btn.equals("Recipes")) {
            serviceDispatcher.gotoRecipes();
        }
        else if (btn.equals("Manage")) {
            serviceDispatcher.gotoManageRecipesUI();
        }
        // take the user to InventoryUI
        else if (btn.equals("Inventory")){
            serviceDispatcher.gotoInventory();
        }
    }


} // end of HomeUI
