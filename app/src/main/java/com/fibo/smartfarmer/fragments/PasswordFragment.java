package com.fibo.smartfarmer.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.activities.LoginActivity;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.utils.Commons;
import com.fibo.smartfarmer.utils.ValidationUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PasswordFragment extends Fragment {

    private TextInputEditText passwordInput,passwordConfirmInput;
    private MaterialButton nextButton;
    private ImageView step1,step2,step3;
    public PasswordFragment() {
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
        View view= inflater.inflate(R.layout.fragment_password, container, false);
        passwordInput=view.findViewById(R.id.userPasswordEdit);
        passwordConfirmInput=view.findViewById(R.id.userPasswordConfirmEdit);
        nextButton=view.findViewById(R.id.next);

        step1=view.findViewById(R.id.step1);
        step2=view.findViewById(R.id.step2);
        step3=view.findViewById(R.id.step3);

        step1.setImageResource(R.drawable.ic_check_white);
        step2.setImageResource(R.drawable.ic_check_white);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=passwordInput.getText().toString();
                String passwordConfirm=passwordConfirmInput.getText().toString();

if (!password.trim().equals(passwordConfirm.trim())){
    passwordInput.setError("Passwords do not match");
    passwordConfirmInput.setError("Passwords do not match");
    passwordInput.requestFocus();
    return;
}

if (!new ValidationUtils().isPasswordValid(password)){
    passwordInput.setError("Passwords should be between 6 to 16 characters");
    return;
}

//update model password
                Authenticator.user.setPassword(password);

                Dialog pdialog=new Dialog(getActivity());
                pdialog.setContentView(R.layout.loading_dialog);
                pdialog.show();
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(Authenticator.user.getUserEmail(),Authenticator.user.getPassword())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser user=authResult.getUser();
                                UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                                        .setDisplayName(Authenticator.user.getUserName()+"__"+Authenticator.user.getLocation()+"__"+Authenticator.user.getPhoneNumber())
                                        .build();
                                user.updateProfile(request)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                if (pdialog.isShowing())pdialog.dismiss();
new Commons().showToast(getActivity(),"Register Successful");
startActivity(new Intent(getActivity(), LoginActivity.class));
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
new Commons().showToast(getActivity(),e.getMessage());
                    }
                });
            }
        });

    return view;}

}