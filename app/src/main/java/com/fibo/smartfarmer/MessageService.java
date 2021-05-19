package com.fibo.smartfarmer;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.fibo.smartfarmer.activities.ChatActivity;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.models.Message;
import com.fibo.smartfarmer.utils.ChatUtils;
import com.fibo.smartfarmer.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import timber.log.Timber;

public class MessageService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Timber.e(remoteMessage.toString());
        if (remoteMessage.getNotification() != null) {
            String messageFrom = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();
            String messageId = remoteMessage.getData().get(Constants.MESSAGE_ID);
            String timeSent = remoteMessage.getData().get(Constants.TIME_SENT);
            String senderId=remoteMessage.getData().get(Constants.FROM_ID);

            Message message=new Message(messageId,messageFrom,senderId,timeSent,messageBody);
           if (!new Authenticator().isCurrentUser(senderId)) {
               new ChatUtils().saveMessage(getApplicationContext(), message);
           }
            showNotification(message);
        }
    }

    private void showNotification(Message message) {
        Intent intent=new Intent(getApplicationContext(), ChatActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),Constants.GALLERY_REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),getResources().getString(R.string.app_name));
        builder.setSmallIcon(R.drawable.ic_baseline_grass_24)
                .setColor(getResources().getColor(R.color.green))
                .setContentTitle(message.getFromName())
                .setContentText(message.getMessageBody())
        .setContentIntent(pendingIntent);

        NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager!=null){
            manager.notify(new Random().nextInt(),builder.build());
        }

    }
}
