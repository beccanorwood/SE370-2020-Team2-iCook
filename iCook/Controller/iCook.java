package iCook.Controller;

import iCook.View.Operations.ModifyRecipeUI;
import iCook.View.Operations.ManageRecipesUI;

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
        new ManageRecipesUI();
        new ModifyRecipeUI();
    }
}
