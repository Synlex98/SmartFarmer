package com.fibo.smartfarmer.listeners;

public interface MessageListener {
    void onSent();
    void onError(String message);
}
