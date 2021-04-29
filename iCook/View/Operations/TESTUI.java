package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.IngredientDisplayObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * TEST class for new inventory UI design
 *
 * @author Team 2
 * @version 04/28/2021
 */
public class TESTUI extends JPanel {
    // instance variables
    private JPanel outerCenterPanel;
    private JLabel title;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel btnPanel;
    private JButton saveBtn;
    private JButton backBtn;
    private JScrollPane scrollPane;
    private JPanel centerPanel;
    private JPanel containerPanel;
    private GridBagConstraints gbc;

    private ArrayList<JPanel> all_ingredient_panels;
    private JPanel ingredientPanel;
    private boolean isFirstIngredient = true; // there should always be at least 1 row
    private int possessedIngredients; // used to stop user from adding rows when all ingredients are in inventory

    // data to populate the UI with
    private ServiceDispatcher serviceDispatcher;
    private ArrayList<IngredientDisplayObject> system_ingredients;
    private ArrayList<IngredientDisplayObject> user_ingredients;

    // styling
    private final Color BG = new Color(255,255,255);
    private final Color FG = new Color(51,51,51);
    private Border emptyBorder = BorderFactory.createEmptyBorder(); // btn formatting

    /**
     * Constructor
     */
    public TESTUI() {
        // initialize instance variables here
        serviceDispatcher = new ServiceDispatcher();
        system_ingredients = serviceDispatcher.getSystemIngredientDisplayObjects();
        user_ingredients = serviceDispatcher.getUserInventory();
        possessedIngredients = user_ingredients.size();

        // initialize the panel's contents
        initialize();

        // add an ingredient row for each ingredient the user possess
        if (!user_ingredients.isEmpty()) {
            for (IngredientDisplayObject ingDO : user_ingredients)
                addIngredientRow(ingDO);
        } else {
            addIngredientRow(null); // add only 1 row if their inventory is empty
        }

        // set this panel to be visible
        this.setVisible(true);
    }


    /**
     * Initializes the panel of its contents
     */
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 255));

        // ******************************
        // this is the header (top panel)
        title = new JLabel("Ingredient Inventory",SwingConstants.CENTER);
        title.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        topPanel = new JPanel(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(768,72));
        topPanel.setBackground(new Color(255,255,255));
        topPanel.add(title, SwingConstants.CENTER);
        this.add(topPanel, BorderLayout.NORTH);

        // *****************************************************
        // this is the outer center panel (will have scrollable)
        outerCenterPanel = new JPanel(new BorderLayout());
        outerCenterPanel.setBackground(new Color(255,255,255));
        scrollPane = new JScrollPane(outerCenterPanel);
        scrollPane.setBackground(new Color(255,255,255));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);

        // ArrayList that holds each ingredient panel (used for saving info)
        all_ingredient_panels = new ArrayList<>();

        // holds every row of ingredient panels
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0, 1));

        // holds the container of ingredient panels
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(7,10,7,10);
        gbc.weighty = 1;
        gbc.weightx = 1;

        // add the container to the center panel and add the center panel to the outerCenterPanel
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(247, 253, 246));
        centerPanel.add(containerPanel, gbc);
        outerCenterPanel.add(centerPanel);

        // ***********************************
        // Bottom panel for navigation buttons
        btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setBackground(new Color(255,255,255));
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(768,72));
        bottomPanel.setBackground(new Color(255,255,255));
        bottomPanel.add(btnPanel, SwingConstants.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // reset these constraints for the navigation buttons
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 1;
        gbc.weightx = 1;

        // back button
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(244, 42));
        backBtn.setForeground(new Color(255,255,255));
        backBtn.setBackground(new Color(28, 31, 46));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(emptyBorder);
        backBtn.addActionListener(e -> serviceDispatcher.gotoHome());

        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backBtn.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                backBtn.setBackground(new Color(28, 31, 46));
            }
        });


        // save button
        saveBtn = new JButton("Save");
        saveBtn.setPreferredSize(new Dimension(244, 42));
        saveBtn.setForeground(new Color(255,255,255));
        saveBtn.setBackground(new Color(28, 31, 46));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(emptyBorder);
        saveBtn.addActionListener(e -> serviceDispatcher.gotoHome());

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveBtn.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                saveBtn.setBackground(new Color(28, 31, 46));
            }
        });

        // add the buttons to the button panel
        btnPanel.add(backBtn, gbc);
        btnPanel.add(saveBtn, gbc);
    }


    /**
     * Dynamically adds an ingredient row
     */
    private void addIngredientRow(IngredientDisplayObject ingDO) {
        // determine if we need to auto-populate the row's contents
        String ing_name = "";
        String ing_qty = "";
        String ing_unit = "";

        if (ingDO != null) {
            ing_name = ingDO.getName();
            ing_qty = String.valueOf(ingDO.getQuantity());
            ing_unit = ingDO.getUnitOfMeasure();
        }

        // create a new panel for additional ingredient
        ingredientPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        ingredientPanel.setBackground(new Color(247, 253, 246));
        ingredientPanel.setBorder(new EmptyBorder(15,0,15,0)); // adding padding between each ingredient panel

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setText(ing_qty);
        quantity.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        quantity.setPreferredSize(new Dimension(3, 5));
        quantity.setHorizontalAlignment(JTextField.CENTER);

        // create the unit_of_measure button
        JLabel ing_units = new JLabel();
        ing_units.setText(ing_unit);
        ing_units.setPreferredSize(new Dimension(122, 42)); // this set the size of all following buttons
        ing_units.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        ing_units.setForeground(new Color(255,255,255));
        ing_units.setBackground(new Color(28, 31, 46));
        ing_units.setOpaque(true);
        ing_units.setHorizontalAlignment(SwingConstants.CENTER);
        ing_units.setBorder(emptyBorder);

        // create the combo box here.
        // although this is the first component in a row, it needs to be created after the unit and quantity
        // components as we want to update the text in those components dynamically.
        JComboBox ing_list = new JComboBox(getIngredientsList());
        ing_list.setSelectedItem(ing_name);
        ((JLabel)ing_list.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ing_list.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        ing_list.addActionListener(e -> {
            for (IngredientDisplayObject i : system_ingredients) {
                if (i.getName().equals(ing_list.getSelectedItem())) {
                    ing_units.setText(i.getUnitOfMeasure());
                    break;
                } else {
                    quantity.setText("");
                    ing_units.setText("");
                }
            }
        });

        // create the add button
        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        addButton.setForeground(new Color(255,255,255));
        addButton.setBackground(new Color(28, 31, 46));
        addButton.setFocusPainted(false);
        addButton.setBorder(emptyBorder);
        addButton.addActionListener(e -> {
            // limit the number of rows based on total number of system ingredients
            if (possessedIngredients < system_ingredients.size()) {
                addIngredientRow(null);
                possessedIngredients++;
            }
        });

        addButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addButton.setForeground(new Color(255,255,255));
                addButton.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                addButton.setForeground(new Color(255,255,255));
                addButton.setBackground(new Color(28, 31, 46));
            }
        });

        // create the sub button
        JButton subButton = new JButton("-");
        subButton.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        subButton.setForeground(new Color(255,255,255));
        subButton.setBackground(new Color(28, 31, 46));
        subButton.setFocusPainted(false);
        subButton.setBorder(emptyBorder);
        subButton.addActionListener(e -> removeIngredientRow(e));

        // first ingredient row cannot be deleted
        if (isFirstIngredient) {
            subButton.setEnabled(false);
            subButton.setBackground(new Color(94,94,94));
            isFirstIngredient = false;
        }
        else {
            subButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    subButton.setForeground(new Color(255, 255, 255));
                    subButton.setBackground(new Color(248, 68, 149));
                }

                public void mouseExited(MouseEvent e) {
                    subButton.setForeground(new Color(255, 255, 255));
                    subButton.setBackground(new Color(28, 31, 46));
                }
            });
        }

        // add everything to the newly created panel
        ingredientPanel.add(ing_list);
        ingredientPanel.add(quantity);
        ingredientPanel.add(ing_units);
        ingredientPanel.add(addButton);
        ingredientPanel.add(subButton);

        // add this new ingredient panel to the container panel
        containerPanel.add(ingredientPanel);

        // add this ingredient panel to the list containing all of them
        all_ingredient_panels.add(ingredientPanel);

        // dynamically update the frame
        // (added row will show immediately)
        this.revalidate();
        this.repaint();
        this.validate();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }


    /**
     * Removes the corresponding ingredient row when (-) button is clicked
     *
     * @param e the source of click whose panel will be removed
     */
    private void removeIngredientRow(ActionEvent e) {
        // remove the ingredientPanel containing the sub button that was pressed
        containerPanel.remove((((JButton)e.getSource()).getParent()));

        // remove the ingredientPanel from the list of all ingredient panels
        all_ingredient_panels.remove((((JButton)e.getSource()).getParent()));

        // decrement the user's possessed ingredients
        possessedIngredients--;

        // dynamically update the frame
        this.revalidate();
        this.repaint();
    }


    /**
     * Returns a String[] containing all of the system's ingredient's names
     *
     * @return a string array containing the list of ingredients available in iCook
     */
    private String[] getIngredientsList() {
        // create a new String array that includes all of the systems ingredients
        String [] ing_display = new String[system_ingredients.size()+1];

        // first selection should be ""
        ing_display[0] = "";

        // populate the ingredients
        for (int i = 1; i < ing_display.length; i++)
            ing_display[i] = system_ingredients.get(i-1).getName();

        // since we are starting at 1, we need to get the last sys_ing into the String array
        ing_display[ing_display.length-1] = system_ingredients.get(system_ingredients.size()-1).getName();

        return ing_display;
    }
}
