package com.fibo.smartfarmer.listeners;

import java.util.List;

public interface ExpertListener {
    void onError(String message);

    default void oControl(List<String> controlList){

    }
}
