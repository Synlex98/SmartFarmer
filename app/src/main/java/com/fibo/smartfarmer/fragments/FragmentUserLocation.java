package com.fibo.smartfarmer.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.utils.GeocoderHelper;
import com.fibo.smartfarmer.utils.ValidationUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutionException;

public class FragmentUserLocation extends Fragment {

private MaterialButton nextButton;
private TextInputEditText locationInput,inputEmail;
private String userLocation;
private ImageView step1,step2,step3;
private FusedLocationProviderClient locationProviderClient;
    public FragmentUserLocation() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_user_location, container, false);
        nextButton=view.findViewById(R.id.next);
        locationInput=view.findViewById(R.id.userLocationEdit);
        inputEmail=view.findViewById(R.id.userEmailInput);
        step1=view.findViewById(R.id.step1);
        step2=view.findViewById(R.id.step2);
        step3=view.findViewById(R.id.step3);

        step1.setImageResource(R.drawable.ic_check_white);



        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("We need to access your location automatically. Click Ok to continue or Cancel to input your location manually");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Dialog pdialog=new Dialog(getActivity());
                pdialog.setContentView(R.layout.loading_dialog);
                pdialog.show();
                checkLocationProviders();

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog=builder.create();
        dialog.show();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//check if location is empty
                if (TextUtils.isEmpty(locationInput.getText().toString())){
                    locationInput.setError("Location cannot be empty");
                    locationInput.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(locationInput.getText().toString()) || !new ValidationUtils().isValidEmail(inputEmail.getText().toString())){
                    locationInput.setError("Location cannot be empty");
                    locationInput.requestFocus();
                    return;
                }
                //add location to the model
                userLocation=locationInput.getText().toString().trim();
                String userEmail=inputEmail.getText().toString().trim();

                Authenticator.user.setLocation(userLocation);
                Authenticator.user.setUserEmail(userEmail);
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new PasswordFragment());
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });
        return view;
    }

    private void checkLocationProviders() {
        LocationManager manager=(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)|| manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) )){
            Toast.makeText(getActivity(),"You need to enable GPS ",Toast.LENGTH_LONG).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),300);
            return;
        }

        getUserLocation();
    }

    @SuppressLint("MissingPermission")
    private void getUserLocation() {
        locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.getResult()!=null){
                    geoCode(task.getResult());

                }else {
                    registerLocationListener();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    @SuppressLint("MissingPermission")
    private void registerLocationListener() {
        LocationRequest request=new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5);
        request.setFastestInterval(0);
        request.setNumUpdates(1);
        locationProviderClient=LocationServices.getFusedLocationProviderClient(getActivity());
        locationProviderClient.requestLocationUpdates(request, new LocationCallback() {

            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location=locationResult.getLastLocation();
                geoCode(location);
            }
        }, Looper.myLooper());
    }

    private void geoCode(Location result) {
        GeocoderHelper helper=new GeocoderHelper(getActivity());
        String location;
        try {
            location =helper.execute(result).get();
            locationInput.setText(location);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            location="Unknown location";

        }
        userLocation=location;

    }

}