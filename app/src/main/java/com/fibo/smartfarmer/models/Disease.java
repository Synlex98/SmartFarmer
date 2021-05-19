package com.fibo.smartfarmer.models;

import java.util.List;

public class Disease {
    private String name;
    private List<String> controlList;

    public Disease(String name, List<String> controlList) {
        this.name = name;
        this.controlList = controlList;
    }

    public List<String> getControlList() {
        return controlList;
    }

    public String getName() {
        return name;
    }
}
