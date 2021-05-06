package iCook.View.Login;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import iCook.Controller.ServiceDispatcher;
import iCook.View.AbstractUI;

/**
 * User interface for the sign up screen. A user can create a new account for iCook by entering
 * a desired username and password. Checks are in place to ensure a user cannot sign up with
 * a username that is already in use.
 *
 * @author Team 2
 * @version 5/5/2021
 */
public class SignUpUI extends AbstractUI implements ActionListener {
    private JPanel signup_panel;
    private JTextField userName_field;
    private JPasswordField passwordField;
    private ServiceDispatcher serviceDispatcher;
    private GridBagConstraints constraints;

    public SignUpUI() {

    }

    /**
     * Called to initialize the panel's contents
     */
    @Override
    public void initializePanel() {
        this.removeAll();

        // Create ServiceDispatcher
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());

        Border emptyBorder = BorderFactory.createEmptyBorder();

        //GridBagLayout specifies size and position of components in row/column layout
        signup_panel = new JPanel(new GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 10, 15, 10);

        JLabel iCook_signUP = new JLabel("Sign Up");
        iCook_signUP.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        iCook_signUP.setForeground(new Color(51,51,51));

        constraints.gridx = 3;
        constraints.gridy = 0;
        signup_panel.add(iCook_signUP, constraints);

        JButton create = new JButton("Create Account");
        create.setPreferredSize(new Dimension(144,32));
        create.addActionListener(this);
        create.setForeground(new Color(255,255,255));
        create.setBackground(new Color(28, 31, 46));
        create.setFocusPainted(false);
        create.setBorder(emptyBorder);

        create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                create.setForeground(new Color(255,255,255));
                create.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                create.setForeground(new Color(255,255,255));
                create.setBackground(new Color(28, 31, 46));
            }
        });

        JButton back = new JButton("Back");
        back.addActionListener(this);
        back.setPreferredSize(new Dimension(144,32));
        back.setForeground(new Color(255,255,255));
        back.setBackground(new Color(28, 31, 46));
        back.setFocusPainted(false);
        back.setBorder(emptyBorder);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                back.setForeground(new Color(255,255,255));
                back.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                back.setForeground(new Color(255,255,255));
                back.setBackground(new Color(28, 31, 46));
            }
        });

        JLabel userName = new JLabel("Create username: ");
        userName_field = new JTextField(20);
        userName_field.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        userName.setForeground(new Color(51,51,51));
        userName.setFont(new Font("Century Gothic", Font.PLAIN, 20));

        constraints.gridx = 3;
        constraints.gridy = 7;
        signup_panel.add(userName, constraints);

        constraints.gridx = 4;
        signup_panel.add(userName_field, constraints);

        JLabel passWord = new JLabel("Create password: ");
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        passWord.setForeground(new Color(51,51,51));
        passWord.setFont(new Font("Century Gothic", Font.PLAIN, 20));

        constraints.gridx = 3;
        constraints.gridy = 9;
        signup_panel.add(passWord, constraints);

        constraints.gridx = 4;
        signup_panel.add(passwordField, constraints);

        //Back button
        constraints.gridx = 3;
        constraints.gridy = 10;
        signup_panel.add(back, constraints);


        //Sign Up Button position
        constraints.gridx = 4;
        signup_panel.add(create, constraints);
        signup_panel.setBackground(new Color(255,255,255));

        //Clean up contents from WelcomeUI panel
        signup_panel.revalidate();
        signup_panel.repaint();

        this.add(signup_panel);

        this.revalidate();
        this.repaint();
        this.setVisible(true); // show the next state (panel)
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String btn_Selection = e.getActionCommand();


        // the user wants to go back to the WelcomeUI
        if(btn_Selection.equals("Back"))
            serviceDispatcher.updateState(nextState(AbstractUI.welcomeUI));

        // user click on "Create Account"
        else if (btn_Selection.equals("Create Account"))
        {
            // Error panel
            JPanel blank_error_p = new JPanel();
            blank_error_p.setBackground(new Color(255,255,255));
            // Error message
            JLabel blank_error = new JLabel();
            blank_error.setForeground(new Color(51,51,51));
            blank_error.setFont(new Font("Century Gothic", Font.PLAIN, 20));
            blank_error_p.add(blank_error);
            this.add(blank_error_p, BorderLayout.SOUTH);

            // get the username and password into a string
            String username = userName_field.getText();
            String password = new String(passwordField.getPassword());

            // make sure the username OR password are not blank
            if(username.isBlank() || password.isBlank())
            {
                //Error message
                blank_error.setText("Error: username and/or password cannot be blank!");
                blank_error_p.revalidate();
                blank_error_p.repaint();
            }

            else
            {
                // try to sign the user in, store the result in message
                String message = serviceDispatcher.signUp(username, password);

                // if creation was successful, check to see if they're logged in
                if (username.equals(message))
                {
                    // if the user is logged in, go to HomeUI
                    if (serviceDispatcher.isLoggedIn())
                        serviceDispatcher.updateState(nextState(AbstractUI.homeUI));
                }
                // if the creation was not successful, display the error
                else {
                    //Error message
                    blank_error.setText(message);
                    blank_error_p.revalidate();
                    blank_error_p.repaint();
                }
            }
        }

    } // end of actionPerformed

    @Override
    protected AbstractUI nextState(AbstractUI state) {
        this.setVisible(false); // hide the current state (panel)

        // re-initialize the next state's contents
        state.initializePanel();
        state.setVisible(true); // show the next state (panel)

        return state;
    }


} // end of SignUpUI class
