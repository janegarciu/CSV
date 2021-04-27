package com.testtask.csv.validator;

import java.util.Arrays;

public class CSVValidator {
    //verifies if any of the strings in a row is empty
    public boolean isValid(String[] row) {

        return Arrays.stream(row).noneMatch(String::isBlank);
    }
}
