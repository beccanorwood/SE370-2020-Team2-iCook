package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.RecipeDisplayObject;

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
 * @version 04/28/2021
 */
public class ViewRecipesUI extends JPanel implements ActionListener
{
    // instance variables
    private GridBagConstraints gbc;

    private JPanel toppanel;
    private JLabel iCook;

    private JPanel recipe_panel;
    private JScrollPane recipe_scrollable;

    private JPanel center_panel;
    private JScrollPane center_scrollable;
    private JTextArea instructions;

    private JPanel bottom_panel;
    private JButton[] recipesBtn;

    private JButton modifyRecipe;
    private JButton cancel;
    private JButton save;
    private RecipeDisplayObject selectedRecipe;

    private ArrayList<JButton> newRecipes;

    private ServiceDispatcher serviceDispatcher;

    private ArrayList<RecipeDisplayObject> satisfiedRecipes;
    private int numOfRecipes;

    private Border emptyBorder = BorderFactory.createEmptyBorder();

    public ViewRecipesUI() {
        // use the service dispatcher to get the recipe list for the logged in user
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());
        satisfiedRecipes = serviceDispatcher.getSatisfiedRecipes();

        //Initialize arraylist for newly added recipes from user
        newRecipes = new ArrayList<>();

        // **************************************************
        // *** Setting up the text field for instructions ***
        // ***    and setting up the frame for this GUI    **
        // **************************************************
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
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.CENTER;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        center_panel.setBackground(new Color(246,251,253));

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

            center_panel.add(instructions, gbc2);
            center_panel.setAlignmentY(Component.CENTER_ALIGNMENT);
            center_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
            center_panel.setVisible(true);

        }

        // else, let the user they need to click on a recipe button to view its details
        else {
            instructions.setText("Click on a recipe to get started!");
            instructions.setSize(740, 900);
            center_panel.add(instructions, gbc2);
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

    private void SaveNewRecipe(String recipe, String newRecipeInstructions)
    {
        newRecipes.add(new JButton(recipe));

        for(int i = 0; i < newRecipes.size(); i++)
        {
            newRecipes.get(i).setFont(new Font("Century Gothic", Font.PLAIN, 16));
            newRecipes.get(i).setPreferredSize(new Dimension(200,32));
            newRecipes.get(i).setHorizontalAlignment(JButton.CENTER);
            newRecipes.get(i).setForeground(new Color(255,255,255));
            newRecipes.get(i).setBackground(new Color(28, 31, 46));
            newRecipes.get(i).setFocusPainted(false);
            newRecipes.get(i).setBorder(emptyBorder);

            newRecipes.get(i).addMouseListener(new MouseAdapter() {
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

            gbc.gridy++;
            recipe_panel.add(newRecipes.get(i), gbc);

            iCook.setText(recipe);
            instructions.setText(newRecipeInstructions);
        }

        instructions.setBackground(new Color(246,251,253));
        center_panel.setBackground(new Color(246,251,253));
        modifyRecipe.setVisible(false);
        cancel.setVisible(false);
        save.setVisible(false);

        this.revalidate();
        this.repaint();
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

            // take user to InventoryUI
        } else if ("Inventory".equals(buttonChosen)) {
            serviceDispatcher.gotoInventory();

            // makes text area with recipe instructions editable
        } else if ("Modify Recipe".equals(buttonChosen)) {
            instructions.setEditable(true);
            iCook.setText("Modifying " + selectedRecipe.getName());
            instructions.setBackground(new Color(226, 220, 236));
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

                    instructions.setText(satisfiedRecipes.get(i).getInstructions());
                    instructions.setSize(740, 900);
                    instructions.select(0, 0);

                    // Enable modify recipe button to be visible
                    modifyRecipe.setVisible(true);

                    // this will make the instructions start at the top of the panel!!!
                    gbc.weighty = 1;
                    gbc.weightx = 1;

                    center_panel.add(instructions, gbc);
                    center_panel.setAlignmentY(Component.TOP_ALIGNMENT);
                    center_panel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    center_panel.setVisible(true);
                }
            }
        }
    }


} // end of RecipeUI class
