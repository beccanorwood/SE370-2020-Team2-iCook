package iCook.Controller;

/**
 * iCook's entry point to begin the program.
 *
 * @author Team 2
 * @version 12/10/2020
 */
public class iCook
{
    public static void main(String[] args)
    {
        ServiceDispatcher serviceDispatcher = new ServiceDispatcher();
        serviceDispatcher.startProgram();
    }
}
