package iCook.View.Operations;

import iCook.Controller.ServiceDispatcher;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * User interface for Admins to manage the recipes within iCook's database.
 * An Admin can create a new recipe from this GUI or modify existing recipes.
 *
 * @author Team 2
 * @version 04/23/2021
 */
public class ManageRecipesUI extends JFrame {
    // instance variables
    private ServiceDispatcher serviceDispatcher;
    private JPanel mainPanel;
    private GridBagConstraints gbc;
    private JTable table;
    private String[] columnNames;
    private Object[][] data;

    // constants for color scheme
    private final Color BG = new Color(26, 27, 34);
    private final Color FG = new Color(249,250,244);


    /**
     * Constructor
     */
    public ManageRecipesUI() {
        initialize();
    }


    /**
     * Initializes the frame's contents
     */
    public void initialize() {
        // instantiate the serviceDispatcher to get data
        serviceDispatcher = new ServiceDispatcher();

        // set the frame up
        this.setTitle("iCook");
        this.setSize(1024, 768);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,60));
        this.getContentPane().setBackground(BG);
        this.getContentPane().setForeground(FG);

        // set up the main panel
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(824, 600));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(FG),
                "iCook Recipes", TitledBorder.CENTER, TitledBorder.TOP)); // compound titled line border
        ((TitledBorder)mainPanel.getBorder()).setTitleColor(FG);
        mainPanel.setBackground(BG);
        mainPanel.setForeground(FG);

        // constraint used for component placement
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // instantiate column names
        columnNames = new String[]{"Id", "Recipe", "Published"};

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
        table.getTableHeader().setFont(new Font("Helvetica", Font.PLAIN, 14));  // set font of headers
        table.setFont(new Font("Helvetica", Font.PLAIN, 14));   // set font of rows
        table.setRowHeight(22); // set height of each row

        // allow the user to double click a row
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {           // detects double click events
                    JTable target = (JTable)e.getSource();
                    int row = target.getSelectedRow();  // gets the selected row
                    int column = 0;                     // get the first column containing the recipe's id
                    JOptionPane.showMessageDialog(null, table.getValueAt(row, column)); // get the value of a row and column.
                }
            }
        });

        // put a scroll pane in the table and add to main panel
        mainPanel.add(new JScrollPane(table), gbc);

        // create new button to let admin create a new recipe
        JButton addBtn = new JButton("Add new recipe");
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        addBtn.setPreferredSize(new Dimension(150,32));
        gbc.gridy++;
        mainPanel.add(addBtn, gbc);

        // create a new button to let admin go back to previous screen
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // back button goes to the HomeUI
                //serviceDispatcher.gotoHome(serviceDispatcher.getUserName(), ManageRecipesUI.this);
            }
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
    private Object[][] getData() {
        Object[][] tempData = new Object[][] {
                {"1", "Cake", "\u2713"},
                {"4", "Eggs", ""},
                {"11", "Waffles", ""},
                {"92", "Soup", "\u2713"},
        };

        return tempData;
    }

}

