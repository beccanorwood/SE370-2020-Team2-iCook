package iCook.Model.DatabaseAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientDAO extends BaseDAO {

    public IngredientDAO() throws SQLException {

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
     * Performs SQL statement to return the quantity field of an ingredient
     */
    public String getIngredientMeasurement(int ID) throws SQLException {
        // perform the query
        ResultSet rs = statement.executeQuery("SELECT unit_of_measure FROM ingredients WHERE ID = "+ID+" ");
        rs.next();  // move the 'cursor' to the first row (ALWAYS NEED THIS WHEN QUERYING)

        // return the quantity field of the specified ingredient
        return rs.getString("unit_of_measure");
    }
}
