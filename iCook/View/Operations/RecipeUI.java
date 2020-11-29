package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class RecipeUI  extends JFrame implements ActionListener {

    //GUI where user can view all recipes from iCook's database
    //Search and filter
    //Display dropbox containing list of available ingredients
    //User selects and it gets added to their inventory
    private int numofIngredients = 1;
    private JFrame Recipe;;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel ingredientPanel;
    private JPanel chosenIngredients;
    private JPanel rightPanel;
    private JButton modServings;
    private JButton Ingredients;
    private JButton execute;
    private JButton remove;
    private JLabel title;
    private String[] ingredients;
    private Checkbox name;
    private JComboBox userIngredients;
    private Box box;
    private Box button_box;
    private HashMap<JButton, Box> delete = new HashMap<JButton, Box>();
    private ArrayList<String> iCookIngredients = new ArrayList<>();


    public RecipeUI(){
        Recipe = new JFrame("iCook");
        Recipe.setSize(500, 500);
        Recipe.setLocationRelativeTo(null);
        Recipe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Recipe.setLayout(new BorderLayout());



        //Current ingredients
        ingredients = new String[]{"sugar", "milk", "salt", "chicken", "bacon",
                "pepper", "butter", "sunflower oil", "onion", "carrot", "mined beef",
                "mushrooms", "flour", "beef stock cube", "mashed potato", "tomatoes",
                "eggs", "milk", "potatoes"};

        for(int i = 0; i < ingredients.length; i++){
            iCookIngredients.add(ingredients[i]);
        }

        Collections.sort(iCookIngredients);

        userIngredients = new JComboBox(iCookIngredients.toArray());
        userIngredients.addActionListener(this);

        title = new JLabel("All Available Recipes");
        title.setFont(new Font("ARIAL", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        execute = new JButton("Execute Recipe");
        //remove = new JButton("Remove");
        modServings = new JButton("Modify Servings");
        Ingredients = new JButton("Ingredients");
        execute.addActionListener(this);
        modServings.addActionListener(this);
        Ingredients.addActionListener(this);

        topPanel = new JPanel();
        topPanel.add(title);
        topPanel.setBackground(Color.BLACK);

        ingredientPanel = new JPanel();
        ingredientPanel.add(userIngredients);
        ingredientPanel.setBackground(Color.BLACK);

        box = Box.createVerticalBox();
        button_box = Box.createVerticalBox();

        chosenIngredients = new JPanel();
        chosenIngredients.setBackground(Color.BLACK);
        chosenIngredients.setForeground(Color.WHITE);

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);

        bottomPanel = new JPanel();
        bottomPanel.add(modServings);
        bottomPanel.add(Ingredients);
        bottomPanel.setBackground(Color.BLACK);

        Recipe.add(topPanel, BorderLayout.NORTH);
        Recipe.add(ingredientPanel, BorderLayout.WEST);
        Recipe.add(chosenIngredients, BorderLayout.CENTER);
        Recipe.add(bottomPanel, BorderLayout.SOUTH);

        Recipe.setVisible(true);
    }

    private void DeletePanel(int numofIngredients)
    {
        //Everytime a user selects an ingredient from the dropdown list
        //A new remove button will be added to the rightmost panel
        ArrayList<JButton> removeIng = new ArrayList<JButton>();

        for(int i = 0; i < numofIngredients; i++) {
            JButton button = new JButton();
            button.setText("Remove");
            removeIng.add(button);
            rightPanel.add(removeIng.get(i));
            button_box.add(removeIng.get(i));
            removeIng.get(i).addActionListener(this);
        }

        rightPanel.add(button_box);
        Recipe.add(rightPanel, BorderLayout.EAST);
        Recipe.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String btn = e.getActionCommand();

        //User goes to view all recipes if search is clicked, otherwise they view their inventory
        //and add, update, or delete ingredients

        if(btn.equals("Execute Recipe")){
            Recipe.setVisible(false);
            Recipe.dispose();

        }
        else{

            Recipe.setVisible(false);
            Recipe.dispose();
            InventoryUI inventory = new InventoryUI();
        }

    }
}
