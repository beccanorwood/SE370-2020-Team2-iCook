package User_Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JFrame implements ActionListener
{
    private JFrame signup_frame;
    private JPanel signup_panel;

    public SignUp()
    {
        //Need text fields for the following:
        //First Name, Last Name, Email, UserName, and Password

        signup_frame = new JFrame("iCook");
        signup_frame.setSize(500, 500);
        signup_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signup_frame.setLayout(new BorderLayout());

        signup_panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

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

        JLabel firstName = new JLabel("Enter First Name: ");
        JTextField firstName_field = new JTextField(20);
        firstName.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 1;
        signup_panel.add(firstName, constraints);

        constraints.gridx = 4;
        signup_panel.add(firstName_field, constraints);

        JLabel lastName = new JLabel("Enter Last Name: ");
        JTextField lastName_field = new JTextField(20);
        lastName.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 3;
        signup_panel.add(lastName, constraints);

        constraints.gridx = 4;
        signup_panel.add(lastName_field, constraints);

        JLabel email = new JLabel("Enter Email Address: ");
        JTextField email_field = new JTextField(20);
        email.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 5;
        signup_panel.add(email, constraints);

        constraints.gridx = 4;
        signup_panel.add(email_field, constraints);


        JLabel userName = new JLabel("Enter username: ");
        JTextField userName_field = new JTextField(20);
        userName.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 7;
        signup_panel.add(userName, constraints);

        constraints.gridx = 4;
        signup_panel.add(userName_field, constraints);

        JLabel passWord = new JLabel("Enter password: ");
        JPasswordField passwordField = new JPasswordField(20);
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

        if(btn_Selection.equals("Back")){
            signup_frame.setVisible(false);
            signup_frame.dispose();

            iCook_UI homepage = new iCook_UI();
        }

    }
}
