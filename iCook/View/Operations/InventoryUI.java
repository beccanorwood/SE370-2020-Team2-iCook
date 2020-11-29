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


public class InventoryUI{

    //Display dropbox containing list of available ingredients
    //User selects and it gets added to their inventory
    //Swing attributes
    private JFrame userInventory;
    private JPanel centerPanel;  //Selected ingredients that are displayed in center
    private JPanel rightPanel;
    private JButton search;
    private JButton searchAll;
    private JButton add;
    private Box box, button_box; //Organizes ingredients & delete buttons in vertical format
    private final JComboBox userIngredients;
    private final ArrayList<String> ingredientList;
    private Checkbox[] selectedIng;
    private JButton[] deleteBtns;
    private int deleteButtonPressedCount = 0;

    //
    ButtonListener bl = new ButtonListener();

    public InventoryUI(){

        // create a new controller object
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();

        // get all the ingredients from the system (NAMES ONLY)
        ingredientList = serviceDispatcher.getAllSystemIngredients();

        //Drop down box containing iCook ingredient inventory
        userIngredients = new JComboBox(ingredientList.toArray());

        //Drop down menu action listener class
        DropDownListener dl = new DropDownListener();
        userIngredients.addActionListener(dl);

        DeleteButtons(ingredientList);

        DisplayPanel(); //Initial with empty user inventory
    }

    private void DisplayPanel()
    {
        userInventory = new JFrame("iCook");
        userInventory.setSize(800, 800);
        userInventory.setLocationRelativeTo(null);
        userInventory.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userInventory.setLayout(new BorderLayout());

        JLabel title = new JLabel("Your Ingredient Inventory");
        title.setFont(new Font("ARIAL", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        add = new JButton("Add");
        search = new JButton("Search Checked Ingredients");
        searchAll = new JButton("Search All");
        search.addActionListener(bl);
        searchAll.addActionListener(bl);

        JPanel topPanel = new JPanel();
        topPanel.add(title);
        topPanel.setBackground(Color.BLACK);

        //Drop down menu of ingredients
        JPanel ingredientPanel = new JPanel();
        ingredientPanel.add(userIngredients);
        ingredientPanel.setBackground(Color.BLACK);

        box = Box.createVerticalBox();
        button_box = Box.createVerticalBox();

        centerPanel = new JPanel();
        centerPanel.setBackground(Color.BLACK);

        rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(search);
        bottomPanel.add(searchAll);
        bottomPanel.setBackground(Color.BLACK);

        userInventory.add(topPanel, BorderLayout.NORTH);
        userInventory.add(ingredientPanel, BorderLayout.WEST);
        userInventory.add(centerPanel, BorderLayout.CENTER);
        userInventory.add(rightPanel, BorderLayout.EAST);
        userInventory.add(bottomPanel, BorderLayout.SOUTH);
        userInventory.setVisible(true);
    }


    /*Method that creates separate arrays of the same size
    One containing JButtons & the other containing Checkboxes of user selected ingredients*/
    private void DeleteButtons(ArrayList<String> ingredientList)
    {
        deleteBtns = new JButton[ingredientList.size()];

        //Initialize JButton array (Not visible until user selects and ingredient from drop down menu)
        for(int i = 0; i < ingredientList.size(); i++){
            deleteBtns[i] = new JButton("Delete");
            deleteBtns[i].addActionListener(bl);
        }

        //Initialization of Checkbox array same size as ingredients & delete buttons
        // (Not visible until user selects an ingredient)
        selectedIng = new Checkbox[ingredientList.size()];

        for(int j = 0; j < ingredientList.size(); j++){
            selectedIng[j] = new Checkbox(ingredientList.get(j));
        }
    }

    private void DisplayInventoryandDeleteButtons(int position)
    {
        selectedIng[position].setForeground(Color.WHITE);
        box.add(selectedIng[position]);
        centerPanel.add(box);
        userInventory.add(centerPanel, BorderLayout.CENTER);

        button_box.add(deleteBtns[position]);
        rightPanel.add(button_box);
        userInventory.add(rightPanel, BorderLayout.EAST);
        userInventory.setVisible(true);

    }

    /*Method that displays an ingredient that user has previously removed*/

    private void AddRemovedIng(int index)
    {
        selectedIng[index].setVisible(true);
        deleteBtns[index].setVisible(true);
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
                    if(deleteButtonPressedCount >= 1){
                        AddRemovedIng(i);
                    }
                    System.out.println("Selected Item: " + ingredientList.get(i));
                    DisplayInventoryandDeleteButtons(i);
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

            for(int i = 0; i < deleteBtns.length; i++){
                if(src2 == deleteBtns[i]){
                    deleteButtonPressedCount++;
                    System.out.println("Delete Button " + i + " Pressed: "); //DEBUG
                        System.out.println("Selected Ingredient: " + selectedIng[i]);//DEBUG
                        selectedIng[i].setVisible(false); //Checkbox
                        deleteBtns[i].setVisible(false); //Delete box
                }
            }

            if(src2 == add){
                System.out.println("Add Button Pressed");
            }
            else if(src2 == search){
                System.out.println("Search Button Pressed");
                for(int j = 0; j < selectedIng.length; j++){
                    if(selectedIng[j].isShowing() && selectedIng[j].getState()){
                        //Open list of available ingredients
                        System.out.println("Ingredient Selected: " + ingredientList.get(j));
                    }
                }

            }
            else if(src2 == searchAll){
                System.out.println("Search All Button Pressed");
            }
        }
    }

}
