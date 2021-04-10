package iCook.View.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iCook.Controller.ServiceDispatcher;

/**
 * User interface for the sign up screen. A user can create a new account for iCook by entering
 * a desired username and password. Checks are in place to ensure a user cannot sign up with
 * a username that is already in use.
 *
 * @author Team 2
 * @version 04/09/2021
 */
public class SignUpUI extends JFrame implements ActionListener
{
    private JFrame signup_frame;
    private JPanel signup_panel;
    private JTextField userName_field;
    private JPasswordField passwordField;
    private ServiceDispatcher serviceDispatcher;
    private GridBagConstraints constraints;

    public SignUpUI(JFrame frame)
    {
        // Create ServiceDispatcher
        serviceDispatcher = new ServiceDispatcher();

        signup_frame = frame;
        signup_frame.setTitle("iCook");
        signup_frame.setSize(1024, 768);
        signup_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signup_frame.setLayout(new BorderLayout());
        signup_frame.setResizable(false);

        signup_panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 10, 15, 10);

        JLabel iCook_signUP = new JLabel("Sign Up");
        iCook_signUP.setFont(new Font("Helvetica", Font.BOLD, 40));
        iCook_signUP.setForeground(new Color(249,250,244));

        constraints.gridx = 3;
        constraints.gridy = 0;
        signup_panel.add(iCook_signUP, constraints);

        JButton create = new JButton("Create Account");
        create.setPreferredSize(new Dimension(144,32));
        create.addActionListener(this);

        JButton back = new JButton("Back");
        back.addActionListener(this);
        back.setPreferredSize(new Dimension(144,32));

        JLabel userName = new JLabel("Create username: ");
        userName_field = new JTextField(20);
        userName_field.setFont(new Font("Helvetica", Font.PLAIN, 20));
        userName.setForeground(new Color(249,250,244));
        userName.setFont(new Font("Helvetica", Font.PLAIN, 20));

        constraints.gridx = 3;
        constraints.gridy = 7;
        signup_panel.add(userName, constraints);

        constraints.gridx = 4;
        signup_panel.add(userName_field, constraints);

        JLabel passWord = new JLabel("Create password: ");
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        passWord.setForeground(new Color(249,250,244));
        passWord.setFont(new Font("Helvetica", Font.PLAIN, 20));

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
        signup_panel.setBackground(new Color(26, 27, 34));

        //Clean up contents from WelcomeUI panel
        signup_panel.revalidate();
        signup_panel.repaint();

        signup_frame.add(signup_panel);
        signup_frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String btn_Selection = e.getActionCommand();

        // the user wants to go back to the WelcomeUI
        if(btn_Selection.equals("Back"))
            serviceDispatcher.gotoWelcome(signup_frame);

        // user click on "Create Account"
        else if (btn_Selection.equals("Create Account"))
        {
            // get the username and password into a string
            String username = userName_field.getText();
            String password = new String(passwordField.getPassword());

            // make sure the username OR password are not blank
            if(username.isBlank() || password.isBlank())
            {
                //Error message
                JLabel blank_error = new JLabel("Error: username and/or password cannot be blank!");
                blank_error.setForeground(new Color(241,122,126));
                blank_error.setFont(new Font("Helvetica", Font.PLAIN, 20));

                JPanel blank_error_p = new JPanel();
                blank_error_p.add(blank_error);
                blank_error_p.setBackground(new Color(26, 27, 34));

                signup_frame.add(blank_error_p, BorderLayout.SOUTH);
                signup_frame.setVisible(true);
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
                        serviceDispatcher.gotoHome(username, signup_frame);
                }
                // if the creation was not successful, display the error
                else {
                    //Error message
                    JLabel blank_error = new JLabel(message);
                    blank_error.setForeground(new Color(241,122,126));

                    JPanel blank_error_p = new JPanel();
                    blank_error_p.add(blank_error);
                    blank_error.setFont(new Font("Helvetica", Font.PLAIN, 20));
                    blank_error_p.setBackground(new Color(26, 27, 34));

                    signup_frame.add(blank_error_p, BorderLayout.SOUTH);
                    signup_frame.setVisible(true);
                }
            }
        }

    } // end of actionPerformed

} // end of SignUpUI class
