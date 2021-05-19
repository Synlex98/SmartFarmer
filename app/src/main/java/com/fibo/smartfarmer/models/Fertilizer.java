package com.fibo.smartfarmer.models;

import java.util.List;

public class Fertilizer {
    private String ferName;
    private List<String> qualityList,areaSuitable;

    public Fertilizer(String ferName, List<String> qualityList,List<String> areaSuitable) {
        this.ferName = ferName;
        this.qualityList = qualityList;
        this.areaSuitable = areaSuitable;
    }

    public String getFerName() {
        return ferName;
    }

    public List<String> getQualityList() {
        return qualityList;
    }

    public List<String> getAreaSuitable() {
        return areaSuitable;
    }
}
