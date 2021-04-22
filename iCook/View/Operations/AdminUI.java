package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUI extends JFrame {
    // instance variables
    private JFrame frame;
    private ServiceDispatcher serviceDispatcher;

    private JPanel mainPanel;
    private JScrollPane scrollPane;

    private JPanel namePanel;
    private JPanel instrPanel;
    private JPanel buttonsPanel;
    private JPanel ingredientPanel;

    private int numOfIngredients; // keeps track of how many ingredient rows we have

    public AdminUI(JFrame frame) {
        // use the service dispatcher to get the recipe list for the logged in user
        serviceDispatcher = new ServiceDispatcher();

        // set the frame up
        this.frame = frame;
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set up the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(0,1));

        // set up the main scroll pane
        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // ********************************
        // Setting up the Recipe Name panel
        // ********************************
        namePanel = new JPanel();
        namePanel.setSize(1024,150);

        JLabel nameLabel = new JLabel("Recipe:");
        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        namePanel.add(nameLabel, BorderLayout.WEST);

        JTextField nameTxtField = new JTextField(30);
        nameTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        nameTxtField.setPreferredSize(new Dimension(30, 40));
        namePanel.add(nameTxtField, BorderLayout.WEST);

        mainPanel.add(namePanel, BorderLayout.WEST);

        // ****************************************
        // Setting up the Recipe Instructions panel
        // ****************************************
        instrPanel = new JPanel();
        instrPanel.setSize(1024,150);

        JLabel instrLabel = new JLabel("Instructions:");
        instrLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        instrPanel.add(instrLabel, BorderLayout.WEST);

        JTextArea instrTxtField = new JTextArea(10, 30);
        instrTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));

        JScrollPane textScroll = new JScrollPane(instrTxtField);
        textScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        textScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        instrPanel.add(textScroll);

        mainPanel.add(instrPanel, BorderLayout.WEST);

        // *************
        // Buttons panel
        // *************
        buttonsPanel = new JPanel();
        buttonsPanel.setSize(1024,150);

        ButtonGroup radioBtnGroup = new ButtonGroup();

        JRadioButton publishedButton = new JRadioButton("Published");
        radioBtnGroup.add(publishedButton);
        buttonsPanel.add(publishedButton);

        JRadioButton notPublishedButton = new JRadioButton("Not Published");
        radioBtnGroup.add(notPublishedButton);
        buttonsPanel.add(notPublishedButton);

        JButton submitBtn = new JButton("Submit");
        buttonsPanel.add(submitBtn);

        mainPanel.add(buttonsPanel, BorderLayout.WEST);

        // ********************
        // 1st Ingredient Panel
        // ********************
        numOfIngredients = 1;
        addIngredientRow();

        // ********************************************
        // Set frame visible at end to show all changes
        // ********************************************
        this.frame.add(scrollPane);
        this.frame.setVisible(true);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
    }


//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String btn = e.getActionCommand();
//
//        if (btn.equals("+")) {
//            addIngredientRow();
//        } else if (btn.equals("-")) {
//            removeIngredientRow(e);
//        }
//
//    }


    private void addIngredientRow() {
        // create a new panel for additional ingredient
        ingredientPanel = new JPanel(new GridLayout(1, 4));
        ingredientPanel.setSize(1024,20);

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

        // add everything to the main panel
        mainPanel.add(ingredientPanel);
        numOfIngredients++;

        // dynamically update the frame
        // (added row will show immediately)
        frame.revalidate();
        frame.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }


    private void removeIngredientRow(ActionEvent e) {
        // remove the ingredientPanel containing the sub button that was pressed
        mainPanel.remove((((JButton)e.getSource()).getParent()));

        // dynamically update the frame
        frame.revalidate();
        frame.repaint();
    }

}
