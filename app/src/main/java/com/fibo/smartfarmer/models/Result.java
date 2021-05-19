package com.fibo.smartfarmer.models;

public class Result {
    private String status,disease,accuracy;

    public Result(String status, String disease, String accuracy) {
        this.status = status;
        this.disease = disease;
        this.accuracy = accuracy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
