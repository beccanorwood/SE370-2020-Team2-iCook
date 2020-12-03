package iCook.Model.DatabaseAccess;

import iCook.Model.Recipe;
import iCook.Model.UserIngredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * DAO class for the recipes && recipe_ingredients table in iCook's database.
 * Every method requires a try and catch for a SQLException.
 *
 * @author Team 2
 * @version 12/2/2020
 */
public class RecipeDAO extends BaseDAO {

    /**
     * Constructor
     */
    public RecipeDAO() {
    }


    /**
     * Performs a SQL statement to return a list of all satisfiable recipes for a user
     * based on their ingredient list
     *
     * @param userIngredients the user's inventory containing UserIngredient objects
     * @return an ArrayList containing Recipe objects satisfiable to a user
     */
    public ArrayList<Recipe> getSatisfiedRecipes(ArrayList<UserIngredient> userIngredients)
    {
        try
        {
            // create a new statement to execute a query
            Statement statement = this.createStatement();

            // create a list to be returned to the controller, storing the recipes the user can make
            ArrayList<Recipe> satisfiedRecipes = new ArrayList<>();

            // create a HashMap to store every user ingredient's id and quantity
            HashMap<Integer, Integer> user_inventory = new HashMap<>();

            // populate the user's ingredient HashMap with key = ingredient_id / value = ingredient_quantity
            for (UserIngredient userIngredient : userIngredients)
                user_inventory.put(userIngredient.getIngredientID(), userIngredient.getQuantity());

            // do 1 query to get the info needed from the recipe_ingredients & recipes tables
            ResultSet rs = statement.executeQuery("SELECT R.name, R.instruction, RI.recipe_id, " +
                                                        "group_concat( RI.ingredient_id ) as ingredient_ids, " +
                                                        "group_concat( RI.ingredient_quantity ) as ingredient_quantities " +
                                                        "FROM recipe_ingredients RI, recipes R " +
                                                        "WHERE RI.recipe_id = R.id " +
                                                        "GROUP BY R.id");

            // do this for every row returned from query (aka every recipe)
            outerloop:
            while(rs.next())
            {
                // store the group of ingredient_ids into an array
                String ingredient_ids_array = rs.getString("ingredient_ids");
                String[] ingredient_ids = ingredient_ids_array.split(",");

                // store the group of ingredient_quantities into an array
                String required_quantities_array = rs.getString("ingredient_quantities");
                String[] required_quantities = required_quantities_array.split(",");

                // create a HashMap for the current recipe's required ingredients
                HashMap<Integer, Integer> required_ingredients = new HashMap<>();

                // populate the HashMap with key = ingredient_id / value = ingredient_quantity
                for (int i = 0; i < ingredient_ids.length; i++)
                    required_ingredients.put(Integer.parseInt(ingredient_ids[i]), Integer.parseInt(required_quantities[i]));

                // iterate through the recipes HashMap
                for (Map.Entry<Integer, Integer> entry : required_ingredients.entrySet())
                {
                    // if the user inventory doesn't contain a required recipe ingredient
                    // this recipe cannot be satisfied, go to next recipe
                    if (!user_inventory.containsKey(entry.getKey()))
                        continue outerloop;

                    // if the user's quantity of said ingredient is less than the required amount for the recipe
                    // this recipe cannot be satisfied, go to next recipe
                    if (user_inventory.get(entry.getKey()) <  entry.getValue())
                        continue outerloop;
                }

                // ** IF WE GET HERE, THE RECIPE CAN BE ADDED TO satisfiedRecipes **
                // get the info we need for a Recipe object
                int recipe_id = rs.getInt("recipe_id");
                String recipe_name = rs.getString("name");
                String recipe_instruction = rs.getString("instruction");

                // add a new Recipe object to the recipes list
                satisfiedRecipes.add(new Recipe(recipe_id, recipe_name, recipe_instruction));

            }

            // return the list of satisfiable recipes
            return satisfiedRecipes;
        }

        catch (SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }


} // end of RecipeDAO class
