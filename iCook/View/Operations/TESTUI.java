package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import iCook.View.Operations.DisplayObjects.IngredientDisplayObject;

import javax.imageio.plugins.tiff.TIFFTag;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * TEST class for new inventory UI design
 *
 * @author Team 2
 * @version 04/27/2021
 */
public class TESTUI extends JPanel {
    // instance variables
    private JPanel mainPanel;       // Main panel for all elements
    private JPanel mainIngPanel;
    private JLabel title;
    private JPanel titlePanel;
    private JPanel btnPanel;
    private JButton save;
    private JButton back;
    private JScrollPane scrollPane; // Scroll Panel
    private JPanel centerPanel;
    private JPanel containerPanel;  // Panel that holds row of buttons to add ingredients
    private GridBagConstraints gbc; //Constraints used to ingredient rows


    private ServiceDispatcher serviceDispatcher;
    private ArrayList<IngredientDisplayObject> system_ingredients;
    private ArrayList<IngredientDisplayObject> user_ingredients;

    private ArrayList<JPanel> all_ingredient_panels;
    private JPanel ingredientPanel;
    private boolean isFirstIngredient = true; // there should always be at least 1 row

    private Border emptyBorder = BorderFactory.createEmptyBorder(); // btn formatting


    // constants for color scheme
    private final Color BG = new Color(255,255,255);
    private final Color FG = new Color(51,51,51);


    /**
     * Constructor
     */
    public TESTUI() {
        // initialize instance variables here
        serviceDispatcher = new ServiceDispatcher();    // create the ServiceDispatcher
        system_ingredients = serviceDispatcher.getSystemIngredientDisplayObjects(); // get the list of system ingredients
        //user_ingredients = serviceDispatcher.getUserInventory(); // get user's inventory
        all_ingredient_panels = new ArrayList<>();

        // set up the container panel
        containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(0, 1)); //Panel for Buttons on bottom to dynamically add/remove


        // initialize the panels contents
        initialize();

        // *************************
        // 1st Ingredient Panel
        // *************************
        addIngredientRow();


        // ********************************************
        // Set frame visible at end to show all changes
        // ********************************************
        this.setVisible(true);
    }

    public void initialize() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(255, 255, 255));

        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(7,10,7,10);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255,255,255));


        title = new JLabel("Your Ingredient Inventory",SwingConstants.CENTER);
        title.setFont(new Font("Century Gothic", Font.PLAIN, 46));

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(title, SwingConstants.CENTER);
        this.add(titlePanel, BorderLayout.NORTH);

        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(255,255,255));
        centerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(FG),
                       "Select '+' to add a new Ingredient", TitledBorder.CENTER, TitledBorder.TOP));


        //Create mainIngredientPanel
        mainIngPanel = new JPanel(new GridBagLayout());
        mainIngPanel.setBackground(BG);
        //mainIngPanel.setMaximumSize(new Dimension(824, 600));

        //Panel for Back & Save Buttons
        btnPanel = new JPanel(new GridLayout(1,2, 10, 5));
        btnPanel.setPreferredSize(new Dimension(200, 25));

        back = new JButton("Back");
        back.setFocusPainted(false);
        back.setBorder(emptyBorder);
        back.setForeground(new Color(255,255,255));
        back.setBackground(new Color(28, 31, 46));

        save = new JButton("Save");
        save.setFocusPainted(false);
        save.setBorder(emptyBorder);
        save.setForeground(new Color(255,255,255));
        save.setBackground(new Color(28, 31, 46));


        btnPanel.add(back, gbc);
        btnPanel.add(save, gbc);


        scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBackground(new Color(255,255,255));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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

        // create the unit_of_measure button
        JButton ing_units = new JButton();
        ing_units.setForeground(new Color(255,255,255));
        ing_units.setBackground(new Color(28, 31, 46));
        ing_units.setFocusPainted(false);
        ing_units.setBorder(emptyBorder);
        ing_units.setEnabled(false);

        // create the combo box here
        JComboBox ing_list = new JComboBox(getIngredientsList());
        ing_list.addActionListener(e -> {
            for (IngredientDisplayObject ingDO : system_ingredients) {
                if (ingDO.getName().equals(ing_list.getSelectedItem())) {
                    ing_units.setText(ingDO.getUnitOfMeasure());
                    break;
                } else {
                    ing_units.setText("");
                }
            }
        });

        // create the text field for quantity input
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(3, 5));
        quantity.setHorizontalAlignment(JTextField.CENTER);

        // create the add button
        JButton addButton = new JButton("+");
        addButton.setForeground(new Color(255,255,255));
        addButton.setBackground(new Color(28, 31, 46));
        addButton.setFocusPainted(false);
        addButton.setBorder(emptyBorder);
        addButton.addActionListener(e -> addIngredientRow());

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

        //Empty border that adds padding between ever new ingredient row
        ingredientPanel.setBorder(new EmptyBorder(5,0,5,0));

        // add everything to the main panel
        containerPanel.add(ingredientPanel);

        centerPanel.add(containerPanel);

        //mainPanel.add(centerPanel);
        mainIngPanel.add(centerPanel);
        mainPanel.add(mainIngPanel);


        // add this ingredient panel to the list containing all of them
        all_ingredient_panels.add(ingredientPanel);


        gbc.gridy = GridBagConstraints.SOUTH;

        mainIngPanel.add(btnPanel, gbc);


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
