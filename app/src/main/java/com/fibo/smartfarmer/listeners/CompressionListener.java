package com.fibo.smartfarmer.listeners;

import android.net.Uri;

public interface CompressionListener {
    void complete(Uri newUri);

   default void onError(String message){

   }
}
