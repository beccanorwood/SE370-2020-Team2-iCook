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
 * @version 11/29/2020
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
    private JButton search;
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
    private ArrayList<IngredientDisplayObject> updatedIngredientList;   // this will store the inventory the user wants to be updated (sends to controller)

    private JButton[] currInventoryQuantity;
    private JButton[] currInventoryIncrement;
    private JButton[] currInventoryDecrement;
    private int numOfNewIngs = 0;

    //InitialSize of each panel's row & col
    private int row = 1;
    private int col = 1;

    //User's current ingredient count
    private int ingCount = 0;

    ButtonListener bl = new ButtonListener();

    public InventoryUI(){

        // create a new controller object
        serviceDispatcher = new ServiceDispatcher();

        // get all the ingredients from the system
        ingredientList = serviceDispatcher.getAllSystemIngredients();
        ingredientNames = new ArrayList<>();

        // for every ingredient in ingredientList, add the name to the ingredientNames ArrayList
        for (IngredientDisplayObject ingredient : ingredientList) {
            ingredientNames.add(ingredient.getName());
        }

        // sort the list of ingredient names
        Collections.sort(ingredientNames);

        // get the user's inventory
        userIngredientList = serviceDispatcher.getUserInventory();
        row = userIngredientList.size();

        topPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();
        bottomPanel = new JPanel();

        TopPanel(); //Top Panel isn't modular, just displays title

        if(userIngredientList.size() > 0) {
            CurrentInventory();
        }

        BottomPanel();
        CreatePanels(row, col);
        DisplayPanel();
    }


    private void CurrentInventory()
    {
        //Add Labels of ingredients to leftpanel
        //Add quantity to center panel
        //Add units to right panel

        //Left Panel uses name of IngredientDisplayObject which retrieved from userIngredientList
        //and is displayed on left panel
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

        //Center Panel uses quantity of IngredientDisplayObject which retrieved from userIngredientList
        Box centerPanel_vbox = Box.createVerticalBox();
        Box centerPanel_hbox = Box.createHorizontalBox();

        Box[] quantityBoxes = new Box[userIngredientList.size()];
        currInventoryQuantity = new JButton[userIngredientList.size()];
        currInventoryIncrement = new JButton[userIngredientList.size()];
        currInventoryDecrement = new JButton[userIngredientList.size()];

        centerPanel.add(centerPanel_vbox);

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
            centerPanel_vbox.add(quantityBoxes[j]);
        }

        centerPanel.add(centerPanel_vbox);

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

        pack();
    }

    private void DisplayPanel()
    {
        setTitle("iCook");
        //setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);

    }

    private void CreatePanels(int row, int col)
    {
        LeftPanel(row, col);
        CenterPanel(row, col, ingCount);
        RightPanel(row, col, "n/a");
    }


    private void TopPanel()
    {
        //Top Panel displaying Title
        JLabel title = new JLabel("Your Ingredient Inventory");
        title.setFont(new Font("ARIAL", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        topPanel.setBackground(Color.BLACK);
        topPanel.add(title);
    }

    private void LeftPanel(int row, int col)
    {
        //Left Panel displaying name of ingredient & dropdown menu
        JComboBox ingredientChoices = new JComboBox(ingredientNames.toArray());
        DropDownListener dl = new DropDownListener();
        ingredientChoices.addActionListener(dl);

        Box box = Box.createVerticalBox();

        // ** TESTING TO ADD COLOR FOR BORDER **
        // Border border = new LineBorder(Color.GRAY, 1, true);
        // leftPanel.setBorder(border);

        leftPanel.setLayout(new GridLayout(row , col));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setBorder(BorderFactory.createTitledBorder("Name"));
        box.add(ingredientChoices);
        box.add(Box.createVerticalStrut(10));
        //box.add(add);
        leftPanel.add(box);
    }

    private void CenterPanel(int row, int col, int count)
    {
        //Center Panel displaying amount of ingredient
        Box center_box = Box.createHorizontalBox();
        Box vertical_box = Box.createVerticalBox();

        centerPanel.setLayout(new GridLayout(row , col));
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(BorderFactory.createTitledBorder("Quantity"));
        increment = new JButton("+");
        increment.addActionListener(bl);
        decrement = new JButton("-");
        decrement.addActionListener(bl);
        amount = new JButton(String.valueOf(count));
        amount.setBackground(Color.WHITE);
        amount.setForeground(Color.BLACK);
        centerPanel.add(vertical_box);
        center_box.add(decrement);
        center_box.add(Box.createHorizontalStrut(10));
        center_box.add(amount);
        center_box.add(Box.createHorizontalStrut(10));
        center_box.add(increment);
        vertical_box.add(Box.createVerticalStrut(10));
        vertical_box.add(center_box);
        centerPanel.add(vertical_box);
    }

    private void RightPanel(int row, int col, String name)
    {
        //RightPanel displaying measurement
        Box measure_Box = Box.createVerticalBox();
        ButtonGroup bg = new ButtonGroup(); //Groups buttons together, if one is selected no others can be selected

        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new GridLayout(row , col));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Unit"));
        unitOfMeasure = new JRadioButton(name);
        unitOfMeasure.setBackground(Color.BLACK);
        unitOfMeasure.setForeground(Color.WHITE);
        bg.add(unitOfMeasure);
        measure_Box.add(unitOfMeasure);
        measure_Box.add(Box.createVerticalStrut(10));
        rightPanel.add(measure_Box);
    }

    private void BottomPanel()
    {
        //Bottom Panel displaying Buttons
        bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        add = new JButton("Add");
        search = new JButton("Search");
        update = new JButton("Update");
        homeBtn = new JButton("Home");
        add.addActionListener(bl);
        search.addActionListener(bl);
        update.addActionListener(bl);
        homeBtn.addActionListener(bl);
        bottomPanel.add(add);
        bottomPanel.add(search);
        bottomPanel.add(update);
        bottomPanel.add(homeBtn);
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
                    // update the unit of measure on the right panel
                    unitOfMeasure.setText(ingredient.getUnitOfMeasure());
                    unitOfMeasure.updateUI();

                    // testing purposes
                    System.out.println("Selected Item: " + ingredient.getName());
                    System.out.println("Unit of Measure: " + ingredient.getUnitOfMeasure());
                }
            }
        }

    }


    //ActionListeners for delete buttons and soon to be search, add, and remove
    private class ButtonListener implements ActionListener
    {

        private String updatedQuantity(int index, JButton operation){
            String currentQuantity = currInventoryQuantity[index].getText();
            int change = 1;
            int num = Integer.parseInt(currentQuantity);
            int newAmount = 0;

            String newQuantity = " ";

            if(operation.getText() == "+"){
                newAmount = change + num;
            }
            else{
                newAmount = num - change;
            }

            newQuantity = String.valueOf(newAmount);

            return newQuantity;
        }


        @Override
        public void actionPerformed(ActionEvent e) {

            JButton src2 = (JButton) e.getSource();
            String updatedQuantity = " ";

            /*Loop through array of increment buttons */
            for(int i = 0; i < currInventoryIncrement.length; i++){
                if(src2 == currInventoryIncrement[i]){
                    updatedQuantity = updatedQuantity(i, currInventoryIncrement[i]);
                    currInventoryQuantity[i].setText(updatedQuantity);
                }
            }

            /*Loop through array of decrement buttons */
            for(int j = 0; j < currInventoryDecrement.length; j++){
                if(src2 == currInventoryDecrement[j]){
                    updatedQuantity = updatedQuantity(j, currInventoryDecrement[j]);
                    currInventoryQuantity[j].setText(updatedQuantity);
                }
            }


            if(src2 == search){
                System.out.println("Search button pressed");
            }
            else if(src2 == update){
                System.out.println("Update Button Pressed");

//                // update the quantities for ingredients already in the user's inventory
//                for (int i = 0; i < userIngredientList.size(); i++)
//                {
//                    IngredientDisplayObject userIngredient = userIngredientList.get(i);
//                    userIngredient.setQuantity(Double.parseDouble(currInventoryQuantity[i].getText()));
//                    updatedIngredientList.add(userIngredient);
//                }
//
//                // add the newly added ingredients to the updated ingredients list
//                for (int i = row; i > (row - numOfNewIngs); i--)
//                {
//                    String name = ;
//                    String unitOfMeasure;
//                    double quantity;
//
//                    IngredientDisplayObject addedIngredient = new IngredientDisplayObject(name, unitOfMeasure, quantity);
//                    updatedIngredientList.add(addedIngredient);
//                }
//
//                serviceDispatcher.updateUserInventory(updatedIngredientList);

            }
            else if(src2 == add){
                System.out.println("Add Button Pressed");
                row++;
                numOfNewIngs++;
                ingCount = 0;
                CreatePanels(row, col);
                pack();
                setVisible(true);
            }
            else if(src2 == homeBtn){
                //Instantiate home Class to display home GUI
                HomeUI homeUI = new HomeUI(serviceDispatcher.getUserName());
            }
            else if(src2 == increment){
                System.out.println("Increment Button Pressed");
                ingCount++;
                amount.setText(String.valueOf(ingCount));
            }
            else if(src2 == decrement){
                ingCount--;
                if(ingCount < 0){
                    System.out.println("Error: Ingredient Quantity Cannot be Negative!");
                }
                else {
                    System.out.println("Decrement Button Pressed");
                    amount.setText(String.valueOf(ingCount));
                }
            }
        }
    }

}
