package com.fibo.smartfarmer.listeners;

import android.view.View;

public interface RecyclerListener {
    void onClick(View view,int position);
    default void onOptions(View view,int position){

    }

    default void onTips(View v, int position){

    }

    default void onRefresh(){

    }
}
