package com.testtask.csv.dbconnector;

import com.testtask.csv.model.Person;
import java.sql.*;

public class DBConnector {

    private final String url = "jdbc:sqlite::memory:";
    private Connection connection = null;

    // Opens connection to in-memory database
    public void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url);
        }
    }

    // Closes connection to database
    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void createNewTable() {

        // SQL statement for creating a new table
        String sql = ("CREATE TABLE IF NOT EXISTS personal"
                + "	(firstName TEXT,"
                + "	lastName TEXT,"
                + "	email TEXT,"
                + "	gender TEXT,"
                + "	image TEXT,"
                + " price TEXT,"
                + "	paymentMethod TEXT,"
                + "	isAuthorized TEXT,"
                + "	isEmployed TEXT,"
                + "	country TEXT)");

        try (Statement stmt = connection.createStatement()) {
            // creates a new table
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //insert() method inserts person to a database
    public void insert(Person person) {
        String sql = "INSERT INTO personal(firstName, lastName, email, gender, image, price, paymentMethod, isAuthorized, isEmployed, country) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement prepStmt = connection.prepareStatement(sql);
            prepStmt.setString(1, person.getFirstName());
            prepStmt.setString(2, person.getLastName());
            prepStmt.setString(3, person.getEmail());
            prepStmt.setString(4, person.getGender());
            prepStmt.setBytes(5, person.getImage());
            prepStmt.setString(6, person.getPrice());
            prepStmt.setString(7, person.getPaymentMethod());
            prepStmt.setBoolean(8, person.isAuthorized());
            prepStmt.setBoolean(9, person.isEmployed());
            prepStmt.setString(10, person.getCountry());
            prepStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
    //testDB() method is created to verify if successful rows are inserted to a database
    public void testDB() {
        try {
            final Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM personal;");

            ResultSetMetaData rsmd = results.getMetaData();
            int numColumns = rsmd.getColumnCount();

            while (results.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    System.out.print("Column string:" + results.getString(i) + " ");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
