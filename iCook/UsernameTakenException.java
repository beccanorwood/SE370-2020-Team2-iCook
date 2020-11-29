package iCook;

/**
 * User defined exception. Used when a username has already been taken.
 *
 * @author Team 2
 * @version 11/28/2020
 */
public class UsernameTakenException extends RuntimeException {

    // instance variables
    String message;

    /**
     * Constructor - adds the error message to the super exception
     * and keeps the passed in error message
     */
    public UsernameTakenException(String message)
    {
        super(message);
        this.message = message;
    }


    /**
     * Formats the exception into readable String
     */
    public String toString()
    {
        return ("Error: The Username " + message);
    }


} // end of UserNameTakenException class
