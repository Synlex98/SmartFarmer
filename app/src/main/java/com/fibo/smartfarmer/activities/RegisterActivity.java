package com.fibo.smartfarmer.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.fragments.FragmentPersonalDetails;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new FragmentPersonalDetails());
        transaction.commit();
        transaction.addToBackStack(null);
    }
}