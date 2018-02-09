package com.bneuts.tarotscorecard.model;

/**
 * Created by Benoît Neuts on 05/02/2018.
 * this class will hold User which will be managed later on
 * TODO : create users collection in FireStore
 */

public class User {

    private String userID;
        public String getUserID() { return userID; }
        public void setUserID(String userID) { this.userID = userID; }

    private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

    public User() { }

    public User(String name) {
        this.name = name;
    }
}
