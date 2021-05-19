package com.fibo.smartfarmer.models;

public class User {
    String userName;
    String phoneNumber;
    String password;
    String location;
    String userEmail;

   private static User user;

    public static User getInstance() {
        if (user==null){
            user=new User();
        }
        return user;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;

    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getLocation() {
        return location;
    }


}


