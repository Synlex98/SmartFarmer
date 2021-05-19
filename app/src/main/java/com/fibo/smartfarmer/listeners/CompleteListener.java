package com.fibo.smartfarmer.listeners;

import com.fibo.smartfarmer.models.Disease;

import java.io.File;
import java.util.List;

public interface CompleteListener {
    default void onComplete(){

    }

    default void onError(String message){

    }

    default void onComplete(File file){

    }

    default void onComplete(String message){

    }

    default void onComplete(List<Disease> diseaseList){

    }

}
