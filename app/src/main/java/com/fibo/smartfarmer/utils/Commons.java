package com.fibo.smartfarmer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.db.DbManager;
import com.fibo.smartfarmer.models.Season;

import java.util.Calendar;

public class Commons {
    public void showToast(Context context, String message){

        View view= LayoutInflater.from(context).inflate(R.layout.toast,null,false);
         TextView textView=view.findViewById(R.id.message);
         textView.setText(message);


         Toast toast=new Toast(context);
         toast.setView(view);
         toast.setDuration(Toast.LENGTH_LONG);
         toast.show();
    }

    public boolean isCurrentSeasonExpired(Context context){
        DbManager manager=new DbManager(context).open();
        Season season=manager.getLastSeason();
        manager.closeDb();
        if (season==null){
            return true;
        }

        return season.getCurrentStage()==null || season.getCurrentStage().equals(Constants.HARVESTING_STAGE);
    }

    public String getCurrentTime(){
        Calendar calendar=Calendar.getInstance();
        return calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1) +"/"+calendar.get(Calendar.YEAR)+" @"+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
    }

    public String generateMessageId(){
      return String.valueOf(System.nanoTime());
    }


}
