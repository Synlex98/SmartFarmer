package com.fibo.smartfarmer.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.listeners.LocationFetchListener;
import com.fibo.smartfarmer.utils.Commons;
import com.fibo.smartfarmer.utils.LocationUtils;
import com.fibo.smartfarmer.utils.ValidationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentUserLocation extends Fragment {

private MaterialButton nextButton;
private TextInputEditText locationInput,inputEmail;
private String userLocation;
private ImageView step1,step2,step3;
    public FragmentUserLocation() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                new LocationUtils().fetchLocation(getActivity(), new LocationFetchListener() {
                    @Override
                    public void onFetch(String location) {
                        locationInput.setText(location);
                        userLocation=location;
                        if (pdialog.isShowing())pdialog.dismiss();
                    }

                    @Override
                    public void onFailure(String error) {
                        new Commons().showToast(getActivity(),error);
                    }
                });
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
}