package iCook.View.Operations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class InventoryUI extends JFrame implements ActionListener {

    //Display dropbox containing list of available ingredients
    //User selects and it gets added to their inventory
    private JFrame userInventory;;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel ingredientPanel;
    private JPanel chosenIngredients;
    private JPanel rightPanel;
    private JButton search;
    private JButton searchAll;
    private JButton add;
    private JButton remove;
    private JLabel title;
    private String[] ingredients;
    private Checkbox name;
    private JComboBox userIngredients;
    private Box box;


    public InventoryUI(){
        userInventory = new JFrame("iCook");
        userInventory.setSize(500, 500);
        userInventory.setLocationRelativeTo(null);
        userInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInventory.setLayout(new BorderLayout());

        //Current ingredients
         ingredients = new String[]{"sugar", "milk", "salt", "chicken", "bacon",
         "pepper", "butter", "sunflower oil", "onion", "carrot", "mined beef",
         "mushrooms", "flour", "beef stock cube", "mashed potato", "butter",
         "tomatoes", "eggs", "milk", "potatoes"};

         userIngredients = new JComboBox(ingredients);
         userIngredients.addActionListener(this);

         title = new JLabel("Your Ingredient Inventory");
         title.setFont(new Font("ARIAL", Font.BOLD, 30));
         title.setForeground(Color.WHITE);

         add = new JButton("Add");
         remove = new JButton("Remove");
         search = new JButton("Search");
         searchAll = new JButton("Search All");
         add.addActionListener(this);
         remove.addActionListener(this);
         search.addActionListener(this);
         searchAll.addActionListener(this);

         topPanel = new JPanel();
         topPanel.add(title);
         topPanel.setBackground(Color.BLACK);

         ingredientPanel = new JPanel();
         ingredientPanel.add(userIngredients);
         ingredientPanel.setBackground(Color.BLACK);

         box = Box.createVerticalBox();

         chosenIngredients = new JPanel();
         chosenIngredients.setBackground(Color.BLACK);
         chosenIngredients.setForeground(Color.WHITE);

         rightPanel = new JPanel();
         rightPanel.add(remove);
         rightPanel.setBackground(Color.BLACK);

         bottomPanel = new JPanel();
         bottomPanel.add(search);
         bottomPanel.add(searchAll);
         bottomPanel.setBackground(Color.BLACK);

         userInventory.add(topPanel, BorderLayout.NORTH);
         userInventory.add(ingredientPanel, BorderLayout.WEST);
         userInventory.add(chosenIngredients, BorderLayout.CENTER);
         userInventory.add(rightPanel, BorderLayout.EAST);
         userInventory.add(bottomPanel, BorderLayout.SOUTH);

         userInventory.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JComboBox src2 = (JComboBox) e.getSource();

        Object selected = src2.getSelectedItem();
        System.out.println("Selected Item: " + selected);

        name = new Checkbox((String) selected);
        name.setForeground(Color.WHITE);
        box.add(name);

        chosenIngredients.add(box);
        userInventory.setVisible(true);

    }
}
