package com.testtask.csv.mapper;

import com.testtask.csv.model.Person;

import java.util.UUID;

public class CSVMapper {
    //maps row strings to a Person object fields
    public static Person mapPerson(String[] row) {
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setFirstName(row[0]);
        person.setLastName(row[1]);
        person.setEmail(row[2]);
        person.setGender(row[3]);
        person.setImage(row[4].getBytes());
        person.setPaymentMethod(row[5]);
        person.setPrice(row[6]);
        person.setAuthorized(Boolean.parseBoolean(row[7]));
        person.setEmployed(Boolean.parseBoolean(row[8]));
        person.setCountry(row[9]);
        return person;
    }
}
