package com.fibo.smartfarmer.listeners;

public interface LocationFetchListener {
    void onFetch(String location);
    void onFailure(String error);
}
