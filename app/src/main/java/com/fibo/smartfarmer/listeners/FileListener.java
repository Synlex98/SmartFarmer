package com.fibo.smartfarmer.listeners;

public interface FileListener {
    void fileAvailable();

    void onError(String message);

    default void onReadComplete(String content){

    }
}
