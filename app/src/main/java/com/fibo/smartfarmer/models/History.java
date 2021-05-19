package com.fibo.smartfarmer.models;

public class History {
    private int seasonId;
    private String lastSprayDay,lastPestDay;

    public History(int seasonId, String lastSprayDay, String lastPestDay) {
        this.seasonId = seasonId;
        this.lastSprayDay = lastSprayDay;
        this.lastPestDay = lastPestDay;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public String getLastSprayDay() {
        return lastSprayDay;
    }

    public String getLastPestDay() {
        return lastPestDay;
    }
}
