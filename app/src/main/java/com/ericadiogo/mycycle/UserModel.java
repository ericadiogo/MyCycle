package com.ericadiogo.mycycle;

import java.util.Date;

public class UserModel {
    private String id, firstName, lastName, email, password;
    private int pLength, weight;
    private Date lastPeriod;

    public UserModel(String id, String firstName, String lastName, String email, String password, int pLength, int weight,Date lastPeriod) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pLength = pLength;
        this.weight = weight;
        this.lastPeriod = lastPeriod;
    }

    public UserModel(){

    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getpLength() { return pLength; }

    public void setpLength(int pLength) { this.pLength = pLength; }

    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = weight; }

    public Date getLastPeriod() { return lastPeriod; }

    public void setLastPeriod(Date lastPeriod) { this.lastPeriod = lastPeriod; }
}
