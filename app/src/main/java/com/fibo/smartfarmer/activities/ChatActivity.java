package com.fibo.smartfarmer.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.adapters.ChatsAdapter;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.db.DbManager;
import com.fibo.smartfarmer.listeners.MessageListener;
import com.fibo.smartfarmer.models.Message;
import com.fibo.smartfarmer.utils.ChatUtils;
import com.fibo.smartfarmer.utils.Commons;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ChatActivity extends AppCompatActivity {
private AppCompatEditText messageEdit;
private FloatingActionButton sendButton;
private BroadcastReceiver messagesReceiver;
private RecyclerView messagesRecycler;
private List<Message> messageList;
private ChatsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageEdit=findViewById(R.id.inputChat);
        sendButton=findViewById(R.id.send);
        messagesRecycler=findViewById(R.id.recycler);
        messagesRecycler.setHasFixedSize(true);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);
        messagesRecycler.setLayoutManager(manager);

        messageList=new ArrayList<>();
        adapter=new ChatsAdapter(getApplicationContext(),messageList);
        messagesRecycler.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=messageEdit.getText().toString();
                if(TextUtils.isEmpty(message)){
                    return;
                }
                sendButton.setVisibility(View.GONE);
                String fromName=new Authenticator().getCurrentUser(ChatActivity.this).getUserName();
                String fromId=new Authenticator().getCurrentUserID();
                String messageId=new Commons().generateMessageId();
                String timeSent=new Commons().getCurrentTime();

                Message message1=new Message(messageId,fromName,fromId,timeSent,message);

                new ChatUtils().sendMessage(message1, new MessageListener() {
                    @Override
                    public void onSent() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sendButton.setVisibility(View.VISIBLE);
                                messageEdit.setText("");
                                new ChatUtils().saveMessage(getApplicationContext(),message1);


                            }
                        });

                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });

        messagesReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Timber.e("received intent");
                DbManager manager=new DbManager(getApplicationContext()).open();
                int messagesCount=(int)manager.getMessagesCount();
                Timber.e(String.valueOf(messagesCount));
                int difference=messagesCount-messageList.size();
                List<Message>messages=manager.getLastNMessages(difference);
                manager.closeDb();
                messageList.addAll(messages);
                adapter.notifyDataSetChanged();

            }
        };
        IntentFilter filter=new IntentFilter(getPackageName()+".NEW_CHAT");
        registerReceiver(messagesReceiver,filter);
        Intent intent=new Intent(getPackageName()+".NEW_CHAT");
        messagesReceiver.onReceive(ChatActivity.this,intent);
    }


}