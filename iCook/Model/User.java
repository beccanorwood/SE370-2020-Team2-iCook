package iCook.Model;
import java.util.List;

/**
 * User class that stores a user's id, username, and password
 * Uses the Singleton Pattern - only 1 user should exists in runtime
 *
 * @author Team 2
 * @version 11/14/2020
 */
public class User {

    // instance variables
    private int id;                                 // id that is stored in the database
    private String userName;                        // the username of this user
    private String password;                        // the password of this user
    private static User userObject = null;          // the SINGLE object of this class
    //private List<UserIngredient> userIngredients;   // list of user's ingredients


    /**
     * Constructor - 4 variables must be passed in
     */
    private User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        //this.userIngredients = userIngredients;
    }


    /**
     * Returns the singleton user object
     *
     * Determines if a user object is already created.
     * If the singleton exists, return the object
     * else, create a new user object and return it
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


    /**
     * Returns the singleton user object - no parameters needed here
     *
     * Determines if a user object is already created.
     * If the singleton exists, return the object
     * else, return null
     */
    public static User getUser()
    {
        if (userObject != null)
            return userObject;
        else
            return null;
    }


    /**
     * Getter for the user's username
     */
    public String getUserName()
    {
        return userName;
    }


    /**
     * Getter for the user's password
     */
    public String getPassword()
    {
        return password;
    }


    /**
     * Getter for the user's id
     */
    public int getId()
    {
        return id;
    }


//    /**
//     * Getter for the user's ingredients
//     */
//    public List<UserIngredient> getUserIngredients()
//    {
//        return userIngredients;
//    }


//    /**
//     * Adds a user ingredient to the user's list of ingredients
//     */
//    public void addUserIngredient(UserIngredient userIngredient)
//    {
//        userIngredients.add(userIngredient);
//    }

}
