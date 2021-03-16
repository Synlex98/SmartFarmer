package com.fibo.smartfarmer.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.adapters.SeasonsAdapter;
import com.fibo.smartfarmer.db.DbManager;
import com.fibo.smartfarmer.listeners.RecyclerListener;
import com.fibo.smartfarmer.models.Season;
import com.fibo.smartfarmer.utils.Commons;
import com.fibo.smartfarmer.utils.Constants;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton actionButton;
    private SeasonsAdapter adapter;
    private List<Season>seasonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        recyclerView=findViewById(R.id.recycler);
        actionButton=findViewById(R.id.add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seasonList=new ArrayList<>();
        adapter=new SeasonsAdapter(this, seasonList, new RecyclerListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(MainActivity.this,StepsActivity.class);
                TextView idView=view.findViewById(R.id.seasonId);
                intent.putExtra(Constants.STEP_SEASON_ID,Integer.parseInt(idView.getText().toString()));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new Commons().isCurrentSeasonExpired(getApplicationContext())){
                    new Commons().showToast(getApplicationContext(),"The current season is still active");
                    return;
                }
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.new_season);
                TextInputEditText numberOfFarmsInput,sizeOfFarmsInput;
                MaterialButton saveButton;
                numberOfFarmsInput=dialog.findViewById(R.id.nFarmsInput);
                sizeOfFarmsInput=dialog.findViewById(R.id.sizeInput);
                saveButton=dialog.findViewById(R.id.save);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String numberOfFarms=numberOfFarmsInput.getText().toString();
                        String sizeOfFarms=sizeOfFarmsInput.getText().toString();
                        if (dialog.isShowing())dialog.dismiss();
                        insertNewSeason(numberOfFarms,sizeOfFarms);
                    }
                });

                dialog.show();

            }
        });

        fetchSeasons();


    }

    private void insertNewSeason(String numberOfFarms, String sizeOfFarms) {
        Season season=new Season(numberOfFarms,sizeOfFarms, Constants.DEFAULT_STOCK,Constants.INITIAL_STAGE);
        DbManager manager=new DbManager(getApplicationContext()).open();
        manager.insertSeason(season);
        manager.closeDb();
        new Commons().showToast(getApplicationContext(),"Season Created Successfully");
        fetchSeasons();
    }

    private void fetchSeasons() {
        DbManager manager=new DbManager(getApplicationContext()).open();
        seasonList.clear();
        List<Season> seasons=manager.getSeasons();
        Timber.e(String.valueOf(seasons.size()));
        seasonList.addAll(seasons);
        manager.closeDb();
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
int menuId=item.getItemId();

        if (menuId == R.id.profileMenu) {
        }

        return super.onOptionsItemSelected(item);
    }
}