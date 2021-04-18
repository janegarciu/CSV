package com.testtask.csv.model;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private byte[] image;
    private String paymentMethod;
    private String price;
    private boolean isAuthorized;
    private boolean isEmployed;
    private String country;

    public Person(String firstName, String lastName, String email, String gender, byte[] image, String price, String paymentMethod, boolean isAuthorized, boolean isEmployed, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.image = image;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.isAuthorized = isAuthorized;
        this.isEmployed = isEmployed;
        this.country = country;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public boolean isEmployed() {
        return isEmployed;
    }

    public void setEmployed(boolean isEmployed) {
        this.isEmployed = isEmployed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
