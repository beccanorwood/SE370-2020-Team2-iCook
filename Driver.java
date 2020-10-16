import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Driver
 *
 * @author Team 2
 * @version 10/16/2020
 */
public class Driver
{
    public static void main(String[] args) throws SQLException
    {
        // create new database object
        DatabaseConnection database = new DatabaseConnection();

        // Insert an item to the database
        database.addItem("hello");

        // Retrieving the data
        int num = database.getNumOfItemsInTable();

        // Moving the cursor to the last row
        System.out.println("Table contains " + num + " rows");
    }
}
