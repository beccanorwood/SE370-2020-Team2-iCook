import java.sql.SQLException;
import User_Interface.*;

public class Driver
{
    public static void main(String[] args) throws SQLException
    {
        iCook_UI ui = new iCook_UI();

        // create new database object
        DatabaseConnection database = new DatabaseConnection();

        // Insert an item into the ingredients table
        database.addUserIngredient(1, 2, 50);

        // Update an item in the ingredients table
        database.updateUserIngredient(1, 9);

        // Remove an item from the ingredients table
        database.deleteUserIngredient(2);

        // Retrieving the data
        System.out.println("iCook Ingredients:");
        database.displayIngredientsTable();

        // get the quantity of a specific ingredient
        System.out.print("\nSugar is measure in: " + database.getIngredientMeasurement(1));
    }
}
