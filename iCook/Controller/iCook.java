package iCook.Controller;

import iCook.View.Login.WelcomeUI;
import iCook.View.Operations.AdminUI;

import javax.swing.*;

/**
 * iCook's entry point to begin the program.
 *
 * @author Team 2
 * @version 04/09/2021
 */
public class iCook
{
    public static void main(String[] args)
    {
        //ServiceDispatcher serviceDispatcher = new ServiceDispatcher();
        //serviceDispatcher.startProgram();

        // **************************
        // Temp test code for Admin UI
        // ***************************
        JFrame initial_frame = new JFrame();
        initial_frame.setTitle("iCook");
        initial_frame.setSize(1024, 768);
        initial_frame.setLocationRelativeTo(null);
        new AdminUI(initial_frame);
    }
}
