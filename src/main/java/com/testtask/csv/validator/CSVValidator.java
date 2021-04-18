package com.testtask.csv.validator;

import java.util.Arrays;

public class CSVValidator {
    public boolean isValid(String[] row) {

        return Arrays.asList(row).stream().noneMatch(String::isBlank);
    }
}
