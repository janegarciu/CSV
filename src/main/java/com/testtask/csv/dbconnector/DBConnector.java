package com.testtask.csv.dbconnector;
import java.sql.*;

public class DBConnector {
    // SQLite connection string
    private static String url = "jdbc:sqlite::memory:C://sqlite/db/csv.db";
    public static void createNewTable() {

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS personsdata (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	first_name text NOT NULL,\n"
                + "	last_name text NOT NULL,\n"
                + "	email text NOT NULL UNIQUE ,\n"
                + "	gender text NOT NULL,\n"
                + "	data text NOT NULL,\n"
                + "	payment_details text NOT NULL,\n"
                + "	sum text NOT NULL,\n"
                + "	boolean_1 text NOT NULL,\n"
                + "	boolean_2 text NOT NULL,\n"
                + "	country text NOT NULL,\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insert(String firstName, String lastName, String email, String gender, byte[] image, String price, String paymentMethod, boolean isAuthorized, boolean isEmployed, String country) {
        String sql = "INSERT INTO personsdata(firstName, lastName, email, gender, image, price, paymentMethod, isAuthorized, isEmployed, country) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, gender);
            pstmt.setBytes(5, image);
            pstmt.setString(6, price);
            pstmt.setString(7, paymentMethod);
            pstmt.setBoolean(8, isAuthorized);
            pstmt.setBoolean(9, isEmployed);
            pstmt.setString(10, country);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
