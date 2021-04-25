package iCook.Model.DatabaseAccess;

import iCook.Model.Ingredient;
import iCook.Model.Recipe;
import iCook.Model.RecipeIngredient;
import iCook.Model.UserIngredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


/**
 * DAO class for the recipes && recipe_ingredients table in iCook's database.
 * Every method requires a try and catch for a SQLException.
 *
 * @author Team 2
 * @version 4/25/2021
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
            ResultSet rs = statement.executeQuery("SELECT R.name, R.instruction, RI.recipe_id, R.published, " +
                                                        "group_concat( RI.ingredient_id ) as ingredient_ids, " +
                                                        "group_concat( RI.ingredient_quantity ) as ingredient_quantities, " +
                                                        "group_concat( I.name ) as ingredient_names, " +
                                                        "group_concat( I.unit_of_measure ) as ingredient_units " +
                                                        "FROM recipe_ingredients RI, recipes R, ingredients I " +
                                                        "WHERE RI.recipe_id = R.id " +
                                                        "AND I.id = RI.ingredient_id " +
                                                        "AND R.published = 1 " +
                                                        "GROUP BY R.id");

            // do this for every row returned from query (aka every recipe)
            outerloop:
            while(rs.next())
            {
                ArrayList<RecipeIngredient> ingredients = new ArrayList<>();

                // store the group of ingredient_ids into an array
                String ingredient_ids_array = rs.getString("ingredient_ids");
                String[] ingredient_ids = ingredient_ids_array.split(",");

                // store the group of ingredient_quantities into an array
                String required_quantities_array = rs.getString("ingredient_quantities");
                String[] required_quantities = required_quantities_array.split(",");

                // store the group of ingredient_names into an array
                String ingredient_names_array = rs.getString("ingredient_names");
                String[] ingredient_names = ingredient_names_array.split(",");

                // store the group of ingredient_units into an array
                String ingredient_units_array = rs.getString("ingredient_units");
                String[] ingredient_units = ingredient_units_array.split(",");

                // ************************************************
                // 1ST MAP - key: ingredient_id // value: quantity
                // ************************************************
                // create a HashMap for the current recipe's required ingredients
                HashMap<Integer, Integer> required_ingredients = new HashMap<>();
                // populate the HashMap with key = ingredient_id / value = ingredient_quantity
                for (int i = 0; i < ingredient_ids.length; i++)
                    required_ingredients.put(Integer.parseInt(ingredient_ids[i]), Integer.parseInt(required_quantities[i]));

                // ************************************************
                // 2nd MAP - key: ingredient_id // value: name
                // ************************************************
                // create a HashMap for the current recipe's required ingredient's names
                HashMap<Integer, String> ingredient_names_map = new HashMap<>();
                // populate the HashMap with key = ingredient_id / value = ingredient_quantity
                for (int i = 0; i < ingredient_names.length; i++)
                    ingredient_names_map.put(Integer.parseInt(ingredient_ids[i]), ingredient_names[i]);

                // *******************************************************
                // 3rd MAP - key: ingredient_id // value: unit_of_measure
                // *******************************************************
                // create a HashMap for the current recipe's required ingredient's unit_of_measure
                HashMap<Integer, String> ingredient_units_map = new HashMap<>();
                // populate the HashMap with key = ingredient_id / value = ingredient_quantity
                for (int i = 0; i < ingredient_units.length; i++)
                    ingredient_units_map.put(Integer.parseInt(ingredient_ids[i]), ingredient_units[i]);

                // iterate through the recipes HashMap
                for (Map.Entry<Integer, Integer> entry : required_ingredients.entrySet())
                {
                    // create the Ingredient that will be stored in the new RecipeIngredient being added to the recipe's list
                    int key = entry.getKey();
                    Ingredient ingredient = new Ingredient(key, ingredient_names_map.get(key), ingredient_units_map.get(key));

                    // add a new recipe ingredient object to the array list of all the recipe's ingredients
                    ingredients.add(new RecipeIngredient(entry.getKey(), ingredient, entry.getValue()));

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
                boolean isPublished = rs.getBoolean("published");

                // add a new Recipe object to the recipes list
                satisfiedRecipes.add(new Recipe(recipe_id, recipe_name, recipe_instruction, ingredients, isPublished));
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


    /**
     * Performs a SQL statement to return a Vector of all iCook's recipes.
     * Info we are retrieving from each recipe: id, name, published
     *
     * @return a Vector containing vectors (each inner vector contains a recipe's info).
     */
    public Vector<Vector> getRecipes() {
        try {
            // create a new statement to execute a query
            Statement statement = this.createStatement();

            // store recipes in this array list
            Vector<Vector> recipes = new Vector<>();

            // sql query here
            ResultSet rs = statement.executeQuery("SELECT id, name, published " +
                                                        "FROM recipes");
            while (rs.next()) {
                // info we are interested in
                Vector<String> recipe = new Vector<>();
                int id;
                String name;
                String published;

                // get the current recipe's id, name, published status
                id = rs.getInt("id");
                name = rs.getString("name");
                if (rs.getBoolean("published")) {
                    published = "\u2713";
                } else {
                    published = "";
                }

                recipe.addElement(String.valueOf(id));
                recipe.addElement(name);
                recipe.addElement(published);


                // add the recipe to the array list of recipes
                recipes.addElement(recipe);
            }

            // return the vector of vectors
            return recipes;
        }


        catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    /**
     * Performs a SQL statement to return Recipe given the recipe's id
     *
     * @param id the id of the recipe we want to get an object of
     * @return a Recipe Object
     */
    public Recipe getRecipe(int id) {
        try {
            Statement statement = this.createStatement();
            ResultSet rs = statement.executeQuery("SELECT RI.id as 'RI.id', I.id, I.name as 'I.name', I.unit_of_measure, RI.ingredient_quantity, R.name, R.instruction, R.published " +
                                                        "FROM ingredients I, recipe_ingredients RI, recipes R " +
                                                        "WHERE R.id = RI.recipe_id " +
                                                        "AND RI.ingredient_id = I.id " +
                                                        "AND R.id = '" + id + "' ");
            // make sure the recipe id exists
            if (rs.next()) {

                // items we need for the final object to be returned
                String recipe_name = rs.getString("name");
                String instructions = rs.getString("instruction");
                boolean isPublished = rs.getBoolean("published");
                ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
                rs.beforeFirst();   // reset the cursor to beginning

                // loop through the query results
                while (rs.next()) {
                    int recipe_ingredient_id = rs.getInt("RI.id");
                    int ingredient_quantity = rs.getInt("ingredient_quantity");

                    // info for the Ingredient object held within the RecipeIngredient object
                    int ingredient_id = rs.getInt("id");
                    String ingredient_name = rs.getString("I.name");
                    String ingredient_unit = rs.getString("unit_of_measure");
                    Ingredient ingredient = new Ingredient(ingredient_id, ingredient_name, ingredient_unit);

                    // add a new RecipeIngredient to the recipe's array list of RecipeIngredients
                    ingredients.add(new RecipeIngredient(recipe_ingredient_id, ingredient, ingredient_quantity));
                }

                // return the desired Recipe object
                return new Recipe(id, recipe_name, instructions, ingredients, isPublished);

            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


} // end of RecipeDAO class
