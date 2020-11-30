package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
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
    private JButton decrement;
    private final ArrayList<HashMap<String, String>> ingredientList;
    private ArrayList<String> ingredientNames;


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

        // for every HashMap in ingredientList, add the name to the ingredientNames ArrayList
        for (HashMap<String, String> map : ingredientList)
            ingredientNames.add(map.get("name"));

        // sort the list of ingredient names
        Collections.sort(ingredientNames);

        topPanel = new JPanel();
        leftPanel = new JPanel();
        centerPanel = new JPanel();
        rightPanel = new JPanel();
        bottomPanel = new JPanel();

        TopPanel(); //Top Panel isn't modular, just displays title
        BottomPanel();
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
        CenterPanel(row, col, ingCount);
        RightPanel(row, col);
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
        JComboBox userIngredients = new JComboBox(ingredientList.toArray());

        DropDownListener dl = new DropDownListener();
        userIngredients.addActionListener(dl);

        Box box = Box.createVerticalBox();
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

    private void CenterPanel(int row, int col, int count)
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
        amount = new JButton(String.valueOf(count));
        amount.setBackground(Color.BLACK);
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
        //RightPanel displaying measurement of ingredient selected
        Box measure_Box = Box.createVerticalBox();

        String unit_of_measure = "Strips";

        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new GridLayout(row +1, col));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Unit"));
        JRadioButton ounces = new JRadioButton(unit_of_measure);
        ounces.setBackground(Color.BLACK);
        ounces.setForeground(Color.WHITE);
        measure_Box.add(ounces);
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
                ingCount++;
                amount.setText(String.valueOf(ingCount));
            }
            else if(src2 == decrement){
                ingCount--;
                if(ingCount < 0){
                    JLabel negativeAmount = new JLabel("Amount can't be negative!");
                    negativeAmount.setForeground(Color.RED);
                    centerPanel.add(negativeAmount);
                    centerPanel.updateUI();
                }
                else {
                    System.out.println("Decrement Button Pressed");
                    amount.setText(String.valueOf(ingCount));
                }
            }
        }
    }

}
