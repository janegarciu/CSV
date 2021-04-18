package com.testtask.csv;

import com.testtask.csv.service.CSVParser;

import java.io.IOException;


import static com.testtask.csv.dbconnector.DBConnector.createNewTable;

public class Application {
    public static void main(String[] args) throws IOException {
        createNewTable();
        CSVParser parser = new CSVParser();
        parser.readPersonsFromCSV("Interview-task-data-osh (2).csv");
    }
}
