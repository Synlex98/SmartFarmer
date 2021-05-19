package com.fibo.smartfarmer.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.utils.Commons;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEdit,passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MaterialButton registerButton = findViewById(R.id.register);
        MaterialButton loginButton = findViewById(R.id.login);
        emailEdit=findViewById(R.id.userEmailInput);
        passwordInput=findViewById(R.id.userPasswordEdit);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     String email=emailEdit.getText().toString();
     String password=passwordInput.getText().toString();

     if (TextUtils.isEmpty(email)){
         emailEdit.setError("This field is required");
         return;
     }

                if (TextUtils.isEmpty(password)){
                    passwordInput.setError("This field is required");
                    return;
                }
                Dialog dialog=new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.loading_dialog);
                dialog.show();

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if (dialog.isShowing())dialog.dismiss();
new Commons().showToast(getApplicationContext(),"Login Successful");
                                startActivity(new Intent(LoginActivity.this,PestsActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                new Commons().showToast(getApplicationContext(),e.getMessage());
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       if (FirebaseAuth.getInstance().getCurrentUser()!=null){
           startActivity(new Intent(LoginActivity.this,PestsActivity.class));
       }
    }
}