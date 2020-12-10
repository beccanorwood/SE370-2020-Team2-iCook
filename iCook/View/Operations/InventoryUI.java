package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.IngredientDisplayObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User interface for user's to view their inventory. Two panels are presented to the user, the left panel shows
 * ingredients available to the user to select from, the right panel shows the users current inventory. A user
 * can add,remove, and update ingredients in their inventory on this page.
 *
 * @author Team 2
 * @version 12/10/2020
 */
public class InventoryUI extends JFrame{


    //Inventory UI frame that holds all inventory panels//

    private JFrame frame;
    private JScrollPane available_ingredients_scrollable;
    private JScrollPane inventory_scrollable;


    //Available Ingredients inventory attributes//
    /*Left side of frame instance variables*/

    private JPanel mainleftPanel; //left half of JFrame
    private JButton[] incrementBtns; //Buttons that user selects to increase amount of ingredient not in their inventory
    private JButton[] quantityBtns; //Button that changes value dependent upon the increment/decrement buttons
    private JButton[] decrementBtns; //Button that user selects to decrease the amount of an ingredient not in their inventory

    private JPanel[] btnContainer; //JPanel that holds each increment, decrement, and quantity button for the available ingredients
    private JPanel totalBtnContainer; //JPanel that holds all the separate button panels and aligns them in the middle of the available ingredient panel

    JCheckBox[] ingredient_name_buttons; //Ingredients that user does not currently have in their inventory, but are available from the database
    JLabel[] ingredient_unit_buttons; //Measurements corresponding to each available ingredient



    //User's ingredient inventory instance variables //

    /*Right side of frame instance variables*/


    private JPanel mainrightPanel; //Right half of JFrame
    private JButton[] increaseBtns; //Buttons that the user selects to update the quantity currently in their inventory by increasing the value
    private JButton[] decreaseBtns; //Buttons that the user selects to update the quantity currently in their inventory by decreasing the value
    private JButton[] amountBtns; //Buttons that changes value depending upon the increase/decrease values

    private JPanel[] btnContainerRight;
    private JPanel totalBtnContainerRight;

    /*Bottom panel with Buttons instance variables*/
    private JButton home;
    private JButton recipes;
    private JButton update;

    private ServiceDispatcher serviceDispatcher;

    private ArrayList<IngredientDisplayObject> ingredientList;    // stores the system ingredients

    private ArrayList<IngredientDisplayObject> userIngredientList;      // stores the user's ingredients
    private HashMap<String, IngredientDisplayObject> addedIngredients;        // stores newly added ingredients
    private ArrayList<IngredientDisplayObject> pendingIngredientList;   // this is the list of the entire inventory for the user (sent to controller when update button pressed)

    private ArrayList<JButton> userInventoryIncrementBtns; // Arraylist storing the increase buttons, connected to user's inventory panel
    private ArrayList<JButton> userInventoryDecrementBtns; // Arraylist storing the decrease buttons, connected to user's inventory panel
    private ArrayList<JButton> userInventoryQuantityBtns; // ArrayList storing the amount buttons, connected to user's inventory panel

    private ArrayList<JButton> availableInventoryIncrementBtns; //Arraylist storing increment buttons, connected to the available ingredient panel
    private ArrayList<JButton> availableInventoryDecrementBtns; //Arraylist storing decrement buttons, connected to the available ingredient panel
    private ArrayList<JButton> availableInventoryAmountBtns;//Arraylist storing the quantity buttons, connected to available ingredient panel


    ButtonListener bl = new ButtonListener(); //Inner Action Listener Class for InventoryUI buttons
    CheckBoxListener cbl = new CheckBoxListener(); //Inner Action Listener Class for InventoryUI radioButtons

    public InventoryUI()
    {
        addedIngredients = new HashMap<>();
        userInventoryIncrementBtns = new ArrayList<>();
        userInventoryDecrementBtns = new ArrayList<>();
        userInventoryQuantityBtns = new ArrayList<>();

        availableInventoryIncrementBtns = new ArrayList<>();
        availableInventoryDecrementBtns = new ArrayList<>();
        availableInventoryAmountBtns = new ArrayList<>();

        serviceDispatcher = new ServiceDispatcher();
        ingredientList = serviceDispatcher.getAvailableIngredients();
        userIngredientList = serviceDispatcher.getUserInventory();

        DisplayFrame();
    }

    private void DisplayFrame()
    {
        frame = new JFrame();
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        JPanel topPanel = new JPanel(); //Panel displaying labels for each panel
        JPanel bottomPanel = new JPanel(); //Panel displaying buttons to update their inventory, go to recipe page, or the home page

        topPanel.setBackground(new Color(26, 27, 34));
        bottomPanel.setBackground(new Color(26, 27, 34));

        home = new JButton("Home");
        home.setFont(new Font("Helvetica", Font.PLAIN, 16));
        home.setPreferredSize(new Dimension(144,32));
        home.setHorizontalAlignment(JButton.CENTER);

        recipes = new JButton("Recipes");
        recipes.setFont(new Font("Helvetica", Font.PLAIN, 16));
        recipes.setPreferredSize(new Dimension(144,32));
        recipes.setHorizontalAlignment(JButton.CENTER);

        update = new JButton("Update");
        update.setFont(new Font("Helvetica", Font.PLAIN, 16));
        update.setPreferredSize(new Dimension(144,32));
        update.setHorizontalAlignment(JButton.CENTER);


        //Calls the ButtonListener class //

        home.addActionListener(bl);
        recipes.addActionListener(bl);
        update.addActionListener(bl);

        //Add buttons to the bottomPanel //
        bottomPanel.add(home);
        bottomPanel.add(recipes);
        bottomPanel.add(update);


        //bottomPanel.setLayout(new GridLayout(0, 6));

        //bottomPanel.setLayout(new GridLayout(0, 3));


        //Panel inside of available ingredient inventory that holds the ingredient names
        JPanel availableIngredientsPanel = new JPanel();
        availableIngredientsPanel.setBackground(new Color(26, 27, 34));
        availableIngredientsPanel.setLayout(new GridLayout(ingredientList.size(), 1));


        //==========================================================================

        //Panel inside of available ingredient inventory side of frame, that holds the ingredient units
        JPanel availableIngredientsUnits = new JPanel();
        availableIngredientsUnits.setBackground(new Color(26, 27, 34));
        availableIngredientsUnits.setLayout(new GridLayout(ingredientList.size(), 1));



        //==========================================================================


        /*Panel on left side of frame that displays ingredients available to the user that
                                aren't in the user's inventory */

        mainleftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.fill = GridBagConstraints.VERTICAL;
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        mainleftPanel.setBackground(new Color(26, 27, 34));



        //==========================================================================

        //Components that are added to the right half of the jframe

        JPanel mainrightPanel = new JPanel(new GridBagLayout()); //Right panel, displaying user's inventory
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainrightPanel.setBackground(new Color(26, 27, 34));


        JPanel ingredientPortion = new JPanel(); //Panel that holds the ingredients of the user's inventory
        ingredientPortion.setBackground(new Color(26, 27, 34));

        JPanel currentIngredientsUnitPanel = new JPanel(); //Panel that holds the corresponding ingredient units of the user's inventory
        currentIngredientsUnitPanel.setBackground(new Color(26, 27, 34));

        JPanel[] btnContainerRight; //Array of panels, each panel holds the increase, decrease, and amount buttons for the user's current inventory
        JPanel totalBtnContainerRight = new JPanel(); //Vertical panel that holds the aforementioned array of button panels
        totalBtnContainerRight.setBackground(new Color(26, 27, 34));

        JLabel [] currInventoryName; //Array of labels that display the name of the user's current ingredient inventory
        JLabel [] currInventoryUnits; //Array of RadioButtons that display the units of the user's current ingredient inventory

        if(userIngredientList.isEmpty()) {

            //==========================================================================
                /*The available ingredients, displaying all ingredients, since user's
                                    inventory is empty*/
            //==========================================================================

            ingredient_name_buttons = new JCheckBox[ingredientList.size()];
            ingredient_unit_buttons = new JLabel[ingredientList.size()];

            availableIngredientsPanel.setLayout(new GridLayout(ingredientList.size(), 1));
            availableIngredientsUnits.setLayout(new GridLayout(ingredientList.size(), 1));

            gbc2.gridy = 0;

            // this is for the left panel names/units
            for (int i = 0; i < ingredientList.size(); i++) {
                ingredient_name_buttons[i] = new JCheckBox();
                ingredient_name_buttons[i].setText(ingredientList.get(i).getName());
                ingredient_name_buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 20));
                ingredient_name_buttons[i].setBackground(new Color(26, 27, 34));
                ingredient_name_buttons[i].setForeground(new Color(128,128,128));
                ingredient_name_buttons[i].setPreferredSize(new Dimension(180, 54));

                ingredient_name_buttons[i].addActionListener(cbl);
                ingredient_name_buttons[i].setEnabled(true);
                availableIngredientsPanel.add(ingredient_name_buttons[i], gbc2);

                ingredient_unit_buttons[i] = new JLabel();
                ingredient_unit_buttons[i].setText("  " + ingredientList.get(i).getUnitOfMeasure());
                ingredient_unit_buttons[i].setBackground(new Color(26, 27, 34));
                ingredient_unit_buttons[i].setForeground(new Color(128,128,128));
                ingredient_unit_buttons[i].setFont(new Font("Helvetica", Font.ITALIC, 18));
                ingredient_unit_buttons[i].setPreferredSize(new Dimension(62, 54));

                availableIngredientsUnits.add(ingredient_unit_buttons[i], gbc2);
                gbc2.gridy++;
            }

            gbc2.gridy = 0;

            incrementBtns = new JButton[ingredientList.size()];
            quantityBtns = new JButton[ingredientList.size()];
            decrementBtns = new JButton[ingredientList.size()];

            // holds buttons -/+ for a row
            btnContainer = new JPanel[ingredientList.size()];

            // holds all buttons
            totalBtnContainer = new JPanel(new GridLayout(ingredientList.size(), 1));
            totalBtnContainer.setBackground(new Color(26, 27, 34));

            // this is for left panel -/+ buttons
            for(int i = 0; i < btnContainer.length; i++){
                btnContainer[i] = new JPanel(new GridLayout(1, 0));
                btnContainer[i].setBackground(new Color(26, 27, 34));

                decrementBtns[i] = new JButton("-");
                decrementBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                decrementBtns[i].setForeground(new Color(26, 27, 34));
                decrementBtns[i].setPreferredSize(new Dimension(62, 54));
                decrementBtns[i].setAlignmentX(JButton.CENTER);
                decrementBtns[i].setAlignmentY(JButton.CENTER);

                quantityBtns[i] = new JButton(" ");
                quantityBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                quantityBtns[i].setForeground(new Color(26, 27, 34));
                quantityBtns[i].setPreferredSize(new Dimension(62, 54));
                quantityBtns[i].setAlignmentX(JButton.CENTER);
                quantityBtns[i].setAlignmentY(JButton.CENTER);

                incrementBtns[i] = new JButton("+");
                incrementBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                incrementBtns[i].setForeground(new Color(26, 27, 34));
                incrementBtns[i].setPreferredSize(new Dimension(62, 54));
                incrementBtns[i].setAlignmentX(JButton.CENTER);
                incrementBtns[i].setAlignmentY(JButton.CENTER);

                btnContainer[i].add(decrementBtns[i]);
                btnContainer[i].add(quantityBtns[i]);
                btnContainer[i].add(incrementBtns[i]);

                totalBtnContainer.add(btnContainer[i], gbc2);

                decrementBtns[i].addActionListener(bl);
                incrementBtns[i].addActionListener(bl);
                quantityBtns[i].addActionListener(bl);

                availableInventoryIncrementBtns.add(incrementBtns[i]);
                availableInventoryAmountBtns.add(quantityBtns[i]);
                availableInventoryDecrementBtns.add(decrementBtns[i]);

                gbc2.gridy++;
            }

        }


        else{

            //==========================================================================
                /*The available ingredients panel, displaying ingredients that
                            the user is missing from their inventory*/
            //==========================================================================

            ingredient_name_buttons = new JCheckBox[ingredientList.size()];
            ingredient_unit_buttons = new JLabel[ingredientList.size()];

            incrementBtns = new JButton[ingredientList.size()];
            quantityBtns = new JButton[ingredientList.size()];
            decrementBtns = new JButton[ingredientList.size()];

            // for a row of -/+ buttons
            btnContainer = new JPanel[ingredientList.size()];

            // for all buttons
            totalBtnContainer = new JPanel(new GridLayout(ingredientList.size(), 1));
            totalBtnContainer.setBackground(new Color(26, 27, 34));

            availableIngredientsPanel.setLayout(new GridLayout(ingredientList.size(), 1));
            availableIngredientsUnits.setLayout(new GridLayout(ingredientList.size(), 1));

            gbc2.gridy = 0;

            // this is for the name / units for each ingredient in the left panel
            for(int i = 0; i < ingredientList.size(); i++){
                ingredient_name_buttons[i] = new JCheckBox();
                ingredient_name_buttons[i].setText(ingredientList.get(i).getName());
                ingredient_name_buttons[i].setFont(new Font("Helvetica", Font.PLAIN, 20));
                ingredient_name_buttons[i].setBackground(new Color(26, 27, 34));
                ingredient_name_buttons[i].setForeground(new Color(128,128,128));
                ingredient_name_buttons[i].addActionListener(cbl);
                ingredient_name_buttons[i].setEnabled(true);
                ingredient_name_buttons[i].setPreferredSize(new Dimension(180, 54));

                availableIngredientsPanel.add(ingredient_name_buttons[i], gbc2);

                ingredient_unit_buttons[i] = new JLabel();
                ingredient_unit_buttons[i].setText("  " + ingredientList.get(i).getUnitOfMeasure());
                ingredient_unit_buttons[i].setBackground(new Color(26, 27, 34));
                ingredient_unit_buttons[i].setForeground(new Color(128,128,128));
                ingredient_unit_buttons[i].setFont(new Font("Helvetica", Font.ITALIC, 18));
                ingredient_unit_buttons[i].setPreferredSize(new Dimension(62, 54));


                availableIngredientsUnits.add(ingredient_unit_buttons[i], gbc2);

                gbc2.gridy++;
            }

            gbc2.gridy = 0;

            // this is for each -/+ button in the left panel
            for(int i = 0; i < btnContainer.length; i++)
            {
                decrementBtns[i] = new JButton("-");
                decrementBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                decrementBtns[i].setForeground(new Color(26, 27, 34));
                decrementBtns[i].setPreferredSize(new Dimension(62, 54));
                decrementBtns[i].setAlignmentX(JButton.CENTER);
                decrementBtns[i].setAlignmentY(JButton.CENTER);

                quantityBtns[i] = new JButton(" ");
                quantityBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                quantityBtns[i].setForeground(new Color(26, 27, 34));
                quantityBtns[i].setPreferredSize(new Dimension(62, 54));
                quantityBtns[i].setAlignmentX(JButton.CENTER);
                quantityBtns[i].setAlignmentY(JButton.CENTER);

                incrementBtns[i] = new JButton("+");
                incrementBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                incrementBtns[i].setForeground(new Color(26, 27, 34));
                incrementBtns[i].setPreferredSize(new Dimension(62, 54));
                incrementBtns[i].setAlignmentX(JButton.CENTER);
                incrementBtns[i].setAlignmentY(JButton.CENTER);

                btnContainer[i] = new JPanel(new GridLayout(1, 0));
                btnContainer[i].setBackground(new Color(26, 27, 34));

                btnContainer[i].add(decrementBtns[i]);
                btnContainer[i].add(quantityBtns[i]);
                btnContainer[i].add(incrementBtns[i]);

                totalBtnContainer.add(btnContainer[i], gbc2);

                decrementBtns[i].addActionListener(bl);
                quantityBtns[i].addActionListener(bl);
                incrementBtns[i].addActionListener(bl);

                availableInventoryIncrementBtns.add(incrementBtns[i]);
                availableInventoryAmountBtns.add(quantityBtns[i]);
                availableInventoryDecrementBtns.add(decrementBtns[i]);

                gbc2.gridy++;
            }



            /*Right Panel Attributes*/
            currInventoryName = new JLabel[userIngredientList.size()];
            currInventoryUnits = new JLabel[userIngredientList.size()];
            decreaseBtns = new JButton[userIngredientList.size()];
            amountBtns = new JButton[userIngredientList.size()];
            increaseBtns = new JButton[userIngredientList.size()];

            currentIngredientsUnitPanel.setLayout(new GridLayout(userIngredientList.size(), 1));

            ingredientPortion.setLayout(new GridLayout(userIngredientList.size(), 1));

            btnContainerRight = new JPanel[userIngredientList.size()];
            totalBtnContainerRight.setLayout(new GridLayout(userIngredientList.size(), 1));

            gbc.gridy = 0;

            // this is for every name / unit of user possessed ingredient
            for(int i = 0; i < userIngredientList.size(); i++){
                currInventoryName[i] = new JLabel();
                currInventoryName[i].setText("     " + userIngredientList.get(i).getName());
                currInventoryName[i].setFont(new Font("Helvetica", Font.PLAIN, 20));
                currInventoryName[i].setBackground(new Color(26, 27, 34));
                currInventoryName[i].setForeground(new Color(249,250,244));
                currInventoryName[i].setPreferredSize(new Dimension(180, 54));

                ingredientPortion.add(currInventoryName[i], gbc);
                ingredientPortion.setBackground(new Color(26, 27, 34));

                currInventoryUnits[i] = new JLabel();
                currInventoryUnits[i].setText("  " + userIngredientList.get(i).getUnitOfMeasure());
                currInventoryUnits[i].setBackground(new Color(26, 27, 34));
                currInventoryUnits[i].setForeground(new Color(249,250,244));
                currInventoryUnits[i].setFont(new Font("Helvetica", Font.ITALIC, 18));
                currInventoryUnits[i].setPreferredSize(new Dimension(62, 54));


                currentIngredientsUnitPanel.add(currInventoryUnits[i], gbc);

                gbc.gridy++;
            }

            gbc.gridy = 0;

            // this is for every -/+ button for the ingredients the user possesses
            for(int i = 0; i < userIngredientList.size(); i++){
                decreaseBtns[i] = new JButton("-");
                decreaseBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                decreaseBtns[i].setForeground(new Color(26, 27, 34));
                decreaseBtns[i].setPreferredSize(new Dimension(62, 54));
                decreaseBtns[i].setAlignmentX(JButton.CENTER);
                decreaseBtns[i].setAlignmentY(JButton.CENTER);


                amountBtns[i] = new JButton(String.valueOf(userIngredientList.get(i).getQuantity()));
                amountBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                amountBtns[i].setText(String.valueOf(userIngredientList.get(i).getQuantity()));
                amountBtns[i].setForeground(new Color(26, 27, 34));
                amountBtns[i].setPreferredSize(new Dimension(62, 54));
                amountBtns[i].setAlignmentX(JButton.CENTER);
                amountBtns[i].setAlignmentY(JButton.CENTER);


                increaseBtns[i] = new JButton("+");
                increaseBtns[i].setFont(new Font("Helvetica", Font.PLAIN, 19));
                increaseBtns[i].setForeground(new Color(26, 27, 34));
                increaseBtns[i].setPreferredSize(new Dimension(62, 54));
                increaseBtns[i].setAlignmentX(JButton.CENTER);
                increaseBtns[i].setAlignmentY(JButton.CENTER);


                btnContainerRight[i] = new JPanel(new GridLayout(1, 0));
                btnContainerRight[i].setBackground(new Color(26, 27, 34));
                btnContainerRight[i].add(decreaseBtns[i]);
                btnContainerRight[i].add(amountBtns[i]);
                btnContainerRight[i].add(increaseBtns[i]);

                decreaseBtns[i].addActionListener(bl);
                amountBtns[i].addActionListener(bl);
                increaseBtns[i].addActionListener(bl);

                totalBtnContainerRight.add(btnContainerRight[i], gbc);

                userInventoryIncrementBtns.add(decreaseBtns[i]);
                userInventoryQuantityBtns.add(amountBtns[i]);
                userInventoryDecrementBtns.add(increaseBtns[i]);

                gbc.gridy++;
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

        // make left panel scrollable
        available_ingredients_scrollable = new JScrollPane(mainleftPanel);
        available_ingredients_scrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        available_ingredients_scrollable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // make left panel scrollable
        inventory_scrollable = new JScrollPane(mainrightPanel);
        inventory_scrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        inventory_scrollable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JSplitPane s1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, paneltopLeft, paneltopRight);
        JSplitPane s2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, available_ingredients_scrollable, inventory_scrollable);
        JSplitPane main_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, s1, s2);
        JSplitPane bottom_split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, main_split, bottomPanel);

        final boolean firstResize = true;

        /* Action listener lines up the split panes to be centered as much as possible*/

        s1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if(firstResize){
                    s1.setDividerLocation(0.5);
                    s2.setDividerLocation(0.5);
                    main_split.setDividerLocation(0.10);
                }
            }
        });


        /*Left Panel Layout */
        mainleftPanel.add(availableIngredientsPanel);
        mainleftPanel.add(totalBtnContainer);
        mainleftPanel.add(availableIngredientsUnits);
        mainleftPanel.setBackground(new Color(26, 27, 34));

        /*Right Panel Layout*/
        mainrightPanel.add(ingredientPortion);
        mainrightPanel.add(totalBtnContainerRight);
        mainrightPanel.add(currentIngredientsUnitPanel);
        mainrightPanel.setBackground(new Color(26, 27, 34));


        frame.add(main_split, BorderLayout.CENTER);
        frame.add(bottom_split, BorderLayout.SOUTH);
        frame.setSize(1024, 768);
        frame.setVisible(true);

    }


    private class CheckBoxListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            //Loop to check through list of ingredients
            //If ingredient is selected, it will be added to center panel
            for(int i = 0; i < ingredientList.size(); i++)
            {
                // if the selected item == the ingredient's name
                if (ingredient_name_buttons[i].isSelected())
                {
                    // get the ingredient at i
                    IngredientDisplayObject ingredient = ingredientList.get(i);
                    String selectedIngredient = ingredient_name_buttons[i].getText();

                    if(selectedIngredient == ingredient.getName() && !addedIngredients.containsValue(ingredient))
                    {
                        // add ingredient to list of new ingredient for user
                        addedIngredients.put(ingredient.getName(), ingredient);
                        ingredient_name_buttons[i].setForeground(new Color(249,250,244));
                        ingredient_name_buttons[i].setForeground(new Color(249,250,244));
                        ingredient_unit_buttons[i].setForeground(new Color(249,250,244));

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
            int currentQuantity;

            if (availableInventoryAmountBtns.get(index).getText() == " ")
                currentQuantity = 0;
            else
                currentQuantity = Integer.parseInt(availableInventoryAmountBtns.get(index).getText());

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
            int updatedQuantity;


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
            for (int i = 0; i < ingredientList.size(); i++)
            {
                if(src2 == availableInventoryIncrementBtns.get(i) && ingredient_name_buttons[i].isSelected())
                {
                    updatedQuantity = updateNewIngredientQuantity(i, availableInventoryIncrementBtns.get(i));
                    availableInventoryAmountBtns.get(i).setText(String.valueOf(updatedQuantity));
                    addedIngredients.get(ingredientList.get(i).getName()).setQuantity(updatedQuantity);
                }
            }

            // loop through every newly added dec button and set the quantity to its corresponding ingredient
            // in the addedIngredients List
            for (int i = 0; i < ingredientList.size(); i++)
            {
                if(src2 == availableInventoryDecrementBtns.get(i) && ingredient_name_buttons[i].isSelected())
                {
                    updatedQuantity = updateNewIngredientQuantity(i, availableInventoryDecrementBtns.get(i));
                    availableInventoryAmountBtns.get(i).setText(String.valueOf(updatedQuantity));
                    addedIngredients.get(ingredientList.get(i).getName()).setQuantity(updatedQuantity);
                }
            }

            // if the user clicked the recipes button
            if(src2 == recipes)
            {
                // go to RecipeUI
                serviceDispatcher.gotoRecipes(frame);
            }

            // else if the user clicked the update button
            else if(src2 == update)
            {
                pendingIngredientList = new ArrayList<>();          // to be sent to the controller for processing
                pendingIngredientList.addAll(userIngredientList);   // add the users ingredient list to the pending list
                pendingIngredientList.addAll(addedIngredients.values());     // add the newly added ingredients to the pending list
                serviceDispatcher.updateUserInventory(pendingIngredientList);

                // re-instantiate the InventoryUI to reflect changes
                serviceDispatcher.gotoInventory(frame);
            }

            // else if the user clicked the home button
            else if(src2 == home)
            {
                // go back to HomeUI
                serviceDispatcher.gotoHome(serviceDispatcher.getUserName(), frame);
            }
        }
    }

}
