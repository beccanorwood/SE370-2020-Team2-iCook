package iCook.Model.DatabaseAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import iCook.Model.Ingredient;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO class for the ingredients table in iCook's database.
 * Every method requires a try and catch for a SQLException.
 *
 * @author Team 2
 * @version 4/25/2021
 */
public class IngredientDAO extends BaseDAO {

    /**
     * Constructor
     */
    public IngredientDAO() {
    }


    /**
     * Performs a SQL statement to determine if the ingredient is in the ingredients table
     *
     * @param id the id of the ingredient we want to check exists
     * @return true if the ingredient is in the ingredients table, false otherwise
     */
    public boolean validIngredient(int id) {
        try {
            //create a new statement
            Statement statement = this.createStatement();

            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM ingredients WHERE id = '" + id + "' ");
            return rs.next();  // move the 'cursor' to the first row (ALWAYS NEED THIS WHEN QUERYING)
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return false;
        }
    }


    /**
     * Performs a SQL statement to get all entries in the ingredients table
     *
     * @return an ArrayList containing Ingredient objects (all system ingredients will be in this list)
     */
    public ArrayList<Ingredient> getAllIngredients() {
        try {
            //create a new statement
            Statement statement = this.createStatement();

            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM ingredients");
            ArrayList<Ingredient> ingList = new ArrayList<>();  // ArrayList we are going to return

            // read each row in the table
            while (rs.next())
            {
                // read each column of the row
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String unit_of_measure = rs.getString("unit_of_measure");
                ingList.add(new Ingredient(id, name, unit_of_measure)); // add the ingredient to the ArrayList
            }

            return ingList;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }


} // end of IngredientDAO class
