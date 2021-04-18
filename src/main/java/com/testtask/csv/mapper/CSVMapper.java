package com.testtask.csv.mapper;

import com.testtask.csv.model.Person;

public class CSVMapper {
    public static Person mapPerson(String[] data) {
        String firstName = data[0];
        String lastName = data[1];
        String email = data[2];
        String gender = data[3];
        byte[] image = data[4].getBytes();
        String price = data[6];
        String paymentMethod = data[5];
        boolean isAuthorized = Boolean.parseBoolean(data[7]);
        boolean isEmployed = Boolean.parseBoolean(data[8]);
        String country = data[9];
        return new Person(firstName, lastName, email, gender, image, price, paymentMethod, isAuthorized, isEmployed, country);
    }
}
