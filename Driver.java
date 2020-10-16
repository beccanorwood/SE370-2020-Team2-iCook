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
        database.statement.execute("INSERT INTO test_izzy (first_name) VALUE ('test')");

        // Retrieving the data
        ResultSet resultSet = database.statement.executeQuery("SELECT COUNT(*) FROM test_izzy");
        resultSet.next();

        // Moving the cursor to the last row
        System.out.println("Table contains " + resultSet.getInt("COUNT(*)") + " rows");

        // TESTING
        System.out.println("HI JOEL");
    }
}
