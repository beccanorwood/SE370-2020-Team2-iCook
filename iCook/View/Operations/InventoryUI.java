package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 *
 * @author Team 2
 * @version 11/29/2020
 */


public class InventoryUI extends JFrame{

    //Display dropbox containing list of available ingredients
    //User selects and it gets added to their inventory
    //Swing attributes
    private JPanel topPanel;
    private JPanel centerPanel;  //Selected ingredients that are displayed in center
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JButton add;
    private JButton search;
    private JButton update;
    private JButton increment;
    private JButton decrement;
    private JRadioButton ounces;
    private JRadioButton gallons;
    private JRadioButton cups;
    private Box box, button_box; //Organizes ingredients & delete buttons in vertical format
    private JComboBox userIngredients;
    private JRadioButton availableIngredients;
    private final ArrayList<String> ingredientList;
    private Checkbox[] selectedIng;
    private int row = 1; //InitialSize
    private int col = 1; //InitialSize

    ButtonListener bl = new ButtonListener();

    public InventoryUI(){

        // create a new controller object
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();

        // get all the ingredients from the system (NAMES ONLY)
        ingredientList = serviceDispatcher.getAllSystemIngredients();

        topPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();
        bottomPanel = new JPanel();

        TopPanel(); //Top Panel isn't modular, just displays title
        CreatePanels(row, col);
        DisplayPanel();
    }

    private void DisplayPanel()
    {
        setTitle("iCook");
        setSize(500, 500);
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
        CenterPanel(row, col);
        RightPanel(row, col);
        BottomPanel();
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
        userIngredients = new JComboBox(ingredientList.toArray());

        DropDownListener dl = new DropDownListener();
        userIngredients.addActionListener(dl);

        box = Box.createVerticalBox();
        //add = new JButton("Add");
        //add.addActionListener(bl);
        userIngredients.addActionListener(dl);
        leftPanel.setLayout(new GridLayout(row +1, col));
        leftPanel.setBackground(Color.BLACK);
        leftPanel.setBorder(BorderFactory.createTitledBorder("Name"));
        box.add(userIngredients);
        box.add(Box.createVerticalStrut(10));
        //box.add(add);
        leftPanel.add(box);
    }

    private void CenterPanel(int row, int col)
    {
        //Center Panel displaying amount of ingredient
        Box center_box = Box.createHorizontalBox();
        Box vertical_box = Box.createVerticalBox();
        centerPanel.setLayout(new GridLayout(row +1, col));
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setBorder(BorderFactory.createTitledBorder("Quantity"));
        increment = new JButton("+");
        increment.addActionListener(bl);
        decrement = new JButton("-");
        decrement.addActionListener(bl);
        JLabel amount = new JLabel("0");
        amount.setForeground(Color.WHITE);
        center_box.add(decrement);
        center_box.add(Box.createHorizontalStrut(10));
        center_box.add(amount);
        center_box.add(Box.createHorizontalStrut(10));
        center_box.add(increment);
        vertical_box.add(center_box);
        centerPanel.add(vertical_box);
    }

    private void RightPanel(int row, int col)
    {
        //RightPanel displaying measurement
        Box measure_Box = Box.createVerticalBox();
        ButtonGroup bg = new ButtonGroup(); //Groups buttons together, if one is selected no others can be selected

        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new GridLayout(row +1, col));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Unit"));
        ounces = new JRadioButton("ounces");
        ounces.setBackground(Color.BLACK);
        ounces.setForeground(Color.WHITE);
        gallons = new JRadioButton("gallons");
        gallons.setBackground(Color.BLACK);
        gallons.setForeground(Color.WHITE);
        cups = new JRadioButton("cups");
        cups.setBackground(Color.BLACK);
        cups.setForeground(Color.WHITE);
        bg.add(ounces);
        bg.add(gallons);
        bg.add(cups);
        measure_Box.add(ounces);
        measure_Box.add(gallons);
        measure_Box.add(cups);
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
            for(int i = 0; i < ingredientList.size(); i++){
                if(src1.getSelectedItem() == (ingredientList.get(i))){

                    System.out.println("Selected Item: " + ingredientList.get(i));

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
                System.out.println("Update Button Pressed");
            }
            else if(src2 == add){
                System.out.println("Add Button Pressed");
                row++;
                CreatePanels(row, col);
                pack();
                setVisible(true);
            }
            else if(src2 == increment){
                System.out.println("Increment Button Pressed");
                CenterPanel(row, col);

            }
            else if(src2 == decrement){
                System.out.println("Decrement Button Pressed");
            }
        }
    }

}
