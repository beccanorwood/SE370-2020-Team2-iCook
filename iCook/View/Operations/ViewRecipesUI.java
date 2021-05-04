package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.IngredientDisplayObject;
import iCook.View.Operations.DisplayObjects.RecipeDisplayObjectIF;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * User interface for user's to view recipes they can cook based on their inventory. Users can click on a recipe they
 * wish to create and view its instructions. If a user does not have a sufficient inventory for any recipes, a message will be displayed
 *
 * @author Team 2
 * @version 5/3/2021
 */
public class ViewRecipesUI extends JPanel implements ActionListener
{
    // instance variables
    private GridBagConstraints gbc;
    private GridBagConstraints gbc2;

    private JPanel toppanel;
    private JLabel iCook;

    private JPanel recipe_panel;
    private JScrollPane recipe_scrollable;

    private JPanel center_panel;
    private JScrollPane center_scrollable;
    private JLabel ingredients_label;
    private JLabel instructions_label;
    private JTextArea ingredients;
    private JTextArea instructions;

    private JPanel bottom_panel;
    private JButton[] recipesBtn;

    private JButton modifyRecipe;
    private JButton cancel;
    private JButton save;
    private RecipeDisplayObjectIF selectedRecipe;

    private ServiceDispatcher serviceDispatcher;

    private ArrayList<RecipeDisplayObjectIF> satisfiedRecipes;
    private int numOfRecipes;

    private Border emptyBorder = BorderFactory.createEmptyBorder();

    public ViewRecipesUI() {
        // use the service dispatcher to get the recipe list for the logged in user
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());
        satisfiedRecipes = serviceDispatcher.getSatisfiedRecipes();

        // ************************************************************
        // *** Setting up the text fields & labels for center panel ***
        // ***    and setting up the frame for this GUI    **
        // ************************************************************

        //Ingredient Label
        ingredients_label = new JLabel("INGREDIENTS");
        ingredients_label.setFont(new Font("Century Gothic", Font.BOLD, 25));
        ingredients_label.setVisible(false);
        ingredients_label.setBackground(new Color(246,251,253));
        ingredients_label.setForeground(new Color(51,51,51));


        //Ingredient Text Area
        ingredients = new JTextArea();
        ingredients.setEditable(false);
        ingredients.setFont(new Font("Century Gothic", Font.PLAIN, 22));
        ingredients.setVisible(false);
        ingredients.setBackground(new Color(246,251,253));
        ingredients.setForeground(new Color(51,51,51));


        //Instructions Label
        instructions_label = new JLabel("INSTRUCTIONS");
        instructions_label.setFont(new Font("Century Gothic", Font.BOLD, 25));
        instructions_label.setVisible(false);
        instructions_label.setBackground(new Color(246,251,253));
        instructions_label.setForeground(new Color(51,51,51));

        //Instructions Text Area
        instructions = new JTextArea();
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setEditable(false);
        instructions.setFont(new Font("Century Gothic", Font.PLAIN, 22));
        instructions.setBackground(new Color(246,251,253));
        instructions.setForeground(new Color(51,51,51));



        instructions.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                instructions.getCaret().setVisible(true);
                instructions.getCaret().setSelectionVisible(true);
                save.setEnabled(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                instructions.getCaret().setVisible(false);
                instructions.getCaret().setSelectionVisible(false);
            }
        });

        // ***********************************
        // *** Top panel is worked on here ***
        // ***********************************

        iCook = new JLabel("Recipes in your market");
        iCook.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        iCook.setForeground(new Color(51,51,51));

        // populate the header
        toppanel = new JPanel();
        toppanel.setBackground(new Color(255,255,255));
        toppanel.add(iCook);

        // *********************************************
        // *** Recipe panel & buttons worked on here ***
        // *********************************************

        // numOfRecipes will be the size of the array returned from the controller containing recipes
        if(satisfiedRecipes == null || satisfiedRecipes.isEmpty())
            numOfRecipes = 0;
        else
            numOfRecipes = satisfiedRecipes.size();

        // create the recipe panel (this stores all recipe buttons)
        recipe_panel = new JPanel(new GridBagLayout());
        recipe_panel.setBackground(new Color(234, 246, 248));

        // this is the bounds for where the button will be added in the left panel
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // create an array of buttons
        recipesBtn = new JButton[numOfRecipes];

        // populate the left scrollable with recipe buttons
        for(int i = 0; i < numOfRecipes; i++)
        {
            recipesBtn[i] = new JButton();
            recipesBtn[i].setText(satisfiedRecipes.get(i).getName());
            recipesBtn[i].addActionListener(this);
            recipesBtn[i].setFont(new Font("Century Gothic", Font.PLAIN, 16));
            recipesBtn[i].setPreferredSize(new Dimension(200,32));
            recipesBtn[i].setHorizontalAlignment(JButton.CENTER);
            recipesBtn[i].setForeground(new Color(255,255,255));
            recipesBtn[i].setBackground(new Color(28, 31, 46));
            recipesBtn[i].setFocusPainted(false);
            recipesBtn[i].setBorder(emptyBorder);

            recipesBtn[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    e.getComponent().setForeground(new Color(255,255,255));
                    e.getComponent().setBackground(new Color(68, 166, 154));
                }
                public void mouseExited(MouseEvent e){
                    e.getComponent().setForeground(new Color(255,255,255));
                    e.getComponent().setBackground(new Color(28, 31, 46));
                }
            });


            recipe_panel.add(recipesBtn[i], gbc);
            gbc.gridy++;
        }

        // **************************************
        // *** Center panel is worked on here ***
        // **************************************

        // set the center panel
        center_panel = new JPanel(new GridBagLayout());
        gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.WEST;
        gbc2.insets = new Insets(5,5,5,5);
        gbc2.gridy = 0;
        center_panel.setBackground(new Color(246,251,253));


        //Add labels & text areas to center panel
        center_panel.add(ingredients_label, gbc2);
        gbc2.gridy++;
        center_panel.add(ingredients, gbc2);
        gbc2.gridy++;
        center_panel.add(instructions_label, gbc2);
        gbc2.gridy++;
        center_panel.add(instructions, gbc2);



        // **************************************
        // *** Bottom panel is worked on here ***
        // **************************************

        // create button to go back to home page
        JButton home = new JButton("Home");
        home.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        home.setPreferredSize(new Dimension(144,32));
        home.setForeground(new Color(255,255,255));
        home.setBackground(new Color(28, 31, 46));
        home.setFocusPainted(false);
        home.setBorder(emptyBorder);

        home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                home.setForeground(new Color(255,255,255));
                home.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                home.setForeground(new Color(255,255,255));
                home.setBackground(new Color(28, 31, 46));
            }
        });

        // create button to go to inventory page
        JButton inv = new JButton("Inventory");
        inv.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        inv.setPreferredSize(new Dimension(144,32));
        inv.setForeground(new Color(255,255,255));
        inv.setBackground(new Color(28, 31, 46));
        inv.setFocusPainted(false);
        inv.setBorder(emptyBorder);

        inv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                inv.setForeground(new Color(255,255,255));
                inv.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                inv.setForeground(new Color(255,255,255));
                inv.setBackground(new Color(28, 31, 46));
            }
        });

        modifyRecipe = new JButton("Modify Recipe");
        modifyRecipe.setFont(new Font("Century Gothic", Font.PLAIN, 16));
        modifyRecipe.setPreferredSize(new Dimension(144,32));
        modifyRecipe.setVisible(false);
        modifyRecipe.setForeground(new Color(255,255,255));
        modifyRecipe.setBackground(new Color(28, 31, 46));
        modifyRecipe.setFocusPainted(false);
        modifyRecipe.setBorder(emptyBorder);

        modifyRecipe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                modifyRecipe.setForeground(new Color(255,255,255));
                modifyRecipe.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                modifyRecipe.setForeground(new Color(255,255,255));
                modifyRecipe.setBackground(new Color(28, 31, 46));
            }
        });

        save = new JButton("Save");
        save.setFont(new Font("Helvetica", Font.PLAIN, 16));
        save.setPreferredSize(new Dimension(144,32));
        save.setEnabled(false);
        save.setVisible(false);
        save.setForeground(new Color(255,255,255));
        save.setBackground(new Color(28, 31, 46));
        save.setFocusPainted(false);
        save.setBorder(emptyBorder);

        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                save.setForeground(new Color(255,255,255));
                save.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                save.setForeground(new Color(255,255,255));
                save.setBackground(new Color(28, 31, 46));
            }
        });

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Helvetica", Font.PLAIN, 16));
        cancel.setPreferredSize(new Dimension(144,32));
        cancel.setVisible(false);
        cancel.setForeground(new Color(255,255,255));
        cancel.setBackground(new Color(28, 31, 46));
        cancel.setFocusPainted(false);
        cancel.setBorder(emptyBorder);

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancel.setForeground(new Color(255,255,255));
                cancel.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                cancel.setForeground(new Color(255,255,255));
                cancel.setBackground(new Color(28, 31, 46));
            }
        });

        // make them work
        home.addActionListener(this);
        inv.addActionListener(this);
        modifyRecipe.addActionListener(this);
        save.addActionListener(this);
        cancel.addActionListener(this);

        // set the bottom panel to contain the navigation buttons
        bottom_panel = new JPanel();
        bottom_panel.setBackground(new Color(255,255,255));
        bottom_panel.add(home);
        bottom_panel.add(inv);
        bottom_panel.add(modifyRecipe);
        bottom_panel.add(save);
        bottom_panel.add(cancel);

        // **********************************
        // *** Adding all panels to frame ***
        // **********************************

        // add the header to the frame
        this.add(toppanel, BorderLayout.NORTH);

        // add the left scroll panel to the frame
        recipe_scrollable = new JScrollPane(recipe_panel);
        recipe_scrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        recipe_scrollable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        recipe_scrollable.setBackground(new Color(234, 246, 248));
        this.add(recipe_scrollable, BorderLayout.WEST);

        // add the center scroll panel to the frame
        center_scrollable = new JScrollPane(center_panel);
        center_scrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        center_scrollable.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(center_scrollable, BorderLayout.CENTER);

        // add the navigation buttons to the frame
        this.add(bottom_panel, BorderLayout.SOUTH);

        // set frame to visible
        this.setVisible(true);

        // ***************************
        // *** Error handling here ***
        // ***************************

        // if no available recipes, display an error message
        if(numOfRecipes < 1){
            instructions.setText("We cannot find any recipes given your inventory! " +
                                "\nAdd more ingredients to your inventory to get recipes!");
            instructions.setSize(740, 900);
            instructions.setForeground(new Color(51,51,51));
            center_panel.setAlignmentY(Component.CENTER_ALIGNMENT);
            center_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            center_panel.setVisible(true);

        }

        // else, let the user they need to click on a recipe button to view its details
        else {
            instructions.setText("Click on a recipe to get started!");
            instructions.setSize(740, 900);
            center_panel.setAlignmentY(Component.CENTER_ALIGNMENT);
            center_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            center_panel.setVisible(true);
        }
    }


    private void ModifyRecipe(String button)
    {
        // get the og instructions from db
        String modifiedInstructions = instructions.getText();

        // Logo for JoptionPane Window
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("iCook_Logo(125).png"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


        /*
        Checks if recipe instructions in text field is different than instructions
        from corresponding recipe in DB
         */
        if(!modifiedInstructions.equals(selectedRecipe.getInstructions())) {

            if(button.equals("Save"))
            {
                //Add modified recipe to user's Recipe page
                //New recipe display object for user is created and displayed on left panel
                String tempRecipe = (String) JOptionPane.showInputDialog(this, "Enter new recipe name", "New Recipe", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(img), null, null);
                if (tempRecipe!=null && !tempRecipe.equals("")) {
                    SaveNewRecipe(tempRecipe, modifiedInstructions);
                }
            }
            else if(button.equals("Cancel"))
            {
                Object[] options = {"Continue Editing", "Discard Changes"};

                int result = JOptionPane.showOptionDialog(null, "You have unsaved changes." +
                                                         "\nDo you wish to continue editing?", "iCook",
                                                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                            new ImageIcon(img), options, options[0]);

                if(result == JOptionPane.YES_OPTION)
                {
                    instructions.setEditable(true);
                    save.setVisible(true);
                    modifyRecipe.setVisible(true);
                    cancel.setVisible(true);
                    ingredients.setBackground(new Color(226, 220, 236));
                    instructions.setBackground(new Color(226, 220, 236));
                    center_panel.setBackground(new Color(226, 220, 236));
                }

                else
                {
                    save.setVisible(false);
                    cancel.setVisible(false);
                    modifyRecipe.setVisible(false);
                    instructions.setEditable(false);
                    instructions.getCaret().setVisible(false);
                    instructions.getCaret().setSelectionVisible(false);
                    instructions.setBackground(new Color(246,251,253));
                    center_panel.setBackground(new Color(246,251,253));
                    ingredients.setVisible(false);
                    ingredients_label.setVisible(false);
                    instructions_label.setVisible(false);
                    instructions.setText(selectedRecipe.getInstructions());
                    instructions.select(0,0);
                    iCook.setText(selectedRecipe.getName());

                    iCook.setText("Recipes in your market");
                    instructions.setText("Click on a recipe to get started!");
                }

            }
        }
        else if (button.equals("Cancel")) {
            iCook.setText("Recipes in your market");
            instructions.setText("Click on a recipe to get started!");
        }
    }


    /**
     * Gets the RecipeDisplayObject to be cloned and requests the serviceDispatcher
     * to update the information accordingly. Refreshes this UI when done.
     *
     * @param newRecipeName the name of this cloned recipe
     * @param newRecipeInstructions the instructions of this cloned recipe
     */
    private void SaveNewRecipe(String newRecipeName, String newRecipeInstructions) {
        // get the corresponding recipe (recipe the user clicked)
        RecipeDisplayObjectIF recipe_to_clone = selectedRecipe;

        // request the serviceDispatcher to clone this recipe for the user
        serviceDispatcher.cloneRecipe(recipe_to_clone.getRecipeID(), newRecipeName, newRecipeInstructions);
        serviceDispatcher.gotoViewRecipes();
    }


    /**
     * Tells the GUI what to do when a button is pressed
     */
    public void actionPerformed(ActionEvent e)
    {
        //get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        // take user to HomeUI
        if ("Home".equals(buttonChosen)) {
            serviceDispatcher.gotoHome();

        } else if ("Inventory".equals(buttonChosen)) { // take user to InventoryUI
            serviceDispatcher.gotoInventory();


        } else if ("Modify Recipe".equals(buttonChosen)) { // makes text area with recipe instructions editable
            instructions.setEditable(true);
            iCook.setText("Modifying " + selectedRecipe.getName());
            instructions.setBackground(new Color(226, 220, 236));
            ingredients.setBackground(new Color(226, 220, 236));
            center_panel.setBackground(new Color(226, 220, 236));
            cancel.setVisible(true);
            save.setVisible(true);
            save.setEnabled(false);
        }
        else if ("Cancel".equals(buttonChosen)) {
            instructions.setEditable(false);
            modifyRecipe.setVisible(false);
            save.setVisible(false);
            cancel.setVisible(false);
            ingredients.setVisible(false);
            ingredients_label.setVisible(false);
            instructions_label.setVisible(false);
            ingredients.setBackground(new Color(246, 251, 253));
            instructions.setBackground(new Color(246, 251, 253));
            center_panel.setBackground(new Color(246, 251, 253));

            ModifyRecipe(buttonChosen);
        }

        else if ("Save".equals(buttonChosen)) {
            ModifyRecipe(buttonChosen);
        }

        // this is where we will figure out what button they pressed
        // and display the corresponding recipe's instructions
        else {
            for (int i = 0; i < recipesBtn.length; i++) {
                if (buttonChosen.equals(satisfiedRecipes.get(i).getName())) {
                    System.out.println("You pressed Button: " + satisfiedRecipes.get(i).getName());

                    selectedRecipe = satisfiedRecipes.get(i);
                    iCook.setText(selectedRecipe.getName());

                    // create the string to hold the ingredient text
                    String ingredients_text = "";

                    // populate the string's ingredient section
                    for (IngredientDisplayObject ing : selectedRecipe.getIngredients())
                        ingredients_text += ing.getQuantity() + " " + ing.getUnitOfMeasure() + " " + ing.getName() + "\n"; //text for ingredient text area


                    //String used for instructions text
                    String instructions_text = "";
                    instructions_text += selectedRecipe.getInstructions(); //retrieves recipe instructions from recipe display object


                    //Set text to change to selected recipe
                    ingredients.setText(ingredients_text);

                    // add the string to the instructions text area
                    instructions.setText(instructions_text);
                    instructions.setSize(740, 900);
                    instructions.select(0, 0);

                    //Set up the corresponding center panel text area, labels, & buttons to be set visible
                    modifyRecipe.setVisible(true);
                    ingredients_label.setVisible(true);
                    ingredients.setVisible(true);
                    instructions_label.setVisible(true);
                    instructions.setVisible(true);
                    center_panel.setAlignmentY(Component.TOP_ALIGNMENT);
                    center_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    center_panel.setVisible(true);
                }
            }
        }
    }


} // end of RecipeUI class
