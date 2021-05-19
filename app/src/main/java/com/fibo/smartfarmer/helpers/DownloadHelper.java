package com.fibo.smartfarmer.helpers;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.fibo.smartfarmer.listeners.DownloadListener;
import com.fibo.smartfarmer.utils.Constants;
import com.fibo.smartfarmer.utils.Urls;

import timber.log.Timber;

public class DownloadHelper {

    public static synchronized void downLoadDiseaseFile( DownloadListener listener){

AndroidNetworking.download(Urls.DISEASE_JSON_URL,Constants.FILES_STORAGE_DIR,Constants.DISEASE_FILE)
        .setPriority(Priority.HIGH)
        .build()
        .setDownloadProgressListener(new DownloadProgressListener() {
            @Override
            public void onProgress(long bytesDownloaded, long totalBytes) {
                float percentage=(float)(bytesDownloaded/totalBytes)*100;
                listener.onProgress(percentage+"%");
            }
        })
        .startDownload(new com.androidnetworking.interfaces.DownloadListener() {
            @Override
            public void onDownloadComplete() {
                listener.onDownloadComplete();
            }

            @Override
            public void onError(ANError anError) {
Timber.e(anError);
            }
        });
            }

    public static synchronized void downLoadFertilizerFile( DownloadListener listener){

        AndroidNetworking.download(Urls.FERTILIZER_JSON_URL,Constants.FILES_STORAGE_DIR,Constants.FERTILIZER_FILE)
                .setPriority(Priority.HIGH)
                .build()
                .setDownloadProgressListener(new DownloadProgressListener() {
                    @Override
                    public void onProgress(long bytesDownloaded, long totalBytes) {
                        float percentage=(float)(bytesDownloaded/totalBytes)*100;
                        listener.onProgress(percentage+"%");
                    }
                })
                .startDownload(new com.androidnetworking.interfaces.DownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        listener.onDownloadComplete();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Timber.e(anError);
                    }
                });
    }




}
