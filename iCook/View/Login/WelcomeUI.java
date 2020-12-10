package iCook.View.Login;

import iCook.Controller.ServiceDispatcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User interface for iCook's starting screen. This is the very first screen a user will see when starting iCook.
 * The applications alternative mode of termination is located on this screen via the "Quit" button.
 *
 * @author Team 2
 * @version 12/10/2020
 */
public class WelcomeUI extends JFrame implements ActionListener
{
    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;
    private ServiceDispatcher serviceDispatcher;

    public WelcomeUI() //Constructor will display initial login screen to user
    {
        serviceDispatcher = new ServiceDispatcher();

        frame = new JFrame("iCook");
        frame.setSize(1024, 768);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

        panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel iCook = new JLabel("Welcome to iCook!");
        iCook.setFont(new Font("Helvetica", Font.BOLD, 54));
        iCook.setForeground(new Color(249,250,244));

        constraints.gridx = 3;
        constraints.gridy = 0;
        panel.add(iCook, constraints);

        try{
            img = ImageIO.read(new File("iCook_Logo.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }

        ImageIcon logo = new ImageIcon(img);

        JLabel iCookLogo = new JLabel();
        iCookLogo.setIcon(logo);

        constraints.gridx = 4;
        panel.add(iCookLogo, constraints);

        JButton login = new JButton("Login");
        login.setFont(new Font("Helvetica", Font.PLAIN, 20));
        login.setPreferredSize(new Dimension(144,35));

        JButton signup = new JButton("Signup");
        signup.setFont(new Font("Helvetica", Font.PLAIN, 20));
        signup.setPreferredSize(new Dimension(144,35));

        JButton quit = new JButton("Quit");
        quit.setFont(new Font("Helvetica", Font.PLAIN, 20));
        quit.setPreferredSize(new Dimension(144,35));

        login.addActionListener(this);
        signup.addActionListener(this);
        quit.addActionListener(this);

        constraints.gridx = 3;
        constraints.gridy = 3;
        panel.add(login, constraints);

        constraints.gridx = 3;
        constraints.gridy = 6;
        panel.add(signup, constraints);

        constraints.gridx = 3;
        constraints.gridy = 9;
        panel.add(quit, constraints);

        panel.setBackground(new Color(26, 27, 34));
        frame.add(panel);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        //get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        //Create 3 separate classes for each actionEvent to populate the proper JFrame

        if(buttonChosen.equals("Login")){
            serviceDispatcher.gotoLogin(frame);
        }
        else if(buttonChosen.equals("Signup")){
            serviceDispatcher.gotoSignup(frame);
        }
        else if (buttonChosen.equals("Quit"))
        {
            serviceDispatcher.quitProgram(frame);
        }
    }
}
