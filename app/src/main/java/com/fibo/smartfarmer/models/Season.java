package com.fibo.smartfarmer.models;

public class Season {
    private int seasonId;
    private String noOfFarms,farmSize,stock,currentStage;

    public Season(String noOfFarms, String farmSize, String stock, String currentStage) {
        this.noOfFarms = noOfFarms;
        this.farmSize = farmSize;
        this.stock = stock;
        this.currentStage = currentStage;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getNoOfFarms() {
        return noOfFarms;
    }

    public void setNoOfFarms(String noOfFarms) {
        this.noOfFarms = noOfFarms;
    }

    public String getFarmSize() {
        return farmSize;
    }

    public void setFarmSize(String farmSize) {
        this.farmSize = farmSize;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(String currentStage) {
        this.currentStage = currentStage;
    }
}
