package com.testtask.csv.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.testtask.csv.dbconnector.DBConnector;
import com.testtask.csv.logger.LogHandler;
import com.testtask.csv.mapper.CSVMapper;
import com.testtask.csv.model.Person;
import com.testtask.csv.validator.CSVValidator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class CSVParser {
    private DBConnector connector;

    public CSVParser(DBConnector connector) {
        this.connector = connector;
    }

    /* fileName defines a path to test csv data file location;
     * this method reads data from Test.csv file using opencsv library and defines further actions on each row
     */
    public void readPersonsFromCSV(String fileName) throws IOException {
        CSVValidator csvRowValidator = new CSVValidator();
        Logger logger = LogHandler.createFileHandler(); //creates file handler for a logger
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fileName)));

        List<String[]> allRows = csvReader.readAll();
        logger.info("Number of records received:" + allRows.size()); //logs number of received records to a file

        List<String[]> invalidRows = allRows.stream().filter(row -> !csvRowValidator.isValid(row)).collect(Collectors.toList()); //validates and collects invalid rows

        List<Person> validRows = allRows.stream().filter(row -> csvRowValidator.isValid(row)).map(row -> CSVMapper.mapPerson(row)).collect(Collectors.toList()); //validates and collects valid rows
        logger.info("Number of records successful:" + validRows.size()); //logs number of successful records to a file
        validRows.forEach(row -> {
            connector.insert(row);
        });
        logger.info("Number of records failed:" + invalidRows.size()); //logs number of failed records to a file

        int rowNumber=0;

        for (final String[] row : invalidRows) {
            writeToCsvWriter(row, ++rowNumber);
        }
    }

    private void writeToCsvWriter(String[] row, int index) throws IOException {
        final StringBuilder pathBuilder = getPath(index);

        try (CSVWriter csvWriter = getCsvWriter(pathBuilder)) {
            csvWriter.writeNext(row, false);
        }
    }

    //getCsvWriter method returns CSVWriter with a new .csv file to write failed record in
    private CSVWriter getCsvWriter(StringBuilder pathBuilder) throws IOException {
        return new CSVWriter(new BufferedWriter(new FileWriter(new File(pathBuilder.toString() + ".csv"))));
    }

    //path to a directory where file will be created
    private StringBuilder getPath(int rowNumber) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss_nn");

        final StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("C:\\project_test\\src\\main\\resources\\export\\");
        pathBuilder.append(rowNumber + "_");
        pathBuilder.append(LocalDateTime.now().format(format));
        return pathBuilder;
    }
}