package com.fibo.smartfarmer.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.adapters.StepAdapter;
import com.fibo.smartfarmer.db.DbManager;
import com.fibo.smartfarmer.listeners.RecyclerListener;
import com.fibo.smartfarmer.models.Step;
import com.fibo.smartfarmer.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StepAdapter adapter;
    private List<Step> stepList;
    private int seasonId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        recyclerView=findViewById(R.id.recycler);

        Intent intent=getIntent();
        seasonId=intent.getIntExtra(Constants.STEP_SEASON_ID,1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stepList=new ArrayList<>();
        adapter=new StepAdapter(this, stepList, new RecyclerListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onOptions(View view, int position) {
                showMenu(view);
            }
        });

        recyclerView.setAdapter(adapter);
        fetchSteps();

    }

    private void showMenu(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(StepsActivity.this)
                .setItems(new String[]{"Mark as Done", " Check for Pests", " Others"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        Dialog dialog=builder.create();
        dialog.show();
    }

    private void fetchSteps() {
List<Step>steps;
        DbManager manager=new DbManager(getApplicationContext()).open();
        stepList.clear();
        steps=manager.getSteps(seasonId);
        if (steps.isEmpty()){
            Step step=new Step(seasonId,Constants.STATUS_PENDING,Constants.CLEARANCE_STAGE);
            manager.insertStep(step);
            fetchSteps();
        }else {
            stepList.addAll(steps);
            manager.closeDb();
            adapter.notifyDataSetChanged();
        }

    }
}