package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * User interface for Admins to modify existing (or new) recipes
 *
 * @author Team 2
 * @version 04/25/2021
 */
public class ModifyRecipeUI extends JPanel {
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
    private JButton submitBtn;

    private JPanel ingredientPanel;             // Panel that holds the 4 elements in bottom rows for ingredients
    private ArrayList<JPanel> all_ingredient_panels;

    private boolean isFirstIngredient = true;   // at least 1 ingredient is required for all recipes (1st panel cannot be removed)
    private int numOfIngredients;               // keeps track of how many ingredient rows we have

    private ArrayList<IngredientDisplayObject> system_ingredients;  // list of all system ingredients
    private JComboBox ing_list; // combo box containing all of the system's ingredients

    private RecipeDisplayObject recipe;                             // used if the admin is modifying an existing recipe
    private ArrayList<IngredientDisplayObject> recipe_ingredients;  // used if the admin is modifying an existing recipe


    /**
     * Constructor - for brand new recipe
     */
    public ModifyRecipeUI() {
        // initialize instance variables here
        serviceDispatcher = new ServiceDispatcher();    // create the ServiceDispatcher
        system_ingredients = serviceDispatcher.getSystemIngredientDisplayObjects(); // get the list of system ingredients
        all_ingredient_panels = new ArrayList<>();

        this.setLayout(new BorderLayout());

        // set up the container panel
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0,1)); //Panel for Buttons on bottom to dynamically add/remove

        // ****************************
        // Main Panel Set Up - BLANK
        // ****************************
        setupMainPanel_blank();

        // *************************
        // 1st Ingredient Panel
        // *************************
        addIngredientRow();

        // ********************************************
        // Set frame visible at end to show all changes
        // ********************************************
        this.setVisible(true);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
    }


    /**
     * Constructor - 1 parameter
     *
     * @param recipeID id of the selected recipe to modify/edit
     */
    public ModifyRecipeUI(int recipeID) {
        // initialize instance variables here
        serviceDispatcher = new ServiceDispatcher();    // create the ServiceDispatcher
        system_ingredients = serviceDispatcher.getSystemIngredientDisplayObjects(); // get the list of system ingredients
        recipe = serviceDispatcher.getRecipeDisplayObject(recipeID);    // get the recipe display object
        recipe_ingredients = recipe.getIngredients();   // get the recipe's ingredient list
        all_ingredient_panels = new ArrayList<>();

        this.setLayout(new BorderLayout());

        // set up the container panel
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0,1)); //Panel for Buttons on bottom to dynamically add/remove

        // ****************************
        // Main Panel Set Up - FILLED
        // ****************************
        setupMainPanel_filled();

        // ******************************
        // n number of Ingredient Panels
        // ******************************
        for (IngredientDisplayObject ing : recipe.getIngredients())
            addRecipeIngredientRow(ing);

        // ********************************************
        // Set frame visible at end to show all changes
        // ********************************************
        this.setVisible(true);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
    }


    /**
     * Initializes the frame's contents - BLANK
     */
    private void setupMainPanel_blank() {
        //************************************
        // Initialize variables for main panel
        //************************************
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

        instrTxtField = new JTextArea(12, 30);
        instrTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        JScrollPane txtScroll = new JScrollPane(instrTxtField);
        txtScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //*******************************************
        // Container Panel(Main Panel) initialization
        //*******************************************
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7,10,7,10);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.decode("#23272A"));
        mainPanel.setSize(1024, 768);

        centerPanel = new JPanel(new GridBagLayout());

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
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceDispatcher.gotoManageRecipesUI();
            }
        });
        centerPanel.add(backBtn, gbc);

        centerPanel.setBackground((Color.decode("#23272A")));
        mainPanel.add(centerPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    /**
     * Initializes the frame's contents - FILLED
     */
    private void setupMainPanel_filled() {
        //************************************
        // Initialize variables for main panel
        //************************************
        nameLabel = new JLabel("Recipe:");
        nameLabel.setText("Recipe");
        nameLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        nameLabel.setForeground(Color.decode("#ffffff"));

        nameTxtField = new JTextField(30);
        nameTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        nameTxtField.setPreferredSize(new Dimension(30, 40));
        nameTxtField.setText(recipe.getName());

        instrLabel = new JLabel("Instructions:");
        instrLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        instrLabel.setForeground(Color.decode("#ffffff"));

        instrTxtField = new JTextArea(12, 30);
        instrTxtField.setFont(new Font("Helvetica", Font.PLAIN, 20));
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
        mainPanel.setBackground(Color.decode("#23272A"));
        mainPanel.setSize(1024, 768);

        centerPanel = new JPanel(new GridBagLayout());

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
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceDispatcher.gotoManageRecipesUI();
            }
        });
        centerPanel.add(backBtn, gbc);

        centerPanel.setBackground((Color.decode("#23272A")));
        mainPanel.add(centerPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);
    }


    /**
     * Dynamically adds an ingredient row
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
        // first ingredient row cannot be deleted
        if (isFirstIngredient) {
            subButton.setEnabled(false);
            isFirstIngredient = false;
        }

        // create the combo box here
        createComboBox();

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(3, 5));
        quantity.setHorizontalAlignment(JTextField.CENTER);

        // add everything to the newly created panel
        ingredientPanel.add(addButton);
        ingredientPanel.add(subButton);
        ingredientPanel.add(ing_list);
        ingredientPanel.add(quantity);

        //Empty border that adds padding between ever new ingredient row
        ingredientPanel.setBorder(new EmptyBorder(5,0,5,0));

        // add everything to the main panel
        containerPanel.add(ingredientPanel);

        //Position "add ingredient" panel to be just underneath the instruction text area
        gbc.gridx = 1;
        gbc.gridy = 3;

        centerPanel.add(containerPanel, gbc);
        mainPanel.add(centerPanel);

        numOfIngredients++;

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
     * @param ingredient the ingredient to be displayed in this row
     */
    private void addRecipeIngredientRow(IngredientDisplayObject ingredient) {
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

        // first ingredient row cannot be deleted
        if (isFirstIngredient) {
            subButton.setEnabled(false);
            isFirstIngredient = false;
        }

        // create the combo box here
        createComboBox();
        ing_list.setSelectedItem(ingredient.getName()); // set the ingredient to already be populated in this drop down

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(3, 5));
        quantity.setHorizontalAlignment(JTextField.CENTER);
        quantity.setText(String.valueOf(ingredient.getQuantity())); // set the quantity of this ingredient

        // add everything to the newly created panel
        ingredientPanel.add(addButton);
        ingredientPanel.add(subButton);
        ingredientPanel.add(ing_list);
        ingredientPanel.add(quantity);

        //Empty border that adds padding between ever new ingredient row
        ingredientPanel.setBorder(new EmptyBorder(5,0,5,0));

        // add everything to the main panel
        containerPanel.add(ingredientPanel);

        //Position "add ingredient" panel to be just underneath the instruction text area
        gbc.gridx = 1;
        gbc.gridy = 3;

        centerPanel.add(containerPanel, gbc);
        mainPanel.add(centerPanel);

        numOfIngredients++;

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
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (publishedButton.isSelected()) {

                } else {
                    // things we need to send to the db
                    String recipe_name = nameTxtField.getText();
                    String recipe_instructions = instrTxtField.getText();
                    boolean recipe_status = publishedButton.isSelected();

                    // loop every ingredient panel we have
                    for (int i = 0; i < all_ingredient_panels.size(); i++) {
                        // get every panels component
                        Component[] components = all_ingredient_panels.get(i).getComponents();

                        for (Component c : components) {
                            // if the current component is the JComboBox, print the selected ingredient.
                            if (c instanceof JComboBox) {
                                System.out.println(((JComboBox<?>) c).getSelectedItem());
                            }
                            // if the current component is the JTextField, print the quantity.
                            else if (c instanceof JTextField) {
                                System.out.println(((JTextField) c).getText());
                            }
                        } // end of for loop (components)

                    } // end of for loop (all_ingredient_panels)
                }
            }
        });

        centerPanel.add(submitBtn, gbc);
    }


    /**
     * Defines the radio button group used in the center panel
     */
    private void createRadioButtons() {
        radioBtnGroup = new ButtonGroup();

        gbc.gridx = 2;
        gbc.gridy = 0;
        publishedButton = new JRadioButton("Published");
        publishedButton.setBackground((Color.decode("#23272A")));
        publishedButton.setForeground(Color.decode("#ffffff"));
        radioBtnGroup.add(publishedButton);
        centerPanel.add(publishedButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        notPublishedButton = new JRadioButton("Not Published");
        notPublishedButton.setBackground((Color.decode("#23272A")));
        notPublishedButton.setForeground(Color.decode("#ffffff"));
        radioBtnGroup.add(notPublishedButton);
        centerPanel.add(notPublishedButton, gbc);

        // if this is a new recipe being created, the recipe is not published automatically
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
