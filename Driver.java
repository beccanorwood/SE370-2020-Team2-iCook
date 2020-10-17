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

        // Insert an item into the ingredients table
        //database.addIngredient("milk", 2);

        // Update an item in the ingredients table
        //database.updateIngredient("milk", 9);

        // Remove an item from the ingredients table
        //database.deleteIngredient("milk");

        // Retrieving the data
        System.out.println("My Ingredients:");
        database.displayIngredients();

        // get the quantity of a specific ingredient
        System.out.print("\nAmount of sugar: " + database.getIngredientQuantity("sugar"));
    }
}
