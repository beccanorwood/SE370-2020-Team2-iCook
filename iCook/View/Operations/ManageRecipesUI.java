package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

/**
 * User interface for Admins to manage the recipes within iCook's database.
 * An Admin can create a new recipe from this GUI or modify existing recipes.
 *
 * @author Team 2
 * @version 04/30/2021
 */
public class ManageRecipesUI extends JPanel {
    // instance variables
    private ServiceDispatcher serviceDispatcher;
    private JPanel mainPanel;
    private GridBagConstraints gbc;
    private JTable table;
    private Vector<String> columnNames;
    private Vector<Vector> data;

    // constants for color scheme
    private final Color BG = new Color(255,255,255);
    private final Color FG = new Color(51,51,51);



    /**
     * Constructor
     */
    public ManageRecipesUI() {
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());
        initialize();
    }


    /**
     * Initializes the frame's contents
     */
    public void initialize() {
        // set the frame up
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,60));
        this.setBackground(BG);
        this.setForeground(FG);

        Border emptyBorder = BorderFactory.createEmptyBorder();

        // set up the main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(824, 600));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(FG),
                "iCook Recipes", TitledBorder.CENTER, TitledBorder.TOP, new Font("Century Gothic", Font.PLAIN, 14))); // compound titled line border
        ((TitledBorder)mainPanel.getBorder()).setTitleColor(FG);
        mainPanel.setBackground(new Color(246,251,253));
        mainPanel.setForeground(FG);

        // constraint used for component placement
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,0,0,0);

        // instantiate column names
        columnNames = new Vector<>();
        columnNames.addElement("Id");
        columnNames.addElement("Recipe");
        columnNames.addElement("Published");

        // instantiate the data
        data = getData();

        // instantiate the table containing our data
        table = new JTable(data, columnNames) {
            @Override
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        table.setFocusable(false);
        table.getTableHeader().setReorderingAllowed(false); // prohibit column dragging
        table.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 14));  // set font of headers
        table.setFont(new Font("Century Gothic", Font.PLAIN, 14));   // set font of rows
        table.setRowHeight(22); // set height of each row

        // allow the user to double click a row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {           // detects double click events
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();  // gets the selected row
                    int column = 0;                     // get the first column containing the recipe's id

                    // go to the modify UI for this selected recipe
                    serviceDispatcher.gotoModifyRecipeUI(Integer.parseInt((String)table.getValueAt(row,column)));
                }
            }
        });

        // put a scroll pane in the table and add to main panel
        mainPanel.add(new JScrollPane(table), gbc);

        // create new button to let admin create a new recipe
        JButton addBtn = new JButton("Add new recipe");
        addBtn.setForeground(new Color(255,255,255));
        addBtn.setBackground(new Color(28, 31, 46));
        addBtn.setFocusPainted(false);
        addBtn.setBorder(emptyBorder);

        addBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addBtn.setForeground(new Color(255,255,255));
                addBtn.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                addBtn.setForeground(new Color(255,255,255));
                addBtn.setBackground(new Color(28, 31, 46));
            }
        });

        addBtn.addActionListener(e -> serviceDispatcher.gotoModifyRecipeUI());

        addBtn.setPreferredSize(new Dimension(144,32));
        gbc.gridy++;
        mainPanel.add(addBtn, gbc);

        // create a new button to let admin go back to previous screen
        JButton backBtn = new JButton("Back");
        backBtn.setForeground(new Color(255,255,255));
        backBtn.setBackground(new Color(28, 31, 46));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(emptyBorder);

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

        backBtn.addActionListener(e -> {
            // back button goes to the HomeUI
            serviceDispatcher.gotoHome();
        });

        backBtn.setPreferredSize(new Dimension(100,32));
        gbc.gridy++;
        mainPanel.add(backBtn, gbc);

        // add the main panel to the frame
        this.add(mainPanel);
        this.setVisible(true);
    }


    /**
     * Returns the data to be displayed in the table.
     */
    private Vector<Vector> getData() {
        return serviceDispatcher.getRecipes();
    }

}

