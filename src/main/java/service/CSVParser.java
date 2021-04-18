package service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.testtask.csv.mapper.CSVMapper;
import com.testtask.csv.model.Person;
import com.testtask.csv.validator.CSVValidator;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CSVParser {
//    public static void main(String[] args) throws Exception {
//        List<Person> personList = readPersonsFromCSV("Interview-task-data-osh (2).csv"); // let's print all the person read from CSV file for (Book b : books) { System.out.println(b); }
//
//
//    }

    private static void readPersonsFromCSV(String fileName) throws IOException {
        CSVValidator csvRowValidator = new CSVValidator();
        CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fileName)));
        List<String[]> allRows = csvReader.readAll();
        List<String[]> invalidRows = allRows.stream().filter(row -> !csvRowValidator.isValid(row)).collect(Collectors.toList());

        List<Person> validRows = allRows.stream().filter(row -> csvRowValidator.isValid(row)).map(row -> CSVMapper.mapPerson(row)).collect(Collectors.toList());

        invalidRows.stream().forEach(row -> {
            try {
                write(getCsvWriter(), row);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private static void write(CSVWriter csvWriter, String[] it) throws IOException {
        csvWriter.writeNext(it, false);
        csvWriter.flush();
    }

    private static CSVWriter getCsvWriter() throws IOException {
        String path = "/Users/janegarciu/Desktop/CSV/src/main/resources/export/";
        return new CSVWriter(new BufferedWriter(new FileWriter(new File(path + LocalDateTime.now().toString() + ".csv"))));
    }


}
