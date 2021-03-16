package com.fibo.smartfarmer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.utils.Commons;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton registerButton,loginButton;
    private TextInputEditText emailEdit,passwordInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerButton=findViewById(R.id.register);
        loginButton=findViewById(R.id.login);
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

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
new Commons().showToast(getApplicationContext(),"Login Success");
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            }
                        });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
       if (FirebaseAuth.getInstance().getCurrentUser()!=null){
           startActivity(new Intent(LoginActivity.this,MainActivity.class));
       }
    }
}