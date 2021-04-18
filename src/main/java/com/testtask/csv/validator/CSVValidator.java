package com.testtask.csv.validator;

import java.util.Arrays;

public class CSVValidator {
    public boolean isValid(String[] row) {

        return Arrays.stream(row).noneMatch(String::isBlank);
    }
}
