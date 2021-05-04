package iCook.Model.DatabaseAccess;

import iCook.Model.*;

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
 * @version 5/3/2021
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
     * @param owner_id the owner's id so we can include their modified recipes in the list
     * @return an ArrayList containing Recipe objects satisfiable to a user
     */
    public ArrayList<Recipe> getSatisfiedRecipes(ArrayList<UserIngredient> userIngredients, int owner_id)
    {
        try {
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
                                                        "AND (owner_id IS NULL OR owner_id = '"+owner_id+"') " +
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
     * Performs a SQL statement to return an Arraylist of RecipeIF objects representing all
     * of the entries in the recipes table.
     *
     * @return an ArrayList<RecipeIF>
     */
    public ArrayList<RecipeIF> getAllSystemRecipes() {
        try {
            // create a new statement to execute a query
            Statement statement = this.createStatement();

            // store recipes in this array list
            ArrayList<RecipeIF> recipes = new ArrayList<>();

            // sql query here to get all recipes
            ResultSet rs = statement.executeQuery("SELECT * FROM recipes");
            while (rs.next()) {
                // info we need for each recipe
                int recipe_id = rs.getInt("id");
                String recipe_name = rs.getString("name");
                String recipe_instructions = rs.getString("instruction");
                ArrayList<RecipeIngredient> ingredients = new ArrayList<>();
                boolean is_published = rs.getBoolean("published");

                // create a new statement to execute a query
                Statement statement2 = this.createStatement();

                // sql query here to get all the ingredients
                ResultSet rs2 = statement2.executeQuery("SELECT RI.id, RI.ingredient_quantity, I.id, I.name, I.unit_of_measure " +
                                                            "FROM recipe_ingredients RI, ingredients I " +
                                                            "WHERE recipe_id = '"+recipe_id+"' " +
                                                            "AND RI.ingredient_id = I.id");
                while (rs2.next()) {
                    // info we need for each recipe ingredient
                    int ri_id = rs2.getInt("RI.id");
                    int ri_qty = rs2.getInt("RI.ingredient_quantity");
                    int ing_id = rs2.getInt("I.id");
                    String ing_name = rs2.getString("I.name");
                    String ing_unit = rs2.getString("I.unit_of_measure");

                    // add a new RecipeIngredient object to the recipe's list of ingredients
                    ingredients.add(new RecipeIngredient(ri_id, new Ingredient(ing_id, ing_name, ing_unit), ri_qty));
                }

                // add the new RecipeIF object to the array list of recipes
                recipes.add(new Recipe(recipe_id, recipe_name, recipe_instructions, ingredients, is_published));
            }

            // return the array list
            return recipes;
        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    /**
     * Performs a SQL statement to insert a new recipe into the recipes table.
     * This newly added recipe will be a clone from an existing recipe.
     * Its ingredients shall remain the same but not its name or instructions.
     * This ingredient is owned by 1 user only.
     *
     * @param cloned_recipe the recipe to be inserted into the recipes table
     * @param owner_id owner of this cloned recipe
     */
    public void insertClonedRecipe(RecipeIF cloned_recipe, int owner_id) {
        try {
            // create a new statement to execute a query
            Statement statement = this.createStatement();

            // info needed to insert into the recipes table
            int original_recipe_id = cloned_recipe.getRecipeID();
            String name = cloned_recipe.getRecipeName();
            String instructions = cloned_recipe.getInstructions();
            int isPublished = cloned_recipe.isPublished() ? 1 : 0;

            // insert recipe data into the recipes table
            statement.executeUpdate("INSERT INTO recipes " +
                    "VALUES(NULL, '" + name + "', '" + instructions + "', '" + isPublished + "', '" + owner_id + "' )", Statement.RETURN_GENERATED_KEYS);

            // get the new entry id
            int cloned_recipe_id = 0;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                cloned_recipe_id = rs.getInt(1);
            }
            rs.close();

            // clone the recipe_ingredients that correspond to the original recipe (these entries will be linked to the new cloned recipe id)
            statement.executeUpdate("INSERT INTO recipe_ingredients (ingredient_id, ingredient_quantity, recipe_id) " +
                                        "SELECT ingredient_id, ingredient_quantity, '"+cloned_recipe_id+"' " +
                                        "FROM recipe_ingredients " +
                                        "WHERE recipe_id = '"+original_recipe_id+"' ");

        }

        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs a SQL statement to return a Vector of all iCook's recipes.
     * Info we are retrieving from each recipe: id, name, published
     *
     * @return a Vector of vectors (each inner vector contains a recipe's info).
     */
    public Vector<Vector> getRecipes() {
        try {
            // create a new statement to execute a query
            Statement statement = this.createStatement();

            // store recipes in this Vector
            Vector<Vector> recipes = new Vector<>();

            // sql query here to get all system ingredients (not owned by anyone)
            ResultSet rs = statement.executeQuery("SELECT id, name, published " +
                                                        "FROM recipes WHERE owner_id IS NULL");
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
     * @return the corresponding Recipe Object of the id passed in
     */
    public Recipe getRecipe(int id) {
        try {
            Statement statement = this.createStatement();
            ResultSet rs = statement.executeQuery("SELECT RI.id as 'RI.id', I.id, I.name as 'I.name', I.unit_of_measure, RI.ingredient_quantity, R.name, R.instruction, R.published " +
                                                        "FROM ingredients I, recipe_ingredients RI, recipes R " +
                                                        "WHERE R.id = RI.recipe_id " +
                                                        "AND RI.ingredient_id = I.id " +
                                                        "AND R.id = '" + id + "' ");
            // if the recipe has ingredients, continue below
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
            }

            // safety check to see if the recipe exists without ingredients
            else {
                ResultSet rs2 = statement.executeQuery("SELECT * FROM recipes WHERE id = '"+id+"' ");

                // if so, return a recipe object with no ingredient list
                if (rs2.next()) {
                    // items we need for the final object to be returned
                    String recipe_name = rs2.getString("name");
                    String instructions = rs2.getString("instruction");
                    boolean isPublished = rs2.getBoolean("published");

                    // return the desired Recipe object
                    return new Recipe(id, recipe_name, instructions, new ArrayList<>(), isPublished);
                }

                // else there is no such recipe, return null
                else {
                    return null;
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    /**
     * Performs a SQL statement to insert a new Recipe into the recipes table
     * as well as other SQL statements to insert its ingredients into the recipe_ingredients table.
     *
     * @param recipe to be inserted into the database
     */
    public void addNewRecipe(RecipeIF recipe) {
        try {
            Statement statement = this.createStatement();

            // info needed to insert into the recipes table
            String name = recipe.getRecipeName();
            String instructions = recipe.getInstructions();
            int isPublished = recipe.isPublished() ? 1 : 0;

            // insert recipe data into the recipes table
            statement.executeUpdate("INSERT INTO recipes " +
                                        "VALUES(NULL, '"+name+"', '"+instructions+"', '"+isPublished+"', NULL)", Statement.RETURN_GENERATED_KEYS);

            // get the newly added recipe's id
            int new_recipe_id = 0;
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                new_recipe_id = rs.getInt(1);
            }
            rs.close();

            // for each RecipeIngredient in the recipe, insert it into the recipe_ingredients table
            for (RecipeIngredient ing : recipe.getIngredients()) {
                int ing_id = ing.getIngredient().getIngredientID();
                int ing_qty = ing.getQuantity();

                statement.executeUpdate("INSERT INTO recipe_ingredients " +
                                            "VALUES(NULL, '"+ing_id+"', '"+ing_qty+"', '"+new_recipe_id+"' )");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs a SQL statement to update the Recipe in the recipes table
     * as well as other SQL statements to update its ingredients in the recipe_ingredients table.
     *
     * @param recipe to be updated in the database
     */
    public void updateRecipe(RecipeIF recipe) {
        try {
            Statement statement = this.createStatement();

            // info needed to perform queries
            int recipe_id = recipe.getRecipeID();
            String name = recipe.getRecipeName();
            String instructions = recipe.getInstructions();
            int isPublished = recipe.isPublished() ? 1 : 0;
            ArrayList<RecipeIngredient> ingredients = recipe.getIngredients();

            // update recipe data in the recipes table
            statement.executeUpdate("UPDATE recipes " +
                                        "SET name = '"+name+"', instruction = '"+instructions+"', published = '"+isPublished+"'" +
                                        "WHERE id = '"+recipe_id+"' ");

            // wipe the recipe_ingredients table of its rows containing the recipe's id
            statement.executeUpdate("DELETE FROM recipe_ingredients WHERE recipe_id = '"+recipe_id+"' ");

            // insert the recipe's ingredients into the recipe_ingredients table (unless qty is 0)
            for (RecipeIngredient ing : ingredients) {
                if (ing.getQuantity() != 0) {
                    statement.executeUpdate("INSERT INTO recipe_ingredients " +
                            "VALUES(NULL, '" + ing.getIngredient().getIngredientID() + "', '" + ing.getQuantity() + "', '" + recipe_id + "' )");
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Performs a SQL statement to get the URL of a recipe.
     *
     * @param recipe_ID the id of the recipe we are interested in.
     * @return the URL of the recipe's image. Returns null if there is no URL.
     */
    public String getRecipeImageURL(int recipe_ID) {
        try {
            Statement statement = this.createStatement();

            // query the db to get the recipe's image_path
            ResultSet rs = statement.executeQuery("SELECT image_path from recipes WHERE id = '"+recipe_ID+"' ");
            if (rs.next())
                return rs.getString("image_path");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }


} // end of RecipeDAO class
