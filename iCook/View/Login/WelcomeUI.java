package iCook.View.Login;

import iCook.Controller.ServiceDispatcher;

import javax.imageio.ImageIO;
import javax.swing.*;
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
 * @version 04/09/2021
 */
public class WelcomeUI extends JPanel implements ActionListener {
    private JPanel panel;
    private JLabel iCook;
    private JButton login;
    private JButton signup;
    private JButton quit;
    private BufferedImage img;
    private BufferedImage chefImg;
    private ServiceDispatcher serviceDispatcher;


    /**
     * Constructor
     */
    public WelcomeUI() {
        serviceDispatcher = new ServiceDispatcher();
        this.setLayout(new BorderLayout());


        /*
        Splits frame into two panels vertically
         */
        this.panel = new JPanel(new GridLayout(1,0)); //Left panel of welcomeUI


        //Read image to be displayed on left panel
        try {
            img = ImageIO.read(new File("logo_welcome_transp.png"));
        }catch(IOException e){
            e.printStackTrace();
        }

        //*****************************
        //Initialize left side of panel
        //*****************************
        setUpLeftPanel();

        //*****************************
        //Setup Button & Image Effects
        //*****************************
        componentEffects();

        this.panel.setBackground((Color.decode("#838996")));
        this.add(this.panel, BorderLayout.CENTER);
    }


    private void setUpLeftPanel()
    {
        JLabel logo = new JLabel(new ImageIcon(img)); //Background image for left frame;
        logo.setBorder(new EmptyBorder(0,15,0,0));

        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try{
                    chefImg = ImageIO.read(new File("chef_welcome_transp.png"));
                }catch(IOException e1){
                    e1.printStackTrace();
                }
                logo.setIcon(new ImageIcon(chefImg));
            }
            public void mouseExited(MouseEvent e){
                logo.setIcon(new ImageIcon(img));
            }
        });

        this.panel.add(logo, BorderLayout.CENTER);
    }


    private void componentEffects()
    {
        login = new JButton("Login");
        login.setFont(new Font("Helvetica", Font.PLAIN, 20));
        login.setPreferredSize(new Dimension(144,35));

        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                login.setFont(new Font("Helvetica", Font.BOLD, 20));
                login.setForeground(new Color(81, 143, 112));
            }
            public void mouseExited(MouseEvent e){
                login.setFont(new Font("Helvetica", Font.PLAIN, 20));
                login.setForeground(new JButton().getForeground());
                login.setBackground(new JButton().getBackground());
            }
        });


        signup = new JButton("Signup");
        signup.setFont(new Font("Helvetica", Font.PLAIN, 20));
        signup.setPreferredSize(new Dimension(144,35));

        signup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                signup.setFont(new Font("Helvetica", Font.BOLD, 20));
                signup.setForeground(new Color(81, 143, 112));
            }
            public void mouseExited(MouseEvent e){
                signup.setFont(new Font("Helvetica", Font.PLAIN, 20));
                signup.setForeground(new JButton().getForeground());
                signup.setBackground(new JButton().getBackground());
            }
        });


        quit = new JButton("Quit");
        quit.setFont(new Font("Helvetica", Font.PLAIN, 20));
        quit.setPreferredSize(new Dimension(144,35));


        quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                quit.setFont(new Font("Helvetica", Font.BOLD, 20));
                quit.setForeground(new Color(81, 143, 112));
            }
            public void mouseExited(MouseEvent e){
                quit.setFont(new Font("Helvetica", Font.PLAIN, 20));
                quit.setForeground(new JButton().getForeground());
                quit.setBackground(new JButton().getBackground());
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
        iCook.setFont(new Font("Helvetica", Font.BOLD, 54));
        iCook.setForeground(Color.decode("#d3d3d3"));

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

        userInfoPanel.setBackground((Color.decode("#23272A")));
    }



    public void actionPerformed(ActionEvent e)
    {

        //get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        //Create 3 separate classes for each actionEvent to populate the proper JFrame

        if(buttonChosen.equals("Login")){
            serviceDispatcher.gotoLogin();
        }
        else if(buttonChosen.equals("Signup")){
            serviceDispatcher.gotoSignup();
        }
        else if (buttonChosen.equals("Quit"))
        {
            serviceDispatcher.quitProgram();
        }
    }
}
