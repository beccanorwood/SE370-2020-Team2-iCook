package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


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
    private JButton increment;
    private JButton amount;
    private JButton decrement;//Always be current ingredient
    private JRadioButton unitOfMeasure;

    private final ArrayList<HashMap<String, String>> ingredientList;    // stores the system ingredients
    private ArrayList<String> ingredientNames;      // stores the name of system ingredients (stored in the dropdown menu)
    private ArrayList<String> unitsOfMeasure;       // stores the unit of measure of the system ingredients

    private ArrayList<HashMap<String, String>> userIngredientList;      // stores the user's ingredients
    private ArrayList<String> userIngredientName;       // ArrayList of user ingredient names
    private ArrayList<String> userIngredientQuantity;   // ArrayList of user ingredient quantities
    private ArrayList<String> userIngredientUnit;       // ArrayList of user ingredient units

    private ArrayList<HashMap<String, String>> updatedIngredientList;   // this will store the inventory the user wants to be updated (sent to controller)


    //InitialSize of each panel's row & col
    private int row = 1;
    private int col = 1;

    //User's current ingredient count
    private int ingCount = 0;

    ButtonListener bl = new ButtonListener();

    public InventoryUI(){

        // create a new controller object
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();

        // get all the ingredients from the system
        ingredientList = serviceDispatcher.getAllSystemIngredients();
        ingredientNames = new ArrayList<>();
        unitsOfMeasure = new ArrayList<>();

        // for every HashMap in ingredientList, add the name to the ingredientNames ArrayList
        for (HashMap<String, String> map : ingredientList) {
            ingredientNames.add(map.get("name"));
            unitsOfMeasure.add(map.get("units_of_measure"));
        }

        // sort the list of ingredient names
        Collections.sort(ingredientNames);

        // get the user's inventory and store the name, quantity, and unit of measure in their own separate ArrayList
        userIngredientList = serviceDispatcher.getUserInventory();
        userIngredientName = new ArrayList<>();
        userIngredientQuantity = new ArrayList<>();
        userIngredientUnit = new ArrayList<>();
        row = userIngredientList.size();


        for (HashMap<String, String> map : userIngredientList)
        {
            userIngredientName.add(map.get("name"));
            userIngredientQuantity.add(map.get("quantity"));
            userIngredientUnit.add(map.get("unit_of_measure"));
        }

        System.out.println("\n\nTESTING:");

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


        //Left Panel uses userIngredientName arraylist which retrieved name of ingredient
        //in user's inventory and is displayed on left panel
        Box leftBox = Box.createVerticalBox();
        JLabel[] currInventoryName = new JLabel[userIngredientName.size()];


        for(int i = 0; i < userIngredientName.size(); i++){
            currInventoryName[i] = new JLabel();
            currInventoryName[i].setText(userIngredientName.get(i));
            currInventoryName[i].setForeground(Color.WHITE);
            leftBox.add(currInventoryName[i]);
            leftBox.add(Box.createVerticalStrut(10));
        }

        leftPanel.add(leftBox);


        //Center Panel using userIngredientQuantity from database then displays quantity
        Box centerPanel_vbox = Box.createVerticalBox();
        Box centerPanel_hbox = Box.createHorizontalBox();

        Box[] quantityBoxes = new Box[userIngredientQuantity.size()];
        JButton[] currInventoryQuantity = new JButton[userIngredientQuantity.size()];
        JButton[] currInventoryIncrement = new JButton[userIngredientQuantity.size()];
        JButton[] currInventoryDecrement = new JButton[userIngredientQuantity.size()];

        centerPanel.add(centerPanel_vbox);

        for(int j = 0; j < userIngredientQuantity.size(); j++){
            currInventoryQuantity[j] = new JButton();
            currInventoryDecrement[j] = new JButton("-");
            currInventoryIncrement[j] = new JButton("+");
            quantityBoxes[j] = Box.createHorizontalBox();
            currInventoryQuantity[j].setText(String.valueOf(userIngredientQuantity.get(j)));
            quantityBoxes[j].add(currInventoryDecrement[j]);
            quantityBoxes[j].add(Box.createHorizontalStrut(10));
            quantityBoxes[j].add(currInventoryQuantity[j]);
            quantityBoxes[j].add(Box.createHorizontalStrut(10));
            quantityBoxes[j].add(currInventoryIncrement[j]);
            centerPanel_vbox.add(quantityBoxes[j]);
        }

        centerPanel.add(centerPanel_vbox);

        //Right Panel uses userIngredientUnit array that pulls from database and displays units
        Box rightpanel_vbox = Box.createVerticalBox();
        JRadioButton[] currInventoryUnit = new JRadioButton[userIngredientUnit.size()];

        for(int k = 0; k < userIngredientUnit.size(); k++){
            currInventoryUnit[k] = new JRadioButton();
            currInventoryUnit[k].setText(userIngredientUnit.get(k));
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
        JComboBox userIngredients = new JComboBox(ingredientNames.toArray());
        DropDownListener dl = new DropDownListener();
        userIngredients.addActionListener(dl);

        Box box = Box.createVerticalBox();

        // ** TESTING TO ADD COLOR FOR BORDER **
        // Border border = new LineBorder(Color.GRAY, 1, true);
        // leftPanel.setBorder(border);

        leftPanel.setLayout(new GridLayout(row , col));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setBorder(BorderFactory.createTitledBorder("Name"));
        box.add(userIngredients);
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
        add.addActionListener(bl);
        search.addActionListener(bl);
        update.addActionListener(bl);
        bottomPanel.add(add);
        bottomPanel.add(search);
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
                // get the HashMap at i
                HashMap<String, String> map = ingredientList.get(i);

                // if the selected item == the ingredient's name
                if(src1.getSelectedItem() == (map.get("name"))){
                    // update the unit of measure on the right panel
                    //RightPanel(row, col, map.get("unit_of_measure"));
                    unitOfMeasure.setText(map.get("unit_of_measure"));
                    unitOfMeasure.updateUI();

                    // testing purposes
                    System.out.println("Selected Item: " + map.get("name"));
                    System.out.println("Unit of Measure: " + map.get("unit_of_measure"));
                }
            }
        }

    }


    //ActionListeners for delete buttons and soon to be search, add, and remove
    private class ButtonListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton src2 = (JButton) e.getSource();

            if(src2 == search){
                System.out.println("Search button pressed");
            }
            else if(src2 == update){
                // send new inventory to the database
                System.out.println("Update Button Pressed");
            }
            else if(src2 == add){
                System.out.println("Add Button Pressed");
                row++;
                ingCount = 0;
                CreatePanels(row, col);
                pack();
                setVisible(true);
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
