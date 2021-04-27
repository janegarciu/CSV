package com.testtask.csv;

import com.testtask.csv.dbconnector.DBConnector;
import com.testtask.csv.service.CSVParser;
import java.io.IOException;
import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws IOException, SQLException {
        DBConnector connector = new DBConnector();
        CSVParser parser = new CSVParser(connector);
        connector.openConnection();
        connector.createNewTable();
        parser.readPersonsFromCSV("C:\\project_test\\src\\main\\resources\\Test.csv");
        connector.closeConnection();
        connector.testDB();
    }
}
