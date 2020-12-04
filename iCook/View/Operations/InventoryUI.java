package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.DisplayObjects.IngredientDisplayObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author Team 2
 * @version 12/2/2020
 */
public class InventoryUI extends JFrame{

    //Display dropbox containing list of available ingredients
    //User selects and it gets added to their inventory
    //Swing attributes
    private final JPanel topPanel;
    private final JPanel centerPanel;  //Selected ingredients that are displayed in center
    private final JPanel rightPanel;
    private final JPanel leftPanel;
    private JPanel bottomPanel;
    private JButton add;
    private JButton recipesbtn;
    private JButton update;
    private JButton homeBtn;
    private JButton increment;
    private JButton amount;
    private JButton decrement;//Always be current ingredient
    private JRadioButton unitOfMeasure;

    private ServiceDispatcher serviceDispatcher;

    private final ArrayList<IngredientDisplayObject> ingredientList;    // stores the system ingredients
    private ArrayList<String> ingredientNames;                          // stores the name of system ingredients (stored in the dropdown menu)

    private ArrayList<IngredientDisplayObject> userIngredientList;      // stores the user's ingredients
    private ArrayList<IngredientDisplayObject> addedIngredients;        // stores newly added ingredients
    private ArrayList<IngredientDisplayObject> pendingIngredientList;   // this is the list of the entire inventory for the user (sent to controller when update button pressed)

    private JButton[] currInventoryQuantity;
    private JButton[] currInventoryIncrement;
    private JButton[] currInventoryDecrement;

    private ArrayList<JButton> newlyAddedIncrementBtns; // list of newly created + buttons (whenever add button is pressed)
    private ArrayList<JButton> newlyAddedDecrementBtns; // list of newly created - buttons (whenever add button is pressed)

    //private JPanel mainPanel;

    //InitialSize of each panel's row & col
    private int row;
    private int col = 1;

    //User's current ingredient count
    private int ingCount = 0;

    ButtonListener bl = new ButtonListener();

    public InventoryUI()
    {
        //mainPanel = new JPanel();
        this.setSize(1024, 768);
        //this.setResizable(false);

        // initialize instance variables
        addedIngredients = new ArrayList<>();
        newlyAddedIncrementBtns = new ArrayList<>();
        newlyAddedDecrementBtns = new ArrayList<>();
        serviceDispatcher = new ServiceDispatcher();
        ingredientList = serviceDispatcher.getAllSystemIngredients();
        ingredientNames = new ArrayList<>();
        userIngredientList = serviceDispatcher.getUserInventory();
        row = userIngredientList.size();

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

        // the default item for the dropdown list
        ingredientNames.add("\"Select Ingredient\"");

        // sort the list of ingredient names
        Collections.sort(ingredientNames);

        topPanel = new JPanel();
        topPanel.setBackground(new Color(26, 27, 34));

        leftPanel = new JPanel();
        leftPanel.setBackground(new Color(26, 27, 34));

        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(26, 27, 34));

        rightPanel = new JPanel();
        rightPanel.setBackground(new Color(26, 27, 34));

        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(26, 27, 34));

        TopPanel(); //Top Panel isn't modular, just displays title

        // display the user's inventory if they have ingredients already
        if(userIngredientList.size() > 0)
            CurrentInventory();

        // display the panels for this frame
        BottomPanel();
        CreatePanels(row, col);
        DisplayPanel();
    }

    /*
    * Adds names of ingredients to the left panel
    * Adds quantity of ingredients to the center panel
    * Adds units of measure of ingredients to the right panel
    */
    private void CurrentInventory()
    {
        // *******************************************
        // *** LEFT PANEL FOR EXISTING INGREDIENTS ***
        // *******************************************

        // Left Panel uses name of IngredientDisplayObject which is retrieved from userIngredientList
        Box leftBox = Box.createVerticalBox();
        JLabel[] currInventoryName = new JLabel[userIngredientList.size()];

        leftPanel.setLayout(new GridLayout(row + 1, col));

        for(int i = 0; i < userIngredientList.size(); i++){
            currInventoryName[i] = new JLabel();
            currInventoryName[i].setText(userIngredientList.get(i).getName());
            currInventoryName[i].setForeground(Color.WHITE);
            leftBox.add(currInventoryName[i]);
            leftBox.add(Box.createVerticalStrut(10));
        }

        leftPanel.add(leftBox);
        leftPanel.setBackground(new Color(26, 27, 34));

        // *********************************************
        // *** CENTER PANEL FOR EXISTING INGREDIENTS ***
        // *********************************************

        //Center Panel uses quantity of IngredientDisplayObject which is retrieved from userIngredientList
        Box centerPanel_vbox = Box.createVerticalBox();

        Box[] quantityBoxes = new Box[userIngredientList.size()];
        currInventoryQuantity = new JButton[userIngredientList.size()];
        currInventoryIncrement = new JButton[userIngredientList.size()];
        currInventoryDecrement = new JButton[userIngredientList.size()];

        centerPanel.setBackground(new Color(26, 27, 34));


        for(int j = 0; j < userIngredientList.size(); j++){
            currInventoryQuantity[j] = new JButton();
            currInventoryDecrement[j] = new JButton("-");
            currInventoryIncrement[j] = new JButton("+");

            currInventoryIncrement[j].addActionListener(bl);
            currInventoryDecrement[j].addActionListener(bl);

            quantityBoxes[j] = Box.createHorizontalBox();

            currInventoryQuantity[j].setText(String.valueOf(userIngredientList.get(j).getQuantity()));

            quantityBoxes[j].add(currInventoryDecrement[j]);
            quantityBoxes[j].add(Box.createHorizontalStrut(10));

            quantityBoxes[j].add(currInventoryQuantity[j]);
            quantityBoxes[j].add(Box.createHorizontalStrut(10));

            quantityBoxes[j].add(currInventoryIncrement[j]);
            quantityBoxes[j].add(Box.createHorizontalStrut(10));

            centerPanel_vbox.add(quantityBoxes[j]);
        }

        centerPanel.add(centerPanel_vbox);

        // ********************************************
        // *** RIGHT PANEL FOR EXISTING INGREDIENTS ***
        // ********************************************

        //Right Panel uses unit of measure of IngredientDisplayObject which retrieved from userIngredientList
        Box rightpanel_vbox = Box.createVerticalBox();
        JRadioButton[] currInventoryUnit = new JRadioButton[userIngredientList.size()];

        for(int k = 0; k < userIngredientList.size(); k++){
            currInventoryUnit[k] = new JRadioButton();

            currInventoryUnit[k].setText(userIngredientList.get(k).getUnitOfMeasure());

            currInventoryUnit[k].setSelected(true);
            currInventoryUnit[k].setForeground(Color.WHITE);
            currInventoryUnit[k].setBackground(Color.BLACK);

            rightpanel_vbox.add(currInventoryUnit[k]);
            rightpanel_vbox.add(Box.createVerticalStrut(10));
        }

        rightPanel.add(rightpanel_vbox);
        rightPanel.setBackground(new Color(26, 27, 34));

        pack();
    }

    private void DisplayPanel()
    {
        setTitle("iCook");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(topPanel, BorderLayout.NORTH);

        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        //mainPanel.add(leftPanel, BorderLayout.WEST);
        //mainPanel.add(centerPanel, BorderLayout.CENTER);
        //mainPanel.add(rightPanel, BorderLayout.EAST);
        //JScrollPane scroller = new JScrollPane(mainPanel);
        //add(scroller);

        add(bottomPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private void CreatePanels(int row, int col)
    {
        LeftPanel(row, col);
    }


    private void TopPanel()
    {
        //Top Panel displaying Title
        JLabel title = new JLabel("Your Ingredient Inventory");
        title.setFont(new Font("Helvetica", Font.BOLD, 40));
        title.setForeground(new Color(249,250,244));

        topPanel.setBackground(new Color(26, 27, 34));
        topPanel.add(title);
    }

    private void LeftPanel(int row, int col)
    {
        //Left Panel displaying name of ingredient & dropdown menu
        JComboBox ingredientChoices = new JComboBox(ingredientNames.toArray());
        DropDownListener dl = new DropDownListener();
        ingredientChoices.addActionListener(dl);

        Box box = Box.createVerticalBox();

        leftPanel.setLayout(new GridLayout(row , col));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Name"));
        leftPanel.setBackground(new Color(26, 27, 34));


        box.add(ingredientChoices);
        box.add(Box.createVerticalStrut(10));

        leftPanel.add(box);
    }

    private void CenterPanel(int row, int col, int count)
    {
        //Center Panel displaying amount of ingredient
        Box center_box = Box.createHorizontalBox();
        Box vertical_box = Box.createVerticalBox();

        centerPanel.setLayout(new GridLayout(row , col));
        centerPanel.setBackground(new Color(26, 27, 34));
        centerPanel.setBorder(BorderFactory.createTitledBorder("Quantity"));

        increment = new JButton("+");
        increment.addActionListener(bl);

        decrement = new JButton("-");
        decrement.addActionListener(bl);

        // add newly created buttons to list of newly created buttons
        newlyAddedIncrementBtns.add(increment);
        newlyAddedDecrementBtns.add(decrement);

        amount = new JButton(String.valueOf(count));
        amount.setBackground(new Color(26, 27, 34));

        centerPanel.add(vertical_box);

        center_box.add(decrement);
        center_box.add(Box.createHorizontalStrut(10));

        center_box.add(amount);
        center_box.add(Box.createHorizontalStrut(10));

        center_box.add(increment);

        vertical_box.add(Box.createVerticalStrut(10));
        vertical_box.add(center_box);

        centerPanel.add(vertical_box);
        centerPanel.updateUI();
    }

    private void RightPanel(int row, int col, String name)
    {
        //RightPanel displaying measurement
        Box measure_Box = Box.createVerticalBox();
        ButtonGroup bg = new ButtonGroup(); //Groups buttons together, if one is selected no others can be selected

        rightPanel.setBackground(new Color(26, 27, 34));
        rightPanel.setLayout(new GridLayout(row , col));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Unit"));
        unitOfMeasure = new JRadioButton(name);
        unitOfMeasure.setBackground(new Color(26, 27, 34));
        unitOfMeasure.setForeground(new Color(249,250,244));
        bg.add(unitOfMeasure);
        measure_Box.add(unitOfMeasure);
        measure_Box.add(Box.createVerticalStrut(10));

        rightPanel.add(measure_Box);
        rightPanel.updateUI();
    }

    private void BottomPanel()
    {
        //Bottom Panel displaying Buttons
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(26, 27, 34));

        homeBtn = new JButton("Home");
        homeBtn.setFont(new Font("Helvetica", Font.PLAIN, 16));
        homeBtn.setPreferredSize(new Dimension(144,32));

        recipesbtn = new JButton("Recipes");
        recipesbtn.setFont(new Font("Helvetica", Font.PLAIN, 16));
        recipesbtn.setPreferredSize(new Dimension(144,32));

        add = new JButton("Add");
        add.setFont(new Font("Helvetica", Font.PLAIN, 16));
        add.setPreferredSize(new Dimension(144,32));

        update = new JButton("Update");
        update.setFont(new Font("Helvetica", Font.PLAIN, 16));
        update.setPreferredSize(new Dimension(144,32));

        add.addActionListener(bl);
        recipesbtn.addActionListener(bl);
        update.addActionListener(bl);
        homeBtn.addActionListener(bl);

        bottomPanel.add(homeBtn);
        bottomPanel.add(recipesbtn);
        bottomPanel.add(add);
        bottomPanel.add(update);
    }


    //DropDownMenu Action Listener
    private class DropDownListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            JComboBox src1 = (JComboBox) e.getSource();

            //Loop to check through list of ingredients
            //If ingredient is selected, it will be added to center panel
            for(int i = 0; i < ingredientList.size(); i++) {
                // get the ingredient at i
                IngredientDisplayObject ingredient = ingredientList.get(i);

                // if the selected item == the ingredient's name
                if(src1.getSelectedItem() == (ingredient.getName())){
                    // update the center panel to show the quantity and -/+ buttons
                    // update the right panel to show the unit of measure
                    CenterPanel(row, col, ingredient.getQuantity());
                    RightPanel(row, col, ingredient.getUnitOfMeasure());
                    pack();
                    addedIngredients.add(ingredient);

                    // testing purposes
                    System.out.println("Selected Item: " + ingredient.getName());
                    System.out.println("Unit of Measure: " + ingredient.getUnitOfMeasure());
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
            int currentQuantity = Integer.parseInt(currInventoryQuantity[index].getText());
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
            int currentQuantity = addedIngredients.get(index).getQuantity();
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
                if(src2 == currInventoryIncrement[i])
                {
                        updatedQuantity = updateUserIngredientQuantity(i, currInventoryIncrement[i]);
                        currInventoryQuantity[i].setText(Integer.toString(updatedQuantity));
                        userIngredientList.get(i).setQuantity(updatedQuantity);
                }
            }

            // loop through array of decrement buttons associated with existing ingredients
            for(int i = 0; i < userIngredientList.size(); i++){
                if(src2 == currInventoryDecrement[i])
                {
                        updatedQuantity = updateUserIngredientQuantity(i, currInventoryDecrement[i]);
                        currInventoryQuantity[i].setText(Integer.toString(updatedQuantity));
                        userIngredientList.get(i).setQuantity(updatedQuantity);
                }
            }

            // loop through every newly added inc button and set the quantity to its corresponding ingredient
            // in the addedIngredients List
            for (int i = 0; i < addedIngredients.size(); i++)
            {
                if(src2 == newlyAddedIncrementBtns.get(i))
                {
                    System.out.println("Increment Button Pressed");
                    updatedQuantity = updateNewIngredientQuantity(i, newlyAddedIncrementBtns.get(i));
                    addedIngredients.get(i).setQuantity(updatedQuantity);
                    amount.setText(String.valueOf(updatedQuantity));
                }
            }

            // loop through every newly added dec button and set the quantity to its corresponding ingredient
            // in the addedIngredients List
            for (int i = 0; i < addedIngredients.size(); i++)
            {
                if(src2 == newlyAddedDecrementBtns.get(i))
                {
                    System.out.println("Decrement Button Pressed");
                    updatedQuantity = updateNewIngredientQuantity(i, newlyAddedDecrementBtns.get(i));
                    addedIngredients.get(i).setQuantity(updatedQuantity);
                    amount.setText(String.valueOf(updatedQuantity));
                }
            }

            // if the user clicked the search button
            if(src2 == recipesbtn)
            {
                System.out.println("Recipes button pressed");
                dispose();
                new RecipeUI();
            }

            // else if the user clicked the update button
            else if(src2 == update)
            {
                System.out.println("Update Button Pressed");

                pendingIngredientList = new ArrayList<>();          // to be sent to the controller for processing
                pendingIngredientList.addAll(userIngredientList);   // add the users ingredient list to the pending list
                pendingIngredientList.addAll(addedIngredients);     // add the newly added ingredients to the pending list
                serviceDispatcher.updateUserInventory(pendingIngredientList);

                // refresh this page
                dispose();
                new InventoryUI();
            }

            // else if the user clicked the add button
            else if(src2 == add)
            {
                System.out.println("Add Button Pressed");
                row++;
                ingCount = 0;
                CreatePanels(row, col);
                pack();
                setVisible(true);
            }

            // else if the user clicked the home button
            else if(src2 == homeBtn)
            {
                //Instantiate home Class to display home GUI
                dispose();
                new HomeUI(serviceDispatcher.getUserName());
            }
        }
    }

}
