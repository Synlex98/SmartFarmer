package com.fibo.smartfarmer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.fragments.FragmentPersonalDetails;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,new FragmentPersonalDetails());
        transaction.commit();
        transaction.addToBackStack(null);
    }
}