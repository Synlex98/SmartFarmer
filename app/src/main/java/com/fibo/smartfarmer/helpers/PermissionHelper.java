package com.fibo.smartfarmer.helpers;

import android.Manifest;

import androidx.appcompat.app.AppCompatActivity;

import com.fibo.smartfarmer.listeners.PermissionListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class PermissionHelper {
    private static PermissionHelper helper;
    public static PermissionHelper getInstance() {
        if (helper==null){
            helper=new PermissionHelper();
        }
        return helper;
    }

    public void checkStoragePermission(PermissionListener listener, AppCompatActivity activity){
        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()){
                            listener.accepted();
                            return;
                        }
                        listener.rejected();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
token.continuePermissionRequest();
                    }
                }).check();

    }

}
