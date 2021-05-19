package com.fibo.smartfarmer.providers;

import android.content.Context;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.fibo.smartfarmer.helpers.PermissionHelper;
import com.fibo.smartfarmer.listeners.CompleteListener;
import com.fibo.smartfarmer.listeners.FileListener;
import com.fibo.smartfarmer.listeners.PermissionListener;
import com.fibo.smartfarmer.utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileProvider{
    private static FileProvider provider;

    public static FileProvider getInstance() {
        if (provider==null){
            provider=new FileProvider();
        }
        return provider;
    }

    public void checkFileAvailable(String name, Context context, FileListener listener){
        PermissionHelper.getInstance().checkStoragePermission(new PermissionListener() {
            @Override
            public void rejected() {

            }

            @Override
            public void accepted() {

                //Create the directory first if it doesn't exist
               createDirectory(new FileListener() {
                   @Override
                   public void fileAvailable() {
File file=new File(Constants.FILES_STORAGE_DIR+"/"+name);
if (!file.exists()){
    try {
        file.createNewFile();
        listener.fileAvailable();
        return;
    } catch (IOException e) {
        e.printStackTrace();
        listener.onError(e.getMessage());
    }
}
listener.fileAvailable();
                   }

                   @Override
                   public void onError(String message) {
listener.onError(message);
                   }
               });



            }
        },((AppCompatActivity)context));

    }

    public void createDirectory(FileListener listener){
        File rootDirectory=new File( Constants.FILES_STORAGE_DIR);
        if (!rootDirectory.exists()){
            try {
                rootDirectory.mkdirs();
                listener.fileAvailable();
                return;
            }catch (Exception e){
                listener.onError(e.getMessage());
            }
        }
        listener.fileAvailable();

    }

    public void createCompressionDir(CompleteListener listener){
        File compDir=new File(Environment.getExternalStorageDirectory(), Constants.FILES_STORAGE_DIR+"/Images");
        if (!compDir.exists()){
            try {
                compDir.mkdirs();
                listener.onComplete(compDir);
                return;
            }catch (Exception e){
                listener.onError(e.getMessage());
            }
        }
        listener.onComplete(compDir);
    }

    public void getStageFileContents(Context context,String stage,FileListener listener){
        String name;
        switch (stage) {
            case Constants.PLANTING_STAGE:
                name = Constants.PLANTING_FILE;
                break;
            case Constants.WEEDING_STAGE:
                name = Constants.WEEDING_FILE;
                break;
            case Constants.HARVESTING_STAGE:
                name = Constants.HARVESTING_FILE;
                break;
            default:
                name = Constants.CLEARANCE_FILE;
                break;
        }
        checkFileAvailable(name, context, new FileListener() {
            @Override
            public void fileAvailable() {
              File file=new File(Constants.FILES_STORAGE_DIR+"/"+name);
                try {
                    FileInputStream inputStream=new FileInputStream(file);
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String data;
                    while ((data=reader.readLine())!=null){
                        builder.append(data).append("\n");
                    }
                    listener.onReadComplete(builder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }
            }

            @Override
            public void onError(String message) {
listener.onError(message);
            }
        });

    }

    public void readJSONFile(String name,Context context,FileListener listener){
        PermissionHelper.getInstance().checkStoragePermission(new PermissionListener() {
            @Override
            public void rejected() {
listener.onError("Permission Denied");
            }

            @Override
            public void accepted() {
File file =new File(Constants.FILES_STORAGE_DIR+"/"+name);
if (!file.exists()){
    listener.onError("The file "+name+" was not found");
    return;
}
                try {
                    FileInputStream inputStream=new FileInputStream(file);
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String data;
                    while ((data=reader.readLine())!=null){
                        builder.append(data);
                    }
                    listener.onReadComplete(builder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onError(e.getMessage());
                }


            }
        },((AppCompatActivity)context));
    }



}
