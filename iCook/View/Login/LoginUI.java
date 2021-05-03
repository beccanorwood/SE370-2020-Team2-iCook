package iCook.View.Login;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import iCook.Controller.ServiceDispatcher;

/**
 * User interface for the login page. A user can login to iCook with their existing account.
 *
 * @author Team 2
 * @version 04/29/2021
 */
public class LoginUI extends JPanel implements ActionListener {
    private JPanel login_panel;
    private JTextField userName_field;
    private JPasswordField passwordField;
    private ServiceDispatcher serviceDispatcher;
    private GridBagConstraints constraints;

    public LoginUI() {
        // Create ServiceDispatcher instance
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());

        Border emptyBorder = BorderFactory.createEmptyBorder();

        login_panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(15, 10, 15, 10);

        JLabel iCook_login = new JLabel("Login");
        iCook_login.setFont(new Font("Century Gothic", Font.PLAIN, 40));
        iCook_login.setForeground(new Color(51,51,51));

        constraints.gridx = 3;
        constraints.gridy = 0;
        login_panel.add(iCook_login, constraints);

        JButton login = new JButton("Login");
        login.setPreferredSize(new Dimension(144,32));
        login.setForeground(new Color(255,255,255));
        login.setBackground(new Color(28, 31, 46));
        login.addActionListener(this);
        login.setFocusPainted(false);
        login.setBorder(emptyBorder);

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                login.setForeground(new Color(255,255,255));
                login.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                login.setForeground(new Color(255,255,255));
                login.setBackground(new Color(28, 31, 46));
            }
        });

        JButton back = new JButton("Back");
        back.setPreferredSize(new Dimension(144,32));
        back.setForeground(new Color(255,255,255));
        back.setBackground(new Color(28, 31, 46));
        back.addActionListener(this);
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

        JLabel userName = new JLabel("Enter username: ");
        userName.setForeground(new Color(51,51,51));
        userName.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        userName_field = new JTextField(20);
        userName_field.setFont(new Font("Century Gothic", Font.PLAIN, 20));

        constraints.gridx = 3;
        constraints.gridy = 3;
        login_panel.add(userName, constraints);

        constraints.gridx = 4;
        login_panel.add(userName_field, constraints);

        JLabel passWord = new JLabel("Enter password: ");
        passWord.setForeground(new Color(51,51,51));
        passWord.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 20));

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

        login_panel.setBackground(new Color(255, 255, 255));

        //Clean up contents from WelcomeUI panel
        login_panel.revalidate();
        login_panel.repaint();

        this.add(login_panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String btn_Selection = e.getActionCommand();

        JPanel southPanel = new JPanel();
        southPanel.setBackground(new Color(255,255,255));

        JLabel emptyFields = new JLabel("Error: The username and/or password cannot be empty");
        emptyFields.setForeground(new Color(51,51,51));
        emptyFields.setFont(new Font("Century Gothic", Font.PLAIN, 20));

        JLabel noAccountFound = new JLabel("Error: The username and/or password you entered does not match an account");
        noAccountFound.setForeground(new Color(51,51,51));
        noAccountFound.setFont(new Font("Century Gothic", Font.PLAIN, 20));

        // the user wants to go back to the WelcomeUI
        if (btn_Selection.equals("Back"))
            serviceDispatcher.gotoWelcome();

        // user clicks on "Login"
        else if (btn_Selection.equals("Login")) {

            // get the username and password into a string
            String username = userName_field.getText();
            String password = new String(passwordField.getPassword());

            // try to login with given credentials
            // if valid, send them to home page
            if ( serviceDispatcher.login(username, password) ) {
                System.out.println("Successfully logged in");
                serviceDispatcher.displayUser();
                serviceDispatcher.gotoHome();
            }

            // else, display an error
            else {
                if(username.isBlank() || password.isBlank())
                    southPanel.add(emptyFields);
                else
                    southPanel.add(noAccountFound);

                this.add(southPanel,BorderLayout.SOUTH);
                southPanel.revalidate();
                southPanel.repaint();
            }
        }

    } // end of actionPerformed

} // end of LoginUI class


