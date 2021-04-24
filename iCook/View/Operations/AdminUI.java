package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * AdminUI
 *
 * @author Team 2
 * @version 04/23/2021
 */


public class AdminUI {

    // instance variables
    private JFrame frame;
    private ServiceDispatcher serviceDispatcher;

    private JPanel mainPanel; //Main panel for all elements
    private JScrollPane scrollPane; //Scroll Panel
    private JPanel containerPanel;//Panel that holds row of buttons to add ingredients

    private GridBagConstraints gbc;

    private JLabel nameLabel;
    private JTextField nameTxtField;
    private JLabel instrLabel;
    private JTextArea instrTxtField;
    private JButton submitBtn;

    private JPanel ingredientPanel; //Panel that holds the 4 buttons in bottoms row for adding ingredients

    private int numOfIngredients; // keeps track of how many ingredient rows we have

    public AdminUI(JFrame frame) {
        // use the service dispatcher to get the recipe list for the logged in user
        serviceDispatcher = new ServiceDispatcher();

        // set the frame up
        this.frame = frame;
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up the container panel
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0,1)); //Panel for Buttons on bottom to dynamically add/remove


        // ******************
        // Main Panel Set Up
        // ******************
        setupMainPanel();

        // ********************
        // 1st Ingredient Panel
        // ********************
        numOfIngredients = 1;
        addIngredientRow();

        // ********************************************
        // Set frame visible at end to show all changes
        // ********************************************
        this.frame.setVisible(true);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
    }

    private void setupMainPanel()
    {
        //******************************************
        //Initialize variables for main panel
        //******************************************

        nameLabel = new JLabel("Recipe:");
        nameLabel.setText("Recipe");
        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        nameLabel.setForeground(Color.decode("#ffffff"));

        nameTxtField = new JTextField(30);
        nameTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        nameTxtField.setPreferredSize(new Dimension(30, 40));

        instrLabel = new JLabel("Instructions:");
        instrLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        instrLabel.setForeground(Color.decode("#ffffff"));

        instrTxtField = new JTextArea(12, 35);
        instrTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        instrTxtField.setPreferredSize(new Dimension(30,30));


        //******************************************
        //Container Panel(Main Panel) initialization
        //******************************************

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7,10,7,10);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#23272A"));
        mainPanel.setSize(1024, 768);

        JPanel centerPanel = new JPanel(new GridBagLayout());

        //*****************************
        //Add components to main panel
        //*****************************

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;

        centerPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;

        centerPanel.add(nameTxtField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(instrLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        centerPanel.add(instrTxtField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        submitBtn = new JButton("Submit");
        centerPanel.add(submitBtn, gbc);

        ButtonGroup radioBtnGroup = new ButtonGroup();

        gbc.gridx = 2;
        gbc.gridy = 0;

        JRadioButton publishedButton = new JRadioButton("Published");
        publishedButton.setBackground((Color.decode("#23272A")));
        publishedButton.setForeground(Color.decode("#ffffff"));
        radioBtnGroup.add(publishedButton);
        centerPanel.add(publishedButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;

        JRadioButton notPublishedButton = new JRadioButton("Not Published");
        notPublishedButton.setBackground((Color.decode("#23272A")));
        notPublishedButton.setForeground(Color.decode("#ffffff"));
        radioBtnGroup.add(notPublishedButton);
        centerPanel.add(notPublishedButton, gbc);

        centerPanel.setBackground((Color.decode("#23272A")));
        mainPanel.add(centerPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.frame.add(scrollPane, BorderLayout.CENTER);
    }


    /*
    Method that dynamically adds/removes an ingredient row
     */
    private void addIngredientRow() {
        // create a new panel for additional ingredient
        ingredientPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        ingredientPanel.setBackground((Color.decode("#23272A")));


        // create the add button
        JButton addButton = new JButton("+");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIngredientRow();
            }
        });

        // create the sub button
        JButton subButton = new JButton("-");
        subButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeIngredientRow(e);
            }
        });
        if (numOfIngredients == 1)
            subButton.setEnabled(false);

        // get the list of ingredients here
        // and store them in a combo box
        String [] ingredients = {"", "Salt", "Flour", "Sugar", "Milk"};
        JComboBox list = new JComboBox(ingredients);

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(3, 5));

        // add everything to the newly created panel
        ingredientPanel.add(addButton);
        ingredientPanel.add(subButton);
        ingredientPanel.add(list);
        ingredientPanel.add(quantity);

        //Empty border that formats the buttons on the bottom to be centered with the
        //recipe text fields and instructions text fields
        ingredientPanel.setBorder(new EmptyBorder(2,166,2,260));

        // add everything to the main panel
        containerPanel.add(ingredientPanel);
        mainPanel.add(containerPanel, BorderLayout.SOUTH);
        numOfIngredients++;

        // dynamically update the frame
        // (added row will show immediately)
        frame.revalidate();
        frame.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }


    private void removeIngredientRow(ActionEvent e) {
        // remove the ingredientPanel containing the sub button that was pressed
        containerPanel.remove((((JButton)e.getSource()).getParent()));

        // dynamically update the frame
        frame.revalidate();
        frame.repaint();
    }

}
