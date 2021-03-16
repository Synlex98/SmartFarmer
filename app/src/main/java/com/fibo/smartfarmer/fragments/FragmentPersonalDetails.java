package com.fibo.smartfarmer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.models.User;
import com.fibo.smartfarmer.utils.ValidationUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentPersonalDetails extends Fragment {

  private MaterialButton nextButton;
  private TextInputEditText nameEdit,phoneEdit;
    public FragmentPersonalDetails() {
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
        View view= inflater.inflate(R.layout.fragment_personal_details, container, false);
        nextButton=view.findViewById(R.id.next);
        nameEdit=view.findViewById(R.id.userNameEdit);
        phoneEdit=view.findViewById(R.id.userPhoneEdit);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get input from views
                String name=nameEdit.getText().toString();
                String phone=phoneEdit.getText().toString();

                //validate input
                if (!new ValidationUtils().isUserNameValid(name)) {
                    nameEdit.setError("The username is invalid");
                    return;
                }

                if (!new ValidationUtils().isPhoneNumberValid(phone)){
                    phoneEdit.setError("Invalid phone number. Use the format 07XX..");
                    return;
                }

                //add the two values to the user class
                Authenticator.user = User.getInstance()
                        .setUserName(name)
                        .setPhoneNumber(phone);

                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container,new FragmentUserLocation());
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });
        return view;
    }
}