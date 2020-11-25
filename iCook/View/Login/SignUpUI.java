package iCook.View.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iCook.Controller.ServiceDispatcher;
import java.sql.SQLException;

public class SignUpUI extends JFrame implements ActionListener
{
    private JFrame signup_frame;
    private JPanel signup_panel;
    private JTextField userName_field;
    private JPasswordField passwordField;
    private ServiceDispatcher serviceDispatcher;
    private GridBagConstraints constraints;

    public SignUpUI()
    {
        signup_frame = new JFrame("iCook");
        signup_frame.setSize(500, 500);
        signup_frame.setLocationRelativeTo(null);
        signup_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signup_frame.setLayout(new BorderLayout());

        signup_panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 10, 15, 10);

        JLabel iCook_signUP = new JLabel("Sign Up");
        iCook_signUP.setFont(new Font("ARIAL", Font.BOLD, 20));
        iCook_signUP.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 0;
        signup_panel.add(iCook_signUP, constraints);

        JButton create = new JButton("Create Account");
        JButton back = new JButton("Back");
        back.addActionListener(this);
        create.addActionListener(this);

        JLabel userName = new JLabel("Enter username: ");
        userName_field = new JTextField(20);
        userName.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 7;
        signup_panel.add(userName, constraints);

        constraints.gridx = 4;
        signup_panel.add(userName_field, constraints);

        JLabel passWord = new JLabel("Enter password: ");
        passwordField = new JPasswordField(20);
        passWord.setForeground(Color.WHITE);

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
        signup_panel.setBackground(Color.BLACK);
        signup_frame.add(signup_panel);
        signup_frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String btn_Selection = e.getActionCommand();

        if(btn_Selection.equals("Back"))
        {
            signup_frame.setVisible(false);
            signup_frame.dispose();
            WelcomeUI homepage = new WelcomeUI();
        }

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
                System.out.println("Error!");
                JLabel blank_error = new JLabel("Error: Username or Password cannot be blank");
                blank_error.setForeground(Color.WHITE);
                JPanel blank_error_p = new JPanel();
                blank_error_p.add(blank_error);
                blank_error_p.setBackground(Color.BLACK);
                signup_frame.add(blank_error_p, BorderLayout.SOUTH);
                signup_frame.setVisible(true);
            }

            else
            {
                // Create ServiceDispatcher
                serviceDispatcher = new ServiceDispatcher();

                try {
                    // create an account with the given username and password
                    serviceDispatcher.signUp(username, password);

                    // if the user is logged in, go to next page
                    if (serviceDispatcher.isLoggedIn()) {
                        serviceDispatcher.displayUser();
                    }
                }

                // catch SQLException error
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    } // end of actionPerformed

} // end of SignUpUI class
