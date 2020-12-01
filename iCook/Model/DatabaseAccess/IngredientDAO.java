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
 * @version 11/30/2020
 */
public class IngredientDAO extends BaseDAO {

    /**
     * Constructor
     */
    public IngredientDAO() {
    }


    /**
     * Performs a SQL statement to nicely display all system ingredients
     */
    public void displayIngredientsTable() {
        try {
            Statement statement = this.createStatement();
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
            throwables.printStackTrace();
        }
    }


    /**
     * Performs a SQL statement to return the id field from the ingredients table
     *
     * @param name the name of the ingredient whose id we want to find
     * @return the id field of the ingredient
     */
    public int getIngredientID(String name) {
        try {
            //create a new statement
            Statement statement = this.createStatement();

            // perform the query
            ResultSet rs = statement.executeQuery("SELECT id FROM ingredients WHERE name = '" + name + "' ");
            rs.next();  // move the 'cursor' to the first row (ALWAYS NEED THIS WHEN QUERYING)

            // return the quantity field of the specified ingredient
            return rs.getInt("id");
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return 0;
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
