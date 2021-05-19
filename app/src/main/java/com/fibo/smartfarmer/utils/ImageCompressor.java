package com.fibo.smartfarmer.utils;

import android.content.Context;
import android.net.Uri;

import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.listeners.CompressionListener;
import com.fibo.smartfarmer.providers.FileProvider;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.File;

public class ImageCompressor {
    public void CompressImage(Context context,Uri realUri, CompressionListener listener){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                FileProvider.getInstance().createCompressionDir(new CompleteListener() {
                    @Override
                    public void onComplete(File file) {
                        String filePath= SiliCompressor.with(context)
                                .compress(realUri.toString(),file);
                        listener.complete(Uri.fromFile(new File(filePath)));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });
        thread.start();
    }
}
