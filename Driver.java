import java.sql.SQLException;
import User_Interface.iCook_UI;

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
        iCook_UI ucook = new iCook_UI();

        // create new database object
        //DatabaseConnection database = new DatabaseConnection();

        // Insert an item into the ingredients table
        //database.addUserIngredient(1, 2, 50);

        // Update an item in the ingredients table
        //database.updateUserIngredient(1, 9);

        // Remove an item from the ingredients table
        //database.deleteUserIngredient(2);

        // Retrieving the data
        //System.out.println("iCook Ingredients:");
        //database.displayIngredientsTable();

        // get the quantity of a specific ingredient
        //System.out.print("\nSugar is measure in: " + database.getIngredientMeasurement(1));

        // determines if a user is in the system
        //System.out.println(database.validUserLogin("john", "doe"));

    }
}
