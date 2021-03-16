package com.fibo.smartfarmer.utils;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fibo.smartfarmer.listeners.LocationFetchListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import timber.log.Timber;

public class LocationUtils {

    public void fetchLocation(Context context, LocationFetchListener listener){
        AppCompatActivity activity=(AppCompatActivity)context;

        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                      if (report.areAllPermissionsGranted()) {
                          continueLocationFetch(context,listener);
                      }else {
                         listener.onFailure("The following permission was denied by user"+ StringUtils.joinWith("\n",report.getDeniedPermissionResponses()));
                      }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
token.continuePermissionRequest();
                    }
                }).check();
    }

    public void continueLocationFetch(Context context,LocationFetchListener listener){
        SmartLocation.with(context)
                .location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        Timber.e(location.toString());
                        listener.onFetch(geoCode(location,context));
                    }
                });
    }

    private String geoCode(Location location, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                String cityName = address.getAddressLine(0) + ", " + address.getLocality() + ", " + address.getSubLocality();
                Timber.e(cityName);

                return cityName;
            }
            Timber.e("Null address list");

            return null;
        } catch (IOException e) {
            Toast.makeText(context, "Could not decode location try again", Toast.LENGTH_LONG).show();
            return null;
        }
    }


}
