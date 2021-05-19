package com.fibo.smartfarmer.utils;

import android.content.Context;
import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.fibo.smartfarmer.db.DbManager;
import com.fibo.smartfarmer.listeners.MessageListener;
import com.fibo.smartfarmer.models.Message;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class ChatUtils {
    public void sendMessage(Message message, MessageListener listener){
        Thread thread=new Thread(() -> sendNotification(message, new MessageListener() {
            @Override
            public void onSent() {
                listener.onSent();
            }

            @Override
            public void onError(String message1) {
listener.onError(message1);
            }
        }));
        thread.start();
    }

    public void saveMessage(Context context, Message message){
        DbManager manager=new DbManager(context).open();
        manager.insertChat(message);
        manager.closeDb();

        Intent intent=new Intent(context.getPackageName()+".NEW_CHAT");
        context.sendBroadcast(intent);
    }

    public void sendNotification(Message message, MessageListener listener){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject notificationPayLoad=new JSONObject();
                    JSONObject notification=new JSONObject();
                    JSONObject dataPart=new JSONObject();

                    notificationPayLoad.put("to","/topics/chat");
                    notification.put("title",message.getFromName());
                    notification.put("body",message.getMessageBody());
                    dataPart.put(Constants.TIME_SENT,message.getTimeSent());
                    dataPart.put(Constants.MESSAGE_ID,message.getMessageId());
                    dataPart.put(Constants.FROM_ID,message.getFromId());

                    notificationPayLoad.put("notification",notification);
                    notificationPayLoad.put("data",dataPart);

                    AndroidNetworking.post(Urls.NOTIFICATION_URL)
                            .setContentType("application/json; charset=utf-8")
                            .addJSONObjectBody(notificationPayLoad)
                            .addHeaders("Authorization",Constants.API_KEY)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Timber.e(response.toString());
                                    listener.onSent();
                                }

                                @Override
                                public void onError(ANError anError) {
                                    listener.onError(anError.getMessage());
                                }
                            });

                }catch (JSONException e){
                    listener.onError(e.getMessage());
                }
            }
        });
        thread.start();
    }
}
