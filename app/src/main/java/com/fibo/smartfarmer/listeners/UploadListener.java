package com.fibo.smartfarmer.listeners;

import com.fibo.smartfarmer.models.Result;

public interface UploadListener {
    void onData(Result result);
    default void onFailure(String message){

    }
}
