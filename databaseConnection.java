package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.*;


public class databaseConnection {

    private static final String username = "se370";
    private static final String password = "Team2!";
    private static final String database = "icook";
    private static final String url = "jdbc:mysql://izzy-se370.ca3u8x8hrfhy.us-west-1.rds.amazonaws.com:3306/" + database;

    public Statement statement;


    // Constructor
    databaseConnection() throws SQLException {
        // Register the Driver
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        // Getting the connection
        Connection connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connection established....");

        // Creating a statement object
        statement = connection.createStatement();
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
    {
        // create new database object
        databaseConnection database = new databaseConnection();

        // Insert an item to the database
        database.statement.execute("INSERT INTO test_izzy (first_name) VALUE ('test')");

        // Retrieving the data
        ResultSet resultSet = database.statement.executeQuery("SELECT COUNT(*) FROM test_izzy");
        resultSet.next();

        // Moving the cursor to the last row
        System.out.println("Table contains " + resultSet.getInt("COUNT(*)") + " rows");

        // frame stuff
        JFrame frame = new JFrame();
        frame.setContentPane(new userName().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
