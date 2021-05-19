package com.fibo.smartfarmer.listeners;

import java.util.List;

public interface DownloadListener {
    default void onDownloadComplete(){

    }

    default void onFilesListLoaded(List<String> files){

    }

    default void onProgress(String s){

    };
}
