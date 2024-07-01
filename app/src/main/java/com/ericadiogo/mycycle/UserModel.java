package com.ericadiogo.mycycle;

public class UserModel {
    private String id, firstName, lastName, email, password,lastPeriod;
    private int pLength, cLength, weight;

    public UserModel(String id, String firstName, String lastName, String email, String password, int pLength, int cLength, int weight,String lastPeriod) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pLength = pLength;
        this.weight = weight;
        this.cLength = cLength;
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

    public String getLastName() { return lastName; }

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

    public int setpLength(int pLength) { this.pLength = pLength;
        return pLength;
    }

    public int getWeight() { return weight; }

    public void setWeight(int weight) { this.weight = weight; }

    public String getLastPeriod() { return lastPeriod; }

    public void setLastPeriod(String lastPeriod) { this.lastPeriod = lastPeriod; }

    public int getcLength() { return cLength; }

    public void setcLength(int cLength) {
        this.cLength = cLength;
    }
}
