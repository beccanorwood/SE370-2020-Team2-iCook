package iCook.View;

import iCook.View.Login.*;
import iCook.View.Operations.*;
import javax.swing.*;

/**
 * Abstract UI class that contains all of iCook's states.
 * Used in the State design pattern.
 *
 * @author Team 2
 * @version 5/5/2021
 */
public abstract class AbstractUI extends JPanel {
    // instance variables
    protected static WelcomeUI welcomeUI;
    protected static LoginUI loginUI;
    protected static SignUpUI signUpUI;
    protected static HomeUI homeUI;
    protected static ManageRecipesUI manageRecipesUI;
    protected static ModifyRecipeUI modifyRecipeUI;
    protected static InventoryUI inventoryUI;
    protected static ViewRecipesUI viewRecipesUI;


    /**
     * Initializes all of iCook's states (GUIs) and returns the first
     * UI to be displayed when the program is started.
     *
     * @return the program's initial state
     */
    public static AbstractUI start() {
        // initialize all of the static UIs
        welcomeUI = new WelcomeUI();
        loginUI = new LoginUI();
        signUpUI = new SignUpUI();
        homeUI = new HomeUI();
        manageRecipesUI = new ManageRecipesUI();
        modifyRecipeUI = new ModifyRecipeUI();
        inventoryUI = new InventoryUI();
        viewRecipesUI = new ViewRecipesUI();

        // the welcome panel is the first state of the program
        welcomeUI.initializePanel();
        return welcomeUI;
    }


    /**
     * Determines what the next state is and initializes its panel
     *
     * @param nextState the next state of the program
     * @return the next state (panel) of the program
     */
    protected AbstractUI nextState(AbstractUI nextState) {
        this.setVisible(false); // hide the current state (panel)

        // re-initialize the next state's contents
        nextState.initializePanel();
        return nextState;
    }


    // to be implemented by extending classes.
    // initializes the panel's contents.
    public abstract void initializePanel();


} // end of AbstractUI class
