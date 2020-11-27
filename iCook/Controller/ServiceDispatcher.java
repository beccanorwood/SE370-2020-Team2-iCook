package iCook.Controller;
import iCook.Model.*;

public class ServiceDispatcher {

    // instance variables
    Facade facade;
    User user = null;

    /**
     * Constructor. Does nothing.
     */
    public ServiceDispatcher()
    {
        facade = new Facade();
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

}