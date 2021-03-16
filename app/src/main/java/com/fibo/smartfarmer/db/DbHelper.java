package com.fibo.smartfarmer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.fibo.smartfarmer.utils.Constants;

public class DbHelper extends SQLiteOpenHelper {

    Context context;
    static int DB_VERSION=1;

    public DbHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME,null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SEASON_QUERY="CREATE TABLE IF NOT EXISTS "+Constants.SEASON_TABLE+" ( "+Constants.SEASON_ID+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                Constants.FARM_SIZE+" TEXT NOT NULL ,"+Constants.NO_OF_FARMS+" TEXT NOT NULL,"+
                Constants.HARVESTED_STOCK+" TEXT NOT NULL ,"+Constants.CURRENT_STAGE+" NOT NULL)";
        db.execSQL(CREATE_SEASON_QUERY);

        String CREATE_STEPS_QUERY="CREATE TABLE IF NOT EXISTS "+Constants.STEPS_TABLE+" ( "+Constants.STEP_ID+ " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                Constants.STEP_SEASON_ID+" INTEGER NOT NULL ,"+Constants.STEP_STATUS+" TEXT NOT NULL,"+
                Constants.STEP_NAME+" TEXT NOT NULL )";
        db.execSQL(CREATE_STEPS_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL(Constants.DELETE_QUERY+Constants.SEASON_TABLE);
db.execSQL(Constants.DELETE_QUERY+Constants.STEPS_TABLE);
onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DELETE_QUERY+Constants.SEASON_TABLE);
        db.execSQL(Constants.DELETE_QUERY+Constants.STEPS_TABLE);
        onCreate(db);
    }
}
