package com.fibo.smartfarmer.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.fibo.smartfarmer.models.Message;
import com.fibo.smartfarmer.models.Season;
import com.fibo.smartfarmer.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

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

    public long getMessagesCount(){
       return DatabaseUtils.queryNumEntries(this.database,Constants.CHAT_TABLE);
    }
    public List<Message> getLastNMessages(int limit){
        List<Message> messages=new ArrayList<>();
        String sql="SELECT * FROM "+Constants.CHAT_TABLE+" ORDER BY "+Constants.CHAT_ID +" DESC  LIMIT "+limit;
Cursor cursor=this.database.rawQuery(sql,null);
if (cursor.moveToFirst()){
    do {
        String messageId = cursor.getString(cursor.getColumnIndex(Constants.MESSAGE_ID));
        String fromId= cursor.getString(cursor.getColumnIndex(Constants.FROM_ID));
        String fromName=cursor.getString(cursor.getColumnIndex(Constants.FROM_NAME));
        String messageBody=cursor.getString(cursor.getColumnIndex(Constants.MESSAGE_BODY));
        String timeSent =cursor.getString(cursor.getColumnIndex(Constants.TIME_SENT));

        messages.add(new Message(messageId,fromName,fromId,timeSent,messageBody));

    }while (cursor.moveToNext());
}
cursor.close();
return messages;
    }

    public void insertChat(Message message){
        ContentValues values=new ContentValues();
        values.put(Constants.FROM_ID,message.getFromId());
        values.put(Constants.FROM_NAME,message.getFromName());
        values.put(Constants.MESSAGE_ID,message.getMessageId());
        values.put(Constants.MESSAGE_BODY,message.getMessageBody());
        values.put(Constants.TIME_SENT,message.getTimeSent());
        this.database.insert(Constants.CHAT_TABLE,null,values);

    }

  /*public void insertSeason(Season season){
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
        values.put(Constants.STEP_NAME,step.getStepName());
        values.put(Constants.STEP_STATUS,step.getStepStatus());
        this.database.insert(Constants.STEPS_TABLE,null,values);
        Timber.e("Added");
  }

  public void updateStep(int stepId){
       ContentValues values=new ContentValues();
       values.put(Constants.STEP_STATUS,Constants.STATUS_DONE);
       this.database.update(Constants.STEPS_TABLE,values,Constants.STEP_ID+"="+stepId,null);
  }

  public boolean isStepAlreadyAdded(int seasonId, String nextStep){
        String sql="SELECT * FROM "+Constants.STEPS_TABLE+" WHERE "+Constants.STEP_SEASON_ID+"='"+seasonId+"' AND "+Constants.STEP_NAME+"='"+nextStep+"';";
        Cursor cursor=this.database.rawQuery(sql,null);
        boolean is=false;
        if (cursor.getCount()>0){is= true;}
        cursor.close();
        return is;


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
  }*/

  public Season getLastSeason(){
String query="SELECT * FROM "+Constants.SEASON_TABLE+" ORDER BY "+Constants.SEASON_ID+ " DESC LIMIT 1";
Cursor cursor=this.database.rawQuery(query,null);

if (cursor==null || cursor.getCount()<=0){
    Timber.e("returning null");
    return null;
}
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

/*  public void insertHistory(History history){
        ContentValues values=new ContentValues();
        values.put(Constants.STEP_SEASON_ID,history.getSeasonId());
        values.put(Constants.LAST_PEST_DATE,history.getLastPestDay());
        values.put(Constants.LAST_SPRAY_DATE,history.getLastSprayDay());
        this.database.insert(Constants.HISTORY_TABLE,null,values);
  }

  public History getHistory(int seasonId){
        String query="SELECT * FROM "+Constants.HISTORY_TABLE+" WHERE "+Constants.STEP_SEASON_ID+"='"+seasonId+"';";
        Cursor cursor=this.database.rawQuery(query,null);
        cursor.moveToFirst();
        int s_id=cursor.getInt(cursor.getColumnIndex(Constants.STEP_SEASON_ID));
        String l_pest=cursor.getString(cursor.getColumnIndex(Constants.LAST_PEST_DATE));
        String l_spray=cursor.getString(cursor.getColumnIndex(Constants.LAST_SPRAY_DATE));

        cursor.close();
      return new History(s_id,l_spray,l_pest);
  }
  */  public void closeDb() {
        this.database.close();
    }
}
