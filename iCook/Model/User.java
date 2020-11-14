package iCook.Model;

/**
 * User class that stores a user's id, username, and password
 * Uses the Singleton Pattern - only 1 user should exists in runtime
 *
 * @author Team 2
 * @version 11/7/2020
 */
public class User {

    // instance variables
    private int id;
    private String userName;
    private String password;
    private static User userObject = null;


    /**
     * Constructor - 3 variables must be passed in
     */
    private User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }


    /**
     * Determine if a user object has been made
     *
     * If a user object is already created, return that object
     * else, create a new user object
     */
    public static User getUser(int id, String userName, String password)
    {
        if (userObject == null)
        {
            userObject = new User(id, userName, password);
            System.out.println("User created\n");
        }

        return userObject;
    }

}
