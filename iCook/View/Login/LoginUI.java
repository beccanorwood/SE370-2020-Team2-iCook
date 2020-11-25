package iCook.View.Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import iCook.Controller.ServiceDispatcher;

public class LoginUI extends JFrame implements ActionListener {
    private JFrame login_frame;
    private JPanel login_panel;
    private JTextField userName_field;
    private JPasswordField passwordField;
    private ServiceDispatcher serviceDispatcher;
    private GridBagConstraints constraints;

    public LoginUI() {
        //Need text fields for username & password
        login_frame = new JFrame("iCook");
        login_frame.setSize(500, 500);
        login_frame.setLocationRelativeTo(null);
        login_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login_frame.setLayout(new BorderLayout());

        login_panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 10, 15, 10);

        JLabel iCook_login = new JLabel("Login!");
        iCook_login.setFont(new Font("ARIAL", Font.BOLD, 30));
        iCook_login.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 0;
        login_panel.add(iCook_login, constraints);

        JButton login = new JButton("Login");
        JButton back = new JButton("Back");
        back.addActionListener(this);
        login.addActionListener(this);

        JLabel userName = new JLabel("Enter username: ");
        userName.setForeground(Color.WHITE);
        userName_field = new JTextField(20);

        constraints.gridx = 3;
        constraints.gridy = 3;
        login_panel.add(userName, constraints);

        constraints.gridx = 4;
        login_panel.add(userName_field, constraints);

        JLabel passWord = new JLabel("Enter password: ");
        passWord.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);

        constraints.gridx = 3;
        constraints.gridy = 6;
        login_panel.add(passWord, constraints);

        constraints.gridx = 4;
        login_panel.add(passwordField, constraints);

        //Back button position
        constraints.gridx = 3;
        constraints.gridy = 9;
        login_panel.add(back, constraints);

        //Login Button position
        constraints.gridx = 4;
        login_panel.add(login, constraints);

        login_panel.setBackground(Color.BLACK);
        login_frame.add(login_panel);
        login_frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn_Selection = e.getActionCommand();

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLACK);
        JLabel emptyFields = new JLabel("ERROR! The user name or password cannot be empty");
        emptyFields.setForeground(Color.WHITE);
        JLabel noAccountFound = new JLabel("ERROR! The user name or password you entered does not match an account");
        noAccountFound.setForeground(Color.WHITE);


        // user clicks on "Back"
        if (btn_Selection.equals("Back")) {

            login_frame.setVisible(false);
            login_frame.dispose();

            WelcomeUI ui = new WelcomeUI();

        }

        // user clicks on "Login"
        else if (btn_Selection.equals("Login")) {

            // get the username and password into a string
            String username = userName_field.getText();
            String password = new String(passwordField.getPassword());

            // print them out on the console
            System.out.println("User name: " + username);
            System.out.println("Password: " + password);

            // Create ServiceDispatcher instance
            serviceDispatcher = new ServiceDispatcher();

            // try to login with given credentials
            // if valid, send them to home page
            try
            {
                if ( serviceDispatcher.login(username, password) ) {
                    System.out.println("Successfully logged in");
                    serviceDispatcher.displayUser();
                }

                // else, display an error
                else {
                    if(username.isBlank() || password.isBlank()){
                        southPanel.add(emptyFields);
                        login_frame.add(southPanel, BorderLayout.SOUTH);
                        login_frame.setVisible(true);
                    }
                    else {
                        southPanel.add(noAccountFound);
                        login_frame.add(southPanel, BorderLayout.SOUTH);
                        login_frame.setVisible(true);
                        System.out.println("The user name or password you entered does not match an account");
                    }
                }
            }

            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }

    } // end of actionPerformed

} // end of LoginUI class


