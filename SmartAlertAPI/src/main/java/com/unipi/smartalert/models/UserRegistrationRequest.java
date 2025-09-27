package com.unipi.smartalert.models;

public class UserRegistrationRequest {
    private String firstName;
    private String email;
    private String password;
    private String lastName;

    public UserRegistrationRequest(String firstName, String lastName,
                                   String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
