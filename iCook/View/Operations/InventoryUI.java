package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.DisplayObjects.IngredientDisplayObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author Team 2
 * @version 12/2/2020
 */
public class InventoryUI extends JFrame{


    private JFrame frame;

    /*Left side of frame instance variables*/
    private JPanel mainleftPanel;
    private JButton[] increment;
    private JButton[] quantity;
    private JButton[] decrement;

    private JPanel[] btnContainer;
    private JPanel totalBtnContainer;

    JRadioButton[] ingredient_name; // = new JRadioButton[ingredientList.size()];
    JRadioButton [] units; //= new JRadioButton[ingredientList.size()];


    /*Right side of frame instance variables*/
    private JPanel mainrightPanel;
    private JButton[] increase;
    private JButton[] decrease;
    private JButton[] amount;

    private JPanel[] btnContainerRight;
    private JPanel totalBtnContainerRight;

    /*Bottom panel with Buttons instance variables*/
    private JButton home;
    private JButton recipes;
    private JButton update;


    private ServiceDispatcher serviceDispatcher;

    private ArrayList<IngredientDisplayObject> ingredientList;    // stores the system ingredients
    private ArrayList<String> ingredientNames;                          // stores the name of system ingredients (stored in the dropdown menu)

    private ArrayList<IngredientDisplayObject> userIngredientList;      // stores the user's ingredients
    private ArrayList<IngredientDisplayObject> addedIngredients;        // stores newly added ingredients
    private ArrayList<IngredientDisplayObject> pendingIngredientList;   // this is the list of the entire inventory for the user (sent to controller when update button pressed)

    private JButton[] currInventoryQuantity;
    private JButton[] currInventoryIncrement;
    private JButton[] currInventoryDecrement;

    private ArrayList<JButton> userInventoryIncrementBtns; // list of newly created + buttons (whenever add button is pressed)
    private ArrayList<JButton> userInventoryDecrementBtns; // list of newly created - buttons (whenever add button is pressed)
    private ArrayList<JButton> userInventoryQuantityBtns;

    private ArrayList<JButton> availableInventoryIncrementBtns;
    private ArrayList<JButton> availableInventoryDecrementBtns;
    private ArrayList<JButton> availableInventoryAmountBtns;

    ButtonListener bl = new ButtonListener();
    RadioButtonListener rbl = new RadioButtonListener();

    public InventoryUI()
    {
        //mainPanel = new JPanel();
        //setLayout(new BorderLayout());
        //this.setSize(1024, 768);
        //this.setResizable(false);

        // initialize instance variables
        addedIngredients = new ArrayList<>();
        userInventoryIncrementBtns = new ArrayList<>();
        userInventoryDecrementBtns = new ArrayList<>();
        userInventoryQuantityBtns = new ArrayList<>();

        availableInventoryIncrementBtns = new ArrayList<>();
        availableInventoryDecrementBtns = new ArrayList<>();
        availableInventoryAmountBtns = new ArrayList<>();

        serviceDispatcher = new ServiceDispatcher();
        ingredientList = serviceDispatcher.getAllSystemIngredients();
        ingredientNames = new ArrayList<>();
        userIngredientList = serviceDispatcher.getUserInventory();

        // for every ingredient in ingredientList, add its name to the ingredientNames ArrayList (drop down menu)
        for (IngredientDisplayObject ingredient : ingredientList)
        {
            if (userIngredientList.isEmpty())
                ingredientNames.add(ingredient.getName());

            else
                {
                // checks to see if the ingredient is in the user's inventory
                for (int j = 0; j < userIngredientList.size(); j++)
                {
                    // if the ingredient is in the user's inventory, skip this ingredient (don't add it)
                    if (ingredient.getName().equals(userIngredientList.get(j).getName()))
                        break;
                    // else if the ingredient is not in the user's ingredient list && the name list doesn't already contain it, add it
                    else if (j == userIngredientList.size() - 1 && !ingredientNames.contains(ingredient.getName()))
                        ingredientNames.add(ingredient.getName());
                }
            }
        }

        // sort the list of ingredient names
        Collections.sort(ingredientNames);

        DisplayFrame();

    }


    private ArrayList<IngredientDisplayObject> allAvailableIngredients(ArrayList<IngredientDisplayObject> currentInventory, ArrayList<IngredientDisplayObject> databaseInventory)
    {
        ArrayList<IngredientDisplayObject> temp = new ArrayList<>();
        for(int i = 0; i < ingredientList.size(); i++){
            temp.add(ingredientList.get(i));
        }

        System.out.println(temp.size());
        System.out.println(ingredientList.size());

        System.out.println(databaseInventory.size());
        System.out.println(currentInventory.size());

        for(int i = 0; i < ingredientList.size(); i++){
            for(int j = 0; j < userIngredientList.size(); j++){
                if(ingredientList.contains(userIngredientList)==true){
                    if(ingredientList.get(i).getName().equals(userIngredientList.get(j))){
                        temp.remove(temp.get(i));
                    }
                    else{
                        continue;
                    }
                }
            }
        }

        for(int i = 0; i < temp.size(); i++){
            System.out.println(temp.get(i).getName());
        }

        return temp;

    }

    private void DisplayFrame()
    {
        frame = new JFrame();
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel container = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        container.setBackground(new Color(26, 27, 34));
        topPanel.setBackground(new Color(26, 27, 34));
        bottomPanel.setBackground(new Color(26, 27, 34));

        container.setLayout(new GridLayout(0,1));

        home = new JButton("Home");
        recipes = new JButton("Recipes");
        update = new JButton("Update");

        home.addActionListener(bl);
        recipes.addActionListener(bl);
        update.addActionListener(bl);

        bottomPanel.add(home);
        bottomPanel.add(recipes);
        bottomPanel.add(update);

        bottomPanel.setLayout(new GridLayout(0, 6));


        JPanel panelThree = new JPanel(); //= new JPanel(new GridLayout(ingredientList.size(), 1));
        panelThree.setBackground(new Color(26, 27, 34));

        JPanel leftunitPanel = new JPanel(); //= new JPanel(new GridLayout(ingredientList.size(),1));
        leftunitPanel.setBackground(new Color(26, 27, 34));

        JPanel rightunitPanel = new JPanel();
        rightunitPanel.setBackground(new Color(26, 27, 34));


        /*Array for ingredients that can be added (on left side of frame) */
        mainleftPanel = new JPanel();
        mainleftPanel.setBackground(new Color(26, 27, 34));


        //==========================================================================

        /*Arrays for user's current inventory (right side of frame) */
        JPanel mainrightPanel = new JPanel();
        mainrightPanel.setBackground(new Color(26, 27, 34));

        JPanel ingredientPortion = new JPanel();
        ingredientPortion.setBackground(new Color(26, 27, 34));

        JPanel unitPortion = new JPanel();
        unitPortion.setBackground(new Color(26, 27, 34));

        JPanel[] btnContainerRight;
        JPanel totalBtnContainerRight = new JPanel();
        totalBtnContainerRight.setBackground(new Color(26, 27, 34));

        JLabel [] currInventoryName;
        JRadioButton [] currInventoryUnits;

        JButton[] increase;
        JButton[] amount;
        JButton[] decrease;


        if(userIngredientList.isEmpty()) {

            /*Left Panel Attributes*/

            ingredient_name = new JRadioButton[ingredientList.size()];
            units = new JRadioButton[ingredientList.size()];

            panelThree.setLayout(new GridLayout(ingredientList.size(), 1));
            leftunitPanel.setLayout(new GridLayout(ingredientList.size(), 1));


            for (int i = 0; i < ingredientList.size(); i++) {
                ingredient_name[i] = new JRadioButton();
                ingredient_name[i].setText(ingredientList.get(i).getName());
                ingredient_name[i].setFont(new Font("Arial", Font.PLAIN, 20));
                ingredient_name[i].setBackground(new Color(26, 27, 34));
                ingredient_name[i].setForeground(Color.WHITE);
                ingredient_name[i].addActionListener(rbl);
                ingredient_name[i].setEnabled(true);

                panelThree.add(ingredient_name[i]);

                units[i] = new JRadioButton();
                units[i].setText(ingredientList.get(i).getUnitOfMeasure());
                units[i].setBackground(new Color(26, 27, 34));
                units[i].setForeground(Color.WHITE);
                units[i].setFont(new Font("Arial", Font.PLAIN, 20));

                panelThree.add(ingredient_name[i]);
                leftunitPanel.add(units[i]);
            }

            increment = new JButton[ingredientList.size()];
            quantity = new JButton[ingredientList.size()];
            decrement = new JButton[ingredientList.size()];

            btnContainer = new JPanel[ingredientList.size()];

            totalBtnContainer = new JPanel(new GridLayout(ingredientList.size(), 1));
            totalBtnContainer.setBackground(new Color(26, 27, 34));


            for(int i = 0; i < btnContainer.length; i++){
                btnContainer[i] = new JPanel(new GridLayout(1, 0));
                btnContainer[i].setBackground(new Color(26, 27, 34));

                decrement[i] = new JButton("-");
                decrement[i].setFont(new Font("Arial", Font.PLAIN, 19));
                decrement[i].setForeground(new Color(26, 27, 34));

                quantity[i] = new JButton(" 0 ");
                quantity[i].setFont(new Font("Arial", Font.PLAIN, 19));
                quantity[i].setForeground(new Color(26, 27, 34));

                increment[i] = new JButton("+");
                increment[i].setFont(new Font("Arial", Font.PLAIN, 19));
                increment[i].setForeground(new Color(26, 27, 34));

                btnContainer[i].add(decrement[i]);
                btnContainer[i].add(quantity[i]);
                btnContainer[i].add(increment[i]);


                totalBtnContainer.add(btnContainer[i]);

                decrement[i].addActionListener(bl);
                increment[i].addActionListener(bl);
                quantity[i].addActionListener(bl);

                availableInventoryIncrementBtns.add(increment[i]);
                availableInventoryAmountBtns.add(quantity[i]);
                availableInventoryDecrementBtns.add(decrement[i]);
            }

        }
        else{

            /*Pass usersInventory to iCookingredientinventory & extract values
            that user does not have and put into available ingredients array
             */

            //ArrayList<IngredientDisplayObject> userUpdatedList = updatedIngredients(userIngredientList, ingredientList);
            ArrayList<IngredientDisplayObject> updatedAvailableIngredients = allAvailableIngredients(userIngredientList, ingredientList);

            /*Size for available ingredients*/
            int availableIngredientsSize = updatedAvailableIngredients.size();

            /*Displays available ingredients on right side of jframe */
            int inventorySize = ingredientList.size() - userIngredientList.size();

            ingredient_name = new JRadioButton[availableIngredientsSize];
            units = new JRadioButton[availableIngredientsSize];
            increment = new JButton[availableIngredientsSize];
            quantity = new JButton[availableIngredientsSize];
            decrement = new JButton[availableIngredientsSize];

            btnContainer = new JPanel[availableIngredientsSize];
            totalBtnContainer = new JPanel(new GridLayout(availableIngredientsSize, 1));
            totalBtnContainer.setBackground(new Color(26, 27, 34));


            panelThree.setLayout(new GridLayout(availableIngredientsSize, 1));
            leftunitPanel.setLayout(new GridLayout(availableIngredientsSize, 1));

            for(int i = 0; i < availableIngredientsSize; i++){
                ingredient_name[i] = new JRadioButton();
                ingredient_name[i].setText(updatedAvailableIngredients.get(i).getName());
                ingredient_name[i].setFont(new Font("Arial", Font.PLAIN, 20));
                ingredient_name[i].setBackground(new Color(26, 27, 34));
                ingredient_name[i].setForeground(Color.WHITE);
                ingredient_name[i].addActionListener(rbl);
                ingredient_name[i].setEnabled(true);

                panelThree.add(ingredient_name[i]);

                units[i] = new JRadioButton();
                units[i].setText(updatedAvailableIngredients.get(i).getUnitOfMeasure());
                units[i].setBackground(new Color(26, 27, 34));
                units[i].setForeground(Color.WHITE);
                units[i].setFont(new Font("Arial", Font.PLAIN, 20));

                leftunitPanel.add(units[i]);
            }

            for(int i = 0; i < btnContainer.length; i++){
                decrement[i] = new JButton("-");
                decrement[i].setFont(new Font("Arial", Font.PLAIN, 19));
                decrement[i].setForeground(new Color(26, 27, 34));

                quantity[i] = new JButton(" ");
                quantity[i].setFont(new Font("Arial", Font.PLAIN, 19));
                quantity[i].setForeground(new Color(26, 27, 34));

                increment[i] = new JButton("+");
                increment[i].setFont(new Font("Arial", Font.PLAIN, 19));
                increment[i].setForeground(new Color(26, 27, 34));

                btnContainer[i] = new JPanel(new GridLayout(1, 0));
                btnContainer[i].setBackground(new Color(26, 27, 34));
                btnContainer[i].add(decrement[i]);
                btnContainer[i].add(quantity[i]);
                btnContainer[i].add(increment[i]);


                totalBtnContainer.add(btnContainer[i]);

                decrement[i].addActionListener(bl);
                increment[i].addActionListener(bl);
                quantity[i].addActionListener(bl);

                availableInventoryIncrementBtns.add(increment[i]);
                availableInventoryAmountBtns.add(quantity[i]);
                availableInventoryDecrementBtns.add(decrement[i]);
            }

            /*Right Panel Attributes*/
            currInventoryName = new JLabel[userIngredientList.size()];
            currInventoryUnits = new JRadioButton[userIngredientList.size()];
            decrease = new JButton[userIngredientList.size()];
            amount = new JButton[userIngredientList.size()];
            increase = new JButton[userIngredientList.size()];

            rightunitPanel.setLayout(new GridLayout(userIngredientList.size(), 1));

            ingredientPortion.setLayout(new GridLayout(userIngredientList.size(), 1));

            btnContainerRight = new JPanel[userIngredientList.size()];
            totalBtnContainerRight.setLayout(new GridLayout(userIngredientList.size(), 1));


            for(int i = 0; i < userIngredientList.size(); i++){
                currInventoryName[i] = new JLabel();
                currInventoryName[i].setText(userIngredientList.get(i).getName());
                currInventoryName[i].setFont(new Font("Arial", Font.PLAIN, 20));
                currInventoryName[i].setBackground(new Color(26, 27, 34));
                currInventoryName[i].setForeground(Color.WHITE);

                ingredientPortion.add(currInventoryName[i]);
                ingredientPortion.setBackground(new Color(26, 27, 34));

                currInventoryUnits[i] = new JRadioButton();
                currInventoryUnits[i].setText(userIngredientList.get(i).getUnitOfMeasure());
                currInventoryUnits[i].setBackground(new Color(26, 27, 34));
                currInventoryUnits[i].setForeground(Color.WHITE);
                currInventoryUnits[i].setFont(new Font("Arial", Font.PLAIN, 20));

                rightunitPanel.add(currInventoryUnits[i]);
            }


            for(int i = 0; i < userIngredientList.size(); i++){
                decrease[i] = new JButton("-");
                decrease[i].setFont(new Font("Arial", Font.PLAIN, 19));
                decrease[i].setForeground(new Color(26, 27, 34));

                amount[i] = new JButton(" ");
                amount[i].setFont(new Font("Arial", Font.PLAIN, 19));
                amount[i].setText(String.valueOf(userIngredientList.get(i).getQuantity()));
                amount[i].setForeground(new Color(26, 27, 34));

                increase[i] = new JButton("+");
                increase[i].setFont(new Font("Arial", Font.PLAIN, 19));
                increment[i].setForeground(new Color(26, 27, 34));

                btnContainerRight[i] = new JPanel(new GridLayout(1, 0));
                btnContainerRight[i].setBackground(new Color(26, 27, 34));
                btnContainerRight[i].add(decrease[i]);
                btnContainerRight[i].add(amount[i]);
                btnContainerRight[i].add(increase[i]);

                decrease[i].addActionListener(bl);
                decrease[i].addActionListener(bl);
                amount[i].addActionListener(bl);


                totalBtnContainerRight.add(btnContainerRight[i]);

                userInventoryIncrementBtns.add(decrease[i]);
                userInventoryQuantityBtns.add(amount[i]);
                userInventoryDecrementBtns.add(increase[i]);
            }
        }


        JPanel paneltopLeft = new JPanel(new BorderLayout());
        paneltopLeft.setBackground(new Color(26, 27, 34));

        JLabel test1 = new JLabel("Available Ingredients");
        test1.setForeground(new Color(249,250,244));
        test1.setFont(new Font("Helvetica", Font.BOLD, 22));
        test1.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel paneltopRight = new JPanel(new BorderLayout());
        paneltopRight.setBackground(new Color(26, 27, 34));

        JLabel test2 = new JLabel("Your Ingredient Inventory");
        test2.setForeground(new Color(249,250,244));
        test2.setFont(new Font("Helvetica", Font.BOLD, 22));
        test2.setHorizontalAlignment(SwingConstants.CENTER);

        paneltopLeft.add(test1, BorderLayout.CENTER);
        paneltopRight.add(test2, BorderLayout.CENTER);


        JSplitPane s1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, paneltopLeft, paneltopRight);
        JSplitPane s2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, mainleftPanel, mainrightPanel);
        JSplitPane main_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, s1, s2);
        JSplitPane bottom_split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, main_split, bottomPanel);


        final boolean firstResize = true;

        /* Action listener lines up the split panes to be centered as much as possible*/

        s1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(firstResize){
                    s1.setDividerLocation(0.405);
                    main_split.setDividerLocation(0.040);
                }
            }
        });


        /*Left Panel Layout */
        mainleftPanel.add(panelThree, BorderLayout.WEST);
        mainleftPanel.add(totalBtnContainer, BorderLayout.CENTER);
        mainleftPanel.add(leftunitPanel, BorderLayout.EAST);
        mainleftPanel.setBackground(new Color(26, 27, 34));

        /*Right Panel Layout*/
        mainrightPanel.add(ingredientPortion, BorderLayout.WEST);
        mainrightPanel.add(totalBtnContainerRight, BorderLayout.CENTER);
        mainrightPanel.add(rightunitPanel, BorderLayout.EAST);
        mainrightPanel.setBackground(new Color(26, 27, 34));


        frame.add(main_split, BorderLayout.CENTER);
        frame.add(bottom_split, BorderLayout.SOUTH);
        frame.setSize(1024, 768);
        frame.setVisible(true);

    }


    private class RadioButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Loop to check through list of ingredients
            //If ingredient is selected, it will be added to center panel
            for(int i = 0; i < ingredient_name.length; i++)
            {
                // if the selected item == the ingredient's name
                if (ingredient_name[i].isSelected())
                {
                    // get the ingredient at i
                    IngredientDisplayObject ingredient = ingredientList.get(i);
                    String selectedIngredient = ingredient_name[i].getText();

                    if(selectedIngredient == ingredient.getName())
                    {
                        // add ingredient to list of new ingredient for user
                        addedIngredients.add(ingredient);

                        // testing purposes
                        System.out.println("Selected Item: " + ingredient.getName());
                        System.out.println("Unit of Measure: " + ingredient.getUnitOfMeasure());
                    }
                }
            }
        }

    }


    // ActionListeners for search, add, update, and home buttons
    private class ButtonListener implements ActionListener
    {
        /*
        * Updates the quantity of an ingredient that was already in the user's inventory
        */
        private int updateUserIngredientQuantity(int index, JButton operation){
            int currentQuantity = Integer.parseInt(userInventoryQuantityBtns.get(index).getText());
            int updatedQuantity = 0;

            if (operation.getText() == "+"){
                updatedQuantity = currentQuantity + 1;
            }
            else if (operation.getText() == "-" && currentQuantity > 0) {
                updatedQuantity = currentQuantity - 1;
            }

            return updatedQuantity;
        }


        /*
         * Updates the quantity of a newly added ingredient
         */
        private int updateNewIngredientQuantity(int index, JButton operation){
            int currentQuantity = 0;
            int updatedQuantity = 0;

            if (operation.getText() == "+"){
                updatedQuantity = currentQuantity + 1;
            }
            else if (operation.getText() == "-" && currentQuantity > 0) {
                updatedQuantity = currentQuantity - 1;
            }

            return updatedQuantity;
        }


        @Override
        public void actionPerformed(ActionEvent e) {

            JButton src2 = (JButton) e.getSource();
            int updatedQuantity = 0;


            // loop through array of increment buttons associated with existing ingredients
            for(int i = 0; i < userIngredientList.size(); i++)
            {
                if(src2 == userInventoryIncrementBtns.get(i))
                {
                        updatedQuantity = updateUserIngredientQuantity(i, userInventoryIncrementBtns.get(i));
                        userInventoryQuantityBtns.get(i).setText(String.valueOf(updatedQuantity));
                        userIngredientList.get(i).setQuantity(updatedQuantity);
                }
            }

            // loop through array of decrement buttons associated with existing ingredients
            for(int i = 0; i < userIngredientList.size(); i++){
                if(src2 == userInventoryDecrementBtns.get(i))
                {
                        updatedQuantity = updateUserIngredientQuantity(i, userInventoryDecrementBtns.get(i));
                        userInventoryQuantityBtns.get(i).setText(String.valueOf(updatedQuantity));
                        userIngredientList.get(i).setQuantity(updatedQuantity);
                }
            }

            // loop through every newly added inc button and set the quantity to its corresponding ingredient
            // in the addedIngredients List
            for (int i = 0; i < addedIngredients.size(); i++)
            {
                if(src2 == availableInventoryIncrementBtns.get(i))
                {
                    updatedQuantity = updateNewIngredientQuantity(i, availableInventoryIncrementBtns.get(i));
                    availableInventoryAmountBtns.get(i).setText(String.valueOf(updatedQuantity));
                    addedIngredients.get(i).setQuantity(updatedQuantity);
                }
            }

            // loop through every newly added dec button and set the quantity to its corresponding ingredient
            // in the addedIngredients List
            for (int i = 0; i < addedIngredients.size(); i++)
            {
                if(src2 == availableInventoryDecrementBtns.get(i))
                {
                    updatedQuantity = updateNewIngredientQuantity(i, availableInventoryDecrementBtns.get(i));
                    availableInventoryAmountBtns.get(i).setText(String.valueOf(updatedQuantity));
                    addedIngredients.get(i).setQuantity(updatedQuantity);
                }
            }

            // if the user clicked the search button
            if(src2 == recipes)
            {
                new RecipeUI();
                frame.setVisible(false);
                frame.dispose();
            }

            // else if the user clicked the update button
            else if(src2 == update)
            {
                pendingIngredientList = new ArrayList<>();          // to be sent to the controller for processing
                pendingIngredientList.addAll(userIngredientList);   // add the users ingredient list to the pending list
                pendingIngredientList.addAll(addedIngredients);     // add the newly added ingredients to the pending list
                serviceDispatcher.updateUserInventory(pendingIngredientList);

                new InventoryUI();

                // refresh this page
                frame.setVisible(false);
                frame.dispose();
            }

            // else if the user clicked the home button
            else if(src2 == home)
            {
                new HomeUI(serviceDispatcher.getUserName());

                //Instantiate home Class to display home GUI
                frame.setVisible(false);
                frame.dispose();
            }
        }
    }

}
