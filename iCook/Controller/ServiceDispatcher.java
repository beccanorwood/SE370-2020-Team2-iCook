package iCook.Controller;
import iCook.Model.*;

import java.util.ArrayList;
import java.util.Collections;

public class ServiceDispatcher {

    // instance variables
    private Facade facade;
    private User user = null;
    private ArrayList<Ingredient> systemIngredients = new ArrayList<>();

    /**
     * Constructor. Does nothing.
     */
    public ServiceDispatcher()
    {
        facade = new Facade();
        getSystemIngredients();
    }


    /**
     * Returns true if the user's credentials are valid, false otherwise
     */
    public boolean login(String username, String password)
    {
        if ( facade.login(username, password) ) {
            user = User.getUser();
            return true;
        }
        else
            return false;
    }


    /**
     * Creates a new user with the given username and password
     */
    public void signUp(String username, String password)
    {
        facade.signUp(username, password);
        user = User.getUser();
    }


    /**
     * TESTING --- displays the singleton's variables
     */
    public void displayUser()
    {
        System.out.println(user.getId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
    }

    /**
     * Returns true if the singleton object is not null
     */
    public boolean isLoggedIn()
    {
        return ( user == null ) ? false : true;
    }


    /**
     * Returns a SORTED ArrayList of all system ingredient's names
     */
    public ArrayList<String> getAllSystemIngredients()
    {
        ArrayList<String> nameList = new ArrayList<>();

        for(int i = 0; i < systemIngredients.size(); i++)
        {
            nameList.add(systemIngredients.get(i).getIngredientName());
        }

        // sort the list of ingredient names
        Collections.sort(nameList);
        return nameList;
    }


    /**
     * Initializes SystemIngredients with an ArrayList containing Ingredient objects from the system
     */
    private void getSystemIngredients()
    {
        systemIngredients = facade.getSystemIngredients();
    }

}