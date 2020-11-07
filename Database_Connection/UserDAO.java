package Database_Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO for users table in DB
 *
 * @author Team 2
 * @version 11/7/2020
 */
public class UserDAO extends BaseDAO {

    /**
     * Constructor. Does nothing.
     */
    public UserDAO() throws SQLException {
    }


    /**
     * Performs SQL statement to nicely display all ingredients in the table
     */
    public void displayIngredientsTable() throws SQLException {
        // Perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM ingredients");

        // read each row in the table
        while (rs.next())
        {
            // read each column of the row
            String name = rs.getString("name");
            String unit_of_measure = rs.getString("unit_of_measure");

            // print out the row
            System.out.println(name + " measured in " + unit_of_measure );
        }
    }


    /**
     * Performs SQL statement to determine if a user exists with given login information
     */
    public boolean validUserLogin(String username, String password) throws SQLException {
        // perform the query
        ResultSet rs = statement.executeQuery("SELECT * FROM users WHERE user_name = '"+username+"' AND password = '"+password+"' LIMIT 1");

        // returns true if a there is a result
        // returns false otherwise
        return rs.next();
    }
}
