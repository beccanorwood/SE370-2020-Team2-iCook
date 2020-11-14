package User_Interface;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class iCook_UI extends JFrame implements ActionListener
{
    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;

    public iCook_UI() //Constructor will display initial login screen to user
    {
        frame = new JFrame("iCook");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        panel = new JPanel(new GridBagLayout()); //GridBagLayout specifies size and position of components in row/column layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel iCook = new JLabel("Welcome to iCook!");
        iCook.setFont(new Font("ARIAL", Font.BOLD, 30));
        iCook.setForeground(Color.WHITE);

        constraints.gridx = 3;
        constraints.gridy = 0;
        panel.add(iCook, constraints);

        try{
            img = ImageIO.read(new File("iCook_Logo(125).png"));
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
        JButton signup = new JButton("Signup");
        JButton guest = new JButton("Guest Mode");

        login.addActionListener(this);
        signup.addActionListener(this);
        guest.addActionListener(this);

        constraints.gridx = 3;
        constraints.gridy = 3;
        panel.add(login, constraints);

        constraints.gridx = 3;
        constraints.gridy = 6;
        panel.add(signup, constraints);

        constraints.gridx = 3;
        constraints.gridy = 9;
        //panel.add(guest, constraints);

        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        //get Action Command will find resulting action based upon button name
        String buttonChosen = e.getActionCommand();

        //Create 3 separate classes for each actionEvent to populate the proper JFrame

        if(buttonChosen.equals("Login")){
            //Close initial login page
            frame.setVisible(false);
            frame.dispose();

            //Instantiate Login Class to display login GUI
            Login userLogin = new Login();

        }
        else if(buttonChosen.equals("Signup")){
            //Close home page
            //SignUp class
            frame.setVisible(false);
            frame.dispose();

            //Sign Up class will display and user can create account
            SignUp userSignUp = new SignUp();

        }
        else{
            System.out.println("\nYou pressed the Guest Mode button!");

        }
    }
}
