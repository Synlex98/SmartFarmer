package com.fibo.smartfarmer.auth;

public class UserBuilder {
    private final String userName;
    private final int phoneNumber;
    private final String location;
    private final String password;

    public UserBuilder(String userName, int phoneNumber, String location, String password) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.password = password;
    }


    static class Builder {
        String userName;
        int phoneNumber;
        String location;
        String password;

        public String getUserName() {
            return userName;
        }

        public Builder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public int getPhoneNumber() {
            return phoneNumber;
        }

        public Builder setPhoneNumber(int phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public String getLocation() {
            return location;
        }

        public Builder setLocation(String location) {
            this.location = location;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder build() {
            return new UserBuilder(this.userName, this.phoneNumber, this.location, this.password);
        }

    }
}
