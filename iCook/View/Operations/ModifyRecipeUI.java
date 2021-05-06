package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.AbstractUI;
import iCook.View.Operations.DisplayObjects.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * User interface for Admins to modify existing recipes or to create new recipes.
 *
 * @author Team 2
 * @version 5/5/2021
 */
public class ModifyRecipeUI extends AbstractUI {
    // instance variables
    private ServiceDispatcher serviceDispatcher;

    private JPanel mainPanel;       // Main panel for all elements
    private JScrollPane scrollPane; // Scroll Panel
    private JPanel centerPanel;
    private JPanel containerPanel;  // Panel that holds row of buttons to add ingredients

    private GridBagConstraints gbc;

    private JLabel nameLabel;
    private JTextField nameTxtField;
    private JLabel instrLabel;
    private JTextArea instrTxtField;

    private ButtonGroup radioBtnGroup;
    private JRadioButton publishedButton;
    private JRadioButton notPublishedButton;

    private JButton backBtn;
    private JButton saveBtn;

    private JPanel ingredientPanel;             // Panel that holds the 4 elements in bottom rows for ingredients
    private ArrayList<JPanel> all_ingredient_panels;

    private boolean isFirstIngredient = true;   // at least 1 ingredient is required for all recipes (1st panel cannot be removed)

    private ArrayList<IngredientDisplayObject> system_ingredients;  // list of all system ingredients
    private JComboBox ing_list; // combo box containing all of the system's ingredients

    private int recipeID;   // used if the admin is modifying an existing recipe
    private RecipeDisplayObject recipe; // used if the admin is modifying an existing recipe

    private Border emptyBorder = BorderFactory.createEmptyBorder();


    /**
     * Constructor
     */
    public ModifyRecipeUI() {

    }


    @Override
    public void initializePanel() {
        this.removeAll();

        // initialize instance variables here
        serviceDispatcher = new ServiceDispatcher();
        system_ingredients = serviceDispatcher.getSystemIngredientDisplayObjects();
        all_ingredient_panels = new ArrayList<>();

        // get the recipe display object
        recipe = (recipeID != 0) ? serviceDispatcher.getRecipeDisplayObject(recipeID) : null;

        // set up this panel's layout
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255,255,255));

        // set up the container panel
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0,1)); //Panel for Buttons on bottom to dynamically add/remove

        // set up the main panel and ingredient(s) accordingly
        if (recipe != null) {
            // modifying recipe
            setupMainPanel_filled();

            // set up n number of Ingredient Panels
            if (recipe.getIngredients().isEmpty()){
                addIngredientRow();
            } else {
                for (IngredientDisplayObject ing : recipe.getIngredients())
                    addRecipeIngredientRow(ing);
            }
        } else {
            // creating a new recipe
            setupMainPanel_blank();
            addIngredientRow();
        }

        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());

        this.revalidate();
        this.repaint();
        this.setVisible(true); // show the next state (panel)
    }


    /**
     * Setter for the recipeID. An ID of 0 means we are creating a new recipe.
     *
     * @param recipeID the id of the recipe we are modifying
     */
    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }


    /**
     * sets up the main panel's contents - NEW RECIPE
     */
    private void setupMainPanel_blank() {
        //************************************
        // Initialize variables for main panel
        //************************************
        nameLabel = new JLabel("Recipe:");
        nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        nameLabel.setForeground(new Color(51,51,51));

        nameTxtField = new JTextField(30);
        nameTxtField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        nameTxtField.setPreferredSize(new Dimension(30, 40));

        instrLabel = new JLabel("Instructions:");
        instrLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        instrLabel.setForeground(new Color(51,51,51));

        instrTxtField = new JTextArea(12, 30);
        instrTxtField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        instrTxtField.setLineWrap(true);
        JScrollPane txtScroll = new JScrollPane(instrTxtField);
        txtScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //*******************************************
        // Container Panel(Main Panel) initialization
        //*******************************************
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7,10,7,10);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255,255,255));
        mainPanel.setSize(1024, 768);

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255,255,255));

        //*****************************
        // Add components to main panel
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
        centerPanel.add(txtScroll, gbc);

        // add the radio button group here
        createRadioButtons();

        // add the submit button here
        createSubmitButton();

        gbc.gridx = 2;
        gbc.gridy = 1;
        backBtn = new JButton("Back");
        backBtn.setForeground(new Color(255,255,255));
        backBtn.setBackground(new Color(28, 31, 46));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(emptyBorder);
        backBtn.setPreferredSize(new Dimension(100,32));

        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backBtn.setForeground(new Color(255,255,255));
                backBtn.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                backBtn.setForeground(new Color(255,255,255));
                backBtn.setBackground(new Color(28, 31, 46));
            }
        });

        backBtn.addActionListener(e -> serviceDispatcher.updateState(nextState(AbstractUI.manageRecipesUI)));
        centerPanel.add(backBtn, gbc);

        centerPanel.setBackground(new Color(255,255,255));
        mainPanel.add(centerPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(255,255,255));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    /**
     * sets up the main panel's contents - EXISTING RECIPE
     */
    private void setupMainPanel_filled() {
        //************************************
        // Initialize variables for main panel
        //************************************
        nameLabel = new JLabel("Recipe:");
        nameLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        nameLabel.setForeground(new Color(51,51,51));

        nameTxtField = new JTextField(30);
        nameTxtField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        nameTxtField.setPreferredSize(new Dimension(30, 40));
        nameTxtField.setText(recipe.getName());

        instrLabel = new JLabel("Instructions:");
        instrLabel.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        instrLabel.setForeground(new Color(51,51,51));

        instrTxtField = new JTextArea(12, 30);
        instrTxtField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        instrTxtField.setLineWrap(true);
        instrTxtField.setText(recipe.getInstructions());
        instrTxtField.select(0,0);
        JScrollPane txtScroll = new JScrollPane(instrTxtField);
        txtScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //*******************************************
        // Container Panel(Main Panel) initialization
        //*******************************************
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7,10,7,10);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255,255,255));
        mainPanel.setSize(1024, 768);

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255,255,255));

        //*****************************
        // Add components to main panel
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
        centerPanel.add(txtScroll, gbc);

        // add the radio button group here
        createRadioButtons();

        // add the submit button here
        createSubmitButton();

        gbc.gridx = 2;
        gbc.gridy = 1;
        backBtn = new JButton("Back");
        backBtn.setForeground(new Color(255,255,255));
        backBtn.setBackground(new Color(28, 31, 46));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(emptyBorder);
        backBtn.setPreferredSize(new Dimension(100,32));

        backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                backBtn.setForeground(new Color(255,255,255));
                backBtn.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                backBtn.setForeground(new Color(255,255,255));
                backBtn.setBackground(new Color(28, 31, 46));
            }
        });

        backBtn.addActionListener(e -> serviceDispatcher.updateState(nextState(AbstractUI.manageRecipesUI)));
        centerPanel.add(backBtn, gbc);

        centerPanel.setBackground(new Color(255,255,255));
        mainPanel.add(centerPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(255,255,255));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    /**
     * Dynamically adds an ingredient row
     */
    private void addIngredientRow() {
        // create a new panel for additional ingredient
        ingredientPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        ingredientPanel.setBackground(new Color(255,255,255));

        // create the add button
        JButton addButton = new JButton("+");
        addButton.setForeground(new Color(255,255,255));
        addButton.setBackground(new Color(28, 31, 46));
        addButton.setFocusPainted(false);
        addButton.setBorder(emptyBorder);

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

        addButton.addActionListener(e -> addIngredientRow());

        // create the sub button
        JButton subButton = new JButton("-");
        subButton.setForeground(new Color(255,255,255));
        subButton.setBackground(new Color(28, 31, 46));
        subButton.setFocusPainted(false);
        subButton.setBorder(emptyBorder);

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

        subButton.addActionListener(e -> removeIngredientRow(e));

        // create the combo box here
        createComboBox();

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(3, 5));
        quantity.setHorizontalAlignment(JTextField.CENTER);

        // add everything to the newly created panel
        ingredientPanel.add(ing_list);
        ingredientPanel.add(quantity);
        ingredientPanel.add(addButton);
        ingredientPanel.add(subButton);

        //Empty border that adds padding between ever new ingredient row
        ingredientPanel.setBorder(new EmptyBorder(5,0,5,0));

        // add everything to the main panel
        containerPanel.add(ingredientPanel);

        //Position "add ingredient" panel to be just underneath the instruction text area
        gbc.gridx = 1;
        gbc.gridy = 3;

        centerPanel.add(containerPanel, gbc);
        mainPanel.add(centerPanel);

        // add this ingredient panel to the list containing all of them
        all_ingredient_panels.add(ingredientPanel);

        // dynamically update the frame
        // (added row will show immediately)
        this.revalidate();
        this.repaint();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }


    /**
     * Display a row for an existing recipe ingredient
     *
     * @param ingredient the ingredientDO to be displayed in this row
     */
    private void addRecipeIngredientRow(IngredientDisplayObject ingredient) {
        // create a new panel for additional ingredient
        ingredientPanel = new JPanel(new GridLayout(1, 5, 10, 0));
        ingredientPanel.setBackground(new Color(255,255,255));

        // create the add button
        JButton addButton = new JButton("+");
        addButton.setForeground(new Color(255,255,255));
        addButton.setBackground(new Color(28, 31, 46));
        addButton.setFocusPainted(false);
        addButton.setBorder(emptyBorder);

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

        addButton.addActionListener(e -> addIngredientRow());

        // create the sub button
        JButton subButton = new JButton("-");
        subButton.setForeground(new Color(255,255,255));
        subButton.setBackground(new Color(28, 31, 46));
        subButton.setFocusPainted(false);
        subButton.setBorder(emptyBorder);

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

        subButton.addActionListener(e -> removeIngredientRow(e));


        // create the combo box here
        createComboBox();
        ing_list.setSelectedItem(ingredient.getName()); // set the ingredient to already be populated in this drop down

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(3, 5));
        quantity.setHorizontalAlignment(JTextField.CENTER);
        quantity.setText(String.valueOf(ingredient.getQuantity())); // set the quantity of this ingredient

        // add everything to the newly created panel
        ingredientPanel.add(ing_list);
        ingredientPanel.add(quantity);
        ingredientPanel.add(addButton);
        ingredientPanel.add(subButton);

        //Empty border that adds padding between ever new ingredient row
        ingredientPanel.setBorder(new EmptyBorder(5,0,5,0));

        // add everything to the main panel
        containerPanel.add(ingredientPanel);

        //Position "add ingredient" panel to be just underneath the instruction text area
        gbc.gridx = 1;
        gbc.gridy = 3;

        centerPanel.add(containerPanel, gbc);
        mainPanel.add(centerPanel);

        // add this ingredient panel to the list containing all of them
        all_ingredient_panels.add(ingredientPanel);

        // dynamically update the frame
        // (added row will show immediately)
        this.revalidate();
        this.repaint();
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

        // dynamically update the frame
        this.revalidate();
        this.repaint();
    }


    /**
     * Defines the submit button used in the center panel
     */
    private void createSubmitButton() {
        gbc.gridx = 3;
        gbc.gridy = 1;
        saveBtn = new JButton("Save");
        saveBtn.setForeground(new Color(255,255,255));
        saveBtn.setBackground(new Color(28, 31, 46));
        saveBtn.setFocusPainted(false);
        saveBtn.setBorder(emptyBorder);
        saveBtn.setPreferredSize(new Dimension(100,32));

        saveBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveBtn.setForeground(new Color(255,255,255));
                saveBtn.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                saveBtn.setForeground(new Color(255,255,255));
                saveBtn.setBackground(new Color(28, 31, 46));
            }
        });

        // define what happens when admin clicks the submit button
        saveBtn.addActionListener(e -> {
            // create a RecipeDisplayObject to be passed to the serviceDispatcher
            RecipeDisplayObject recipeDO = createRecipeDO();

            // recipe must at least have a name to be sent to the service dispatcher
            if (!recipeDO.getName().isEmpty()) {

                // recipe exists, so UPDATE the recipe's info
                if (recipe != null) {
                    serviceDispatcher.updateRecipe(recipeDO);
                }
                // recipe does not exist, so ADD a new recipe
                else {
                    serviceDispatcher.addNewRecipe(recipeDO);
                }

                // take admin back to the ManageRecipesUI to see changes
                serviceDispatcher.updateState(nextState(AbstractUI.manageRecipesUI));
            }

            // display error message if there is no recipe name (cannot save)
            else {
                JOptionPane.showMessageDialog(this, "Recipe must have name before saving.", "Inane error", JOptionPane.ERROR_MESSAGE);
            }
        });

        centerPanel.add(saveBtn, gbc);
    }


    /**
     * Creates a RecipeDisplayObject from all of the info in this UI.
     * Used only when the submit button is clicked (when recipe is ready to be sent to serviceDispatcher)
     *
     * @return a RecipeDisplayObject created by all the UI elements
     */
    private RecipeDisplayObject createRecipeDO() {
        // info we need to send to the serviceDispatcher (needed to create a RecipeDisplayObject)
        Integer recipe_id = (recipe != null) ? recipe.getRecipeID() : 0; // if recipe exists, get its id
        String recipe_name = nameTxtField.getText();    // recipe's name
        String recipe_instructions = instrTxtField.getText();   // recipe's instructions
        boolean recipe_status = publishedButton.isSelected();   // recipe's published button
        ArrayList<IngredientDisplayObject> recipe_ings = new ArrayList<>(); // recipe's ingredients

        // loop over every ingredient panel we have added
        outerloop:
        for (JPanel ingredient_panel : all_ingredient_panels) {
            // get the current panel's list of components
            Component[] components = ingredient_panel.getComponents();

            // info we need from each panel (ingredient info)
            IngredientDisplayObject ing = null;
            int ing_qty = 0;

            // loop over each of the panel's components
            for (Component c : components) {
                // current component is the JComboBox
                if (c instanceof JComboBox) {
                    String selected_ing = (String) ((JComboBox<?>) c).getSelectedItem();

                    // match the selected ingredient from the list of system ingredients to get the object
                    for (IngredientDisplayObject system_ingredient : system_ingredients) {
                        if (system_ingredient.getName().equals(selected_ing)) {
                            ing = system_ingredient;
                            break;
                        }
                    }
                }
                // current component is the JTextField, get the quantity
                else if (c instanceof JTextField) {
                    // if the qty is not a number, do not add it to the recipe's list of ingredients
                    try {
                        ing_qty = Integer.parseInt(((JTextField) c).getText());
                    } catch (NumberFormatException exp) {
                        break outerloop;
                    }
                }

            } // end of for loop (components)

            // make sure we are not adding any duplicates
            for (IngredientDisplayObject recipe_ing : recipe_ings) {
                assert ing != null;
                if (recipe_ing.getName().contains(ing.getName())) {
                    continue outerloop;
                }
            }

            // add the current ingredient to the array list of ingredient display objects
            if (ing != null)
                recipe_ings.add(new IngredientDisplayObject(ing.getIngredientID(),
                        ing.getName(), ing.getUnitOfMeasure(), ing_qty));

        } // end of for loop (all_ingredient_panels)

        return new RecipeDisplayObject(recipe_id, recipe_name, recipe_instructions, recipe_ings, recipe_status);
    }


    /**
     * Defines the radio button group used in the center panel
     */
    private void createRadioButtons() {
        radioBtnGroup = new ButtonGroup();

        gbc.gridx = 2;
        gbc.gridy = 0;
        publishedButton = new JRadioButton("Published");
        publishedButton.setForeground(new Color(51,51,51));
        publishedButton.setBackground(new Color(255,255,255));
        radioBtnGroup.add(publishedButton);
        centerPanel.add(publishedButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        notPublishedButton = new JRadioButton("Not Published");
        notPublishedButton.setForeground(new Color(51,51,51));
        notPublishedButton.setBackground(new Color(255,255,255));
        radioBtnGroup.add(notPublishedButton);
        centerPanel.add(notPublishedButton, gbc);

        // if this is a new recipe being created, the recipe is not published
        if (recipe == null) {
            notPublishedButton.setSelected(true);
        } else {
            // set the correct radio button based on the publish status of the recipe
            if (recipe.isPublished()) {
                publishedButton.setSelected(true);
            } else {
                notPublishedButton.setSelected(true);
            }
        }

    }


    /**
     * Defines the combo box used in the center panel
     */
    private void createComboBox() {
        // get the list of ingredients and store them in a combo box
        ing_list = new JComboBox(getIngredientsList());
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


} // end of ModifyRecipeUI
