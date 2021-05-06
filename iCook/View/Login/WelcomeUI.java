package iCook.View.Login;

import iCook.Controller.ServiceDispatcher;
import iCook.View.AbstractUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * User interface for iCook's starting screen. This is the very first screen a user will see when starting iCook.
 * The applications alternative mode of termination is located on this screen via the "Quit" button.
 *
 * @author Team 2
 * @version 5/5/2021
 */
public class WelcomeUI extends AbstractUI implements ActionListener {
    private JPanel panel;
    private JLabel iCook;
    private JButton login;
    private JButton signup;
    private JButton quit;
    private BufferedImage img;
    private ServiceDispatcher serviceDispatcher;


    /**
     * Constructor
     */
    public WelcomeUI() {

    }


    /**
     * Called to initialize the panel's contents
     */
    @Override
    public void initializePanel() {
        this.removeAll();

        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());



        // Splits frame into two panels vertically
        this.panel = new JPanel(new GridLayout(1,0)); //Left panel of welcomeUI


        //Read image to be displayed on left panel
        try {
            img = ImageIO.read(new File("logo_welcome_transp.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        // Initialize left side of panel
        setUpLeftPanel();

        // Setup Button & Image Effects
        componentEffects();

        this.panel.setBackground(new Color(234, 246, 248));
        this.add(this.panel, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();
        this.setVisible(true); // show the next state (panel)
    }


    private void setUpLeftPanel() {
        JLabel logo = new JLabel(new ImageIcon(img)); //Background image for left frame;
        logo.setBorder(new EmptyBorder(0,15,0,0));
        this.panel.add(logo, BorderLayout.CENTER);
    }


    private void componentEffects()
    {
        Border emptyBorder = BorderFactory.createEmptyBorder();

        login = new JButton("Login");
        login.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        login.setPreferredSize(new Dimension(144,35));
        login.setForeground(new Color(255,255,255));
        login.setBackground(new Color(28, 31, 46));
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


        signup = new JButton("Signup");
        signup.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        signup.setPreferredSize(new Dimension(144,35));
        signup.setForeground(new Color(255,255,255));
        signup.setBackground(new Color(28, 31, 46));
        signup.setFocusPainted(false);
        signup.setBorder(emptyBorder);

        signup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signup.setForeground(new Color(255,255,255));
                signup.setBackground(new Color(68, 166, 154));
            }
            public void mouseExited(MouseEvent e){
                signup.setForeground(new Color(255,255,255));
                signup.setBackground(new Color(28, 31, 46));
            }
        });

        quit = new JButton("Quit");
        quit.setFont(new Font("Century Gothic", Font.PLAIN, 20));
        quit.setPreferredSize(new Dimension(144,35));
        quit.setForeground(new Color(255,255,255));
        quit.setBackground(new Color(28, 31, 46));
        quit.setFocusPainted(false);
        quit.setBorder(emptyBorder);

        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                quit.setForeground(new Color(255,255,255));
                quit.setBackground(new Color(248, 68, 149));
            }
            public void mouseExited(MouseEvent e){
                quit.setForeground(new Color(255,255,255));
                quit.setBackground(new Color(28, 31, 46));
            }
        });

        login.addActionListener(this);
        signup.addActionListener(this);
        quit.addActionListener(this);

        setUpRightPanel();
    }

    private void setUpRightPanel()
    {
        JPanel userInfoPanel = new JPanel(new GridBagLayout());
        this.panel.add(userInfoPanel); //Add right panel to frame

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10,10,10,10);

        iCook = new JLabel("Welcome to iCook!");
        iCook.setFont(new Font("Century Gothic", Font.PLAIN, 46));
        iCook.setForeground(new Color(51,51,51));

        constraints.gridx = 3;
        constraints.gridy = 0;
        userInfoPanel.add(iCook, constraints);

        constraints.gridx = 3;
        constraints.gridy = 3;
        userInfoPanel.add(login, constraints);

        constraints.gridx = 3;
        constraints.gridy = 6;
        userInfoPanel.add(signup, constraints);

        constraints.gridx = 3;
        constraints.gridy = 9;
        userInfoPanel.add(quit, constraints);

        userInfoPanel.setBackground(new Color(255,255,255));
    }



    public void actionPerformed(ActionEvent e) {
        // get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        // set the appropriate next state based on button click
        switch (buttonChosen) {
            case "Login" -> serviceDispatcher.updateState(nextState(AbstractUI.loginUI));
            case "Signup" -> serviceDispatcher.updateState(nextState(AbstractUI.signUpUI));
            case "Quit" -> serviceDispatcher.quitProgram();
        }
    }


}
