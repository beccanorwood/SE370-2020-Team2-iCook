package iCook.Model.DatabaseAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import iCook.Model.Ingredient;
import java.util.ArrayList;

/**
 * DAO class for the Ingredient table in iCook's database.
 *
 * @author Team 2
 * @version 11/28/2020
 */
public class IngredientDAO extends BaseDAO {

    /**
     * Constructor for the IngredientDAO
     */
    public IngredientDAO() {

    }


    /**
     * Performs a SQL statement to nicely display all system ingredients
     */
    public void displayIngredientsTable() {
        try {
            // Perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM ingredients");

            // read each row in the table
            while (rs.next()) {
                // read each column of the row
                String name = rs.getString("name");
                String unit_of_measure = rs.getString("unit_of_measure");

                // print out the row
                System.out.println(name + " measured in " + unit_of_measure);
            }
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();;
        }
    }


    /**
     * Performs a SQL statement to return the quantity field of an ingredient
     */
    public String getIngredientMeasurement(int ID) {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT unit_of_measure FROM ingredients WHERE ID = " + ID + " ");
            rs.next();  // move the 'cursor' to the first row (ALWAYS NEED THIS WHEN QUERYING)

            // return the quantity field of the specified ingredient
            return rs.getString("unit_of_measure");
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }


    /**
     * Performs a SQL statement to return an ArrayList of Ingredient Objects
     * (all system ingredients will be in this list).
     */
    public ArrayList<Ingredient> getAllIngredients() {
        try {
            // perform the query
            ResultSet rs = statement.executeQuery("SELECT * FROM ingredients");
            ArrayList<Ingredient> ingList = new ArrayList<>();  // array list we are going to return

            // read each row in the table
            while (rs.next())
            {
                // read each column of the row
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String unit_of_measure = rs.getString("unit_of_measure");
                ingList.add(new Ingredient(id, name, unit_of_measure)); // add the ingredient to the array list
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
