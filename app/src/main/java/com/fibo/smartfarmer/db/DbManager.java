package com.fibo.smartfarmer.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fibo.smartfarmer.models.Season;
import com.fibo.smartfarmer.models.Step;
import com.fibo.smartfarmer.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DbManager {
    Context context;
    SQLiteDatabase database;
    DbHelper helper;

    public DbManager(Context context){
        this.context=context;
    }

    public DbManager open() throws SQLException{
        this.helper=new DbHelper(this.context);
        this.database=this.helper.getWritableDatabase();
        this.helper.onCreate(database);
        return this;
    }

  public void insertSeason(Season season){
      ContentValues values=new ContentValues();
      values.put(Constants.NO_OF_FARMS,season.getNoOfFarms());
      values.put(Constants.CURRENT_STAGE,season.getCurrentStage());
      values.put(Constants.FARM_SIZE,season.getFarmSize());
      values.put(Constants.HARVESTED_STOCK,season.getStock());

      this.database.insert(Constants.SEASON_TABLE,null,values);
  }

  public List<Season> getSeasons(){
       List<Season> seasonList=new ArrayList<>();
       String query="SELECT * FROM "+Constants.SEASON_TABLE;

      Cursor cursor=this.database.rawQuery(query,null);

      if (cursor.moveToFirst()){
          do {
              int id=cursor.getInt(cursor.getColumnIndex(Constants.SEASON_ID));
              String nFarms=cursor.getString(cursor.getColumnIndex(Constants.NO_OF_FARMS));
              String size=cursor.getString(cursor.getColumnIndex(Constants.FARM_SIZE));
              String stock= cursor.getString(cursor.getColumnIndex(Constants.HARVESTED_STOCK));
              String stage=cursor.getString(cursor.getColumnIndex(Constants.CURRENT_STAGE));

              Season season=new Season(nFarms,size,stock,stage);
              season.setSeasonId(id);
              seasonList.add(season);

          }while (cursor.moveToNext());
      }
      cursor.close();
      return seasonList;
  }

  public void insertStep(Step step){
        ContentValues values=new ContentValues();
        values.put(Constants.STEP_SEASON_ID,step.getSeasonId());
        values.put(Constants.STEP_ID,step.getStepId());
        values.put(Constants.STEP_NAME,step.getStepName());
        values.put(Constants.STEP_STATUS,step.getStepStatus());
        this.database.insert(Constants.STEPS_TABLE,null,values);
  }

  public List<Step>getSteps(int sId){
        List<Step>stepList=new ArrayList<>();
        String sql="SELECT * FROM "+Constants.STEPS_TABLE+ " WHERE "+Constants.STEP_SEASON_ID+"='"+sId+"';";
        Cursor cursor=this.database.rawQuery(sql,null);
        if (cursor.moveToFirst()){
            do {
int stepId=cursor.getInt(cursor.getColumnIndex(Constants.STEP_ID));
int seasonId=cursor.getInt(cursor.getColumnIndex(Constants.STEP_SEASON_ID));
String stepName=cursor.getString(cursor.getColumnIndex(Constants.STEP_NAME));
String stepStatus=cursor.getString(cursor.getColumnIndex(Constants.STEP_STATUS));

Step step=new Step(seasonId,stepStatus,stepName);
step.setStepId(stepId);
stepList.add(step);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return stepList;
  }

  public void deleteSeason(int id){
        this.database.delete(Constants.SEASON_TABLE,Constants.SEASON_ID+"="+id,null);
  }

  public Season getLastSeason(){
String query="SELECT * FROM "+Constants.SEASON_TABLE+" ORDER BY "+Constants.SEASON_ID+ " DESC LIMIT 1";
Cursor cursor=this.database.rawQuery(query,null);
cursor.moveToFirst();
      int id=cursor.getInt(cursor.getColumnIndex(Constants.SEASON_ID));
      String nFarms=cursor.getString(cursor.getColumnIndex(Constants.NO_OF_FARMS));
      String size=cursor.getString(cursor.getColumnIndex(Constants.FARM_SIZE));
      String stock= cursor.getString(cursor.getColumnIndex(Constants.HARVESTED_STOCK));
      String stage=cursor.getString(cursor.getColumnIndex(Constants.CURRENT_STAGE));

      Season season=new Season(nFarms,size,stock,stage);
      season.setSeasonId(id);
      cursor.close();
      return season;
  }
    public void closeDb() {
        this.database.close();
    }
}
