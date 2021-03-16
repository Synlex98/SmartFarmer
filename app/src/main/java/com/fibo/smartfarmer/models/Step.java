package com.fibo.smartfarmer.models;

public class Step {
    private int stepId, seasonId;
    private String stepStatus,stepName;

    public Step(int seasonId, String stepStatus, String stepName) {
        this.seasonId = seasonId;
        this.stepStatus = stepStatus;
        this.stepName = stepName;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
}
