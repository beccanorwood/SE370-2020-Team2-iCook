package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.IngredientDisplayObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * User interface for user's to view their inventory.
 *
 * @author Team 2
 * @version 04/29/2021
 */
public class InventoryUI extends JPanel {
    // instance variables
    private JPanel inventoryContainerPanel;
    private JPanel innerInventoryPanel;
    private JPanel main_center_Panel;
    private JPanel btnPanel;
    private JButton saveBtn;
    private JButton backBtn;
    private JScrollPane scrollPane; // Scroll Panel
    private JPanel containerPanel;  // Panel that holds row of buttons to add ingredients
    private GridBagConstraints gbc; //Constraints used to ingredient rows

    private ArrayList<JPanel> all_ingredient_panels;
    private JPanel ingredientPanel;
    private boolean isFirstIngredient = true; // there should always be at least 1 row
    private int possessedIngredients; // used to stop user from adding rows when all ingredients are in inventory

    // data to used within the UI
    private ServiceDispatcher serviceDispatcher;
    private ArrayList<IngredientDisplayObject> system_ingredients;
    private ArrayList<IngredientDisplayObject> user_ingredients;
    private ArrayList<IngredientDisplayObject> all_ingredients_to_save; // used when sending info to serviceDispatcher

    // styling
    private Border emptyBorder = BorderFactory.createEmptyBorder(); // btn formatting


    /**
     * Constructor
     */
    public InventoryUI() {
        // initialize instance variables here
        serviceDispatcher = new ServiceDispatcher();
        system_ingredients = serviceDispatcher.getSystemIngredientDisplayObjects();
        user_ingredients = serviceDispatcher.getUserInventory();
        possessedIngredients = 0;

        // initialize the panel's contents
        initialize();

        // add an ingredient row for each ingredient the user possesses
        if (!user_ingredients.isEmpty()) {
            for (IngredientDisplayObject ingDO : user_ingredients)
                addIngredientRow(ingDO);
        } else {
            addIngredientRow(null); // add 1 empty row if their inventory is empty
        }

        scrollPane.getVerticalScrollBar().setValue(0);  // make the scrollbar set to the top when page
        this.setVisible(true);  // set this panel to be visible
    }


    /**
     * Initializes the panel's contents
     */
    public void initialize() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 255));

        // ArrayList that holds each ingredient panel (used for saving info)
        all_ingredient_panels = new ArrayList<>();

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7,10,7,10);

        //Top panel attributes and creation
        JLabel title;
        JPanel titlePanel;
        JLabel instructionsTitle;

        title = new JLabel("Ingredient Inventory");
        title.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        instructionsTitle = new JLabel("Click '+' to add an ingredient to your inventory");
        instructionsTitle.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        instructionsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsTitle.setForeground(new Color(51,51,51));

        // ************************************
        // this is the header (top panel)
        titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBorder(new EmptyBorder(15,0,0,0));
        titlePanel.setBackground(new Color(255,255,255));
        titlePanel.add(title);
        titlePanel.add(instructionsTitle);
        this.add(titlePanel, BorderLayout.NORTH);

        // ************************************
        // holds every row of ingredient panels
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0,1,5,5));
        containerPanel.setBackground(new Color(247, 253, 246));

        // Panel that holds container panel
        innerInventoryPanel = new JPanel(new BorderLayout());
        innerInventoryPanel.setBackground(new Color(247, 253, 246));
        innerInventoryPanel.add(containerPanel, BorderLayout.NORTH);

        // holds the innerInventoryPanel
        inventoryContainerPanel = new JPanel();
        inventoryContainerPanel.setBackground(new Color(247, 253, 246));
        inventoryContainerPanel.setLayout(new BoxLayout(inventoryContainerPanel, BoxLayout.Y_AXIS));
        inventoryContainerPanel.add(innerInventoryPanel, gbc);

        // make the inventory panel scrollable
        scrollPane = new JScrollPane(inventoryContainerPanel);
        scrollPane.setPreferredSize(new Dimension(824,550));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // contains all center components
        main_center_Panel = new JPanel(new GridBagLayout());
        main_center_Panel.setBackground(new Color(255,255,255));
        main_center_Panel.add(scrollPane, gbc); // add scrollable to the main center panel
        this.add(main_center_Panel, BorderLayout.CENTER);

        // ***********************************
        // Bottom panel for navigation buttons
        btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 211, 8));
        btnPanel.setBackground(new Color(255,255,255));
        btnPanel.setBorder(new EmptyBorder(0,0,15,0));
        this.add(btnPanel, BorderLayout.SOUTH);

        // back button
        backBtn = new JButton("Back");
        backBtn.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        backBtn.setPreferredSize(new Dimension(244, 40));
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
        saveBtn.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        saveBtn.setPreferredSize(new Dimension(244, 40));
        saveBtn.setForeground(new Color(255,255,255));
        saveBtn.setBackground(new Color(28, 31, 46));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(emptyBorder);

        // if user hits save button, send data to serviceDispatcher
        saveBtn.addActionListener(e -> {
            // populate all_ingredients_to_save
            getIngredientsToSave(all_ingredient_panels);

            // send data to serviceDispatcher
            serviceDispatcher.updateUserInventory(all_ingredients_to_save);
            serviceDispatcher.gotoInventory();
        });

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
        btnPanel.add(backBtn);
        btnPanel.add(saveBtn);
    }


    /**
     * Dynamically adds an ingredient row
     */
    private void addIngredientRow(IngredientDisplayObject ingDO) {
        // increment the user's possession number
        possessedIngredients++;

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
        ingredientPanel.setPreferredSize(new Dimension(300, 50));
        ingredientPanel.setBackground(new Color(247, 253, 246));
        ingredientPanel.setBorder(new EmptyBorder(10,10,10,10)); // adding padding between each ingredient panel

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setText(ing_qty);
        quantity.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        quantity.setHorizontalAlignment(JTextField.CENTER);

        // create the unit_of_measure button
        JLabel ing_units = new JLabel();
        ing_units.setText(ing_unit);
        ing_units.setPreferredSize(new Dimension(122, 42)); // this set the size of all following buttons
        ing_units.setFont(new Font("Century Gothic", Font.PLAIN, 14));
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
        ing_list.setFont(new Font("Century Gothic", Font.PLAIN, 14));
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
        addButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        addButton.setForeground(new Color(255,255,255));
        addButton.setBackground(new Color(28, 31, 46));
        addButton.setFocusPainted(false);
        addButton.setBorder(emptyBorder);
        addButton.addActionListener(e -> {
            // limit the number of rows based on total number of system ingredients
            if (possessedIngredients < system_ingredients.size()) {
                addIngredientRow(null); // add a new empty row
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
        subButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        subButton.setForeground(new Color(255,255,255));
        subButton.setBackground(new Color(28, 31, 46));
        subButton.setFocusPainted(false);
        subButton.setBorder(emptyBorder);
        subButton.addActionListener(this::removeIngredientRow);

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


    /**
     * Initializes and populates all_ingredients_to_save with every ingredient on this GUI
     *
     * @param all_ingredient_panels the ArrayList containing all of our ingredient panels
     */
    private void getIngredientsToSave(ArrayList<JPanel> all_ingredient_panels) {
        // list we will be populating for the serviceDispatcher
        all_ingredients_to_save = new ArrayList<>();

        // loop over each ingredient panel
        panel_loop:
        for (JPanel ingredient_panel : all_ingredient_panels) {
            // get the ingredient panel's components
            Component[] components = ingredient_panel.getComponents();

            // info we need from the ingredient panel's components
            IngredientDisplayObject ing = null;

            // for each component, get the ingredient name and its quantity
            for (Component component : components) {
                // current component is the JComboBox
                if (component instanceof JComboBox) {
                    String selected_ing = (String) ((JComboBox<?>) component).getSelectedItem();

                    // match the selected ingredient from the list of system ingredients to get the object
                    for (IngredientDisplayObject system_ingredient : system_ingredients) {
                        if (system_ingredient.getName().equals(selected_ing)) {
                            ing = system_ingredient;
                            break;
                        }
                    }
                }
                // current component is the JTextField, get the quantity
                else if (component instanceof JTextField) {
                    // if the qty is not a number, do not add it to the list of ingredients and skip to next panel
                    try {
                        assert ing != null;
                        ing.setQuantity(Integer.parseInt(((JTextField) component).getText()));
                    } catch (NumberFormatException exp) {
                        continue panel_loop;
                    }
                }
            } // end of components for loop

            // make sure we are not adding any duplicates
            for (IngredientDisplayObject user_ing : all_ingredients_to_save) {
                assert ing != null;
                if (user_ing.getName().equals(ing.getName())) {
                    continue panel_loop;
                }
            }

            all_ingredients_to_save.add(ing);

        } // end of panel_loop

    } // end of function


}
