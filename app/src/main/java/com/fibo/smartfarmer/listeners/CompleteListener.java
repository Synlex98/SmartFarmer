package com.fibo.smartfarmer.listeners;

import com.google.firebase.auth.PhoneAuthOptions;

public interface CompleteListener {
    default void onComplete(){

    }

    default void onError(String message){

    }

}
