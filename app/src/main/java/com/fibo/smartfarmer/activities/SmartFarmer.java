package com.fibo.smartfarmer.activities;

import android.app.Application;

import com.fibo.smartfarmer.BuildConfig;
import com.google.firebase.messaging.FirebaseMessaging;

import timber.log.Timber;

public class SmartFarmer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());

        }

        FirebaseMessaging.getInstance().subscribeToTopic("chat");
    }
}
