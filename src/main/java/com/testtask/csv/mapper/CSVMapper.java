package com.testtask.csv.mapper;

import com.testtask.csv.model.Person;

import java.util.UUID;

public class CSVMapper {
    public static Person mapPerson(String[] data) {
        Person person = new Person();
        person.setId(UUID.randomUUID());
        person.setFirstName(data[0]);
        person.setLastName(data[1]);
        person.setEmail(data[2]);
        person.setGender(data[3]);
        person.setImage(data[4].getBytes());
        person.setPaymentMethod(data[5]);
        person.setPrice(data[6]);
        person.setAuthorized(Boolean.parseBoolean(data[7]));
        person.setEmployed(Boolean.parseBoolean(data[8]));
        person.setCountry(data[9]);
        return person;
    }
}
