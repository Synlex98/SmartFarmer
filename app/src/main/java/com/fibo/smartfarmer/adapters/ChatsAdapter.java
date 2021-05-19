package com.fibo.smartfarmer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fibo.smartfarmer.R;
import com.fibo.smartfarmer.auth.Authenticator;
import com.fibo.smartfarmer.models.Message;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Message> messageList;

    public ChatsAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==0){
            view= LayoutInflater.from(context).inflate(R.layout.my_messages,parent,false);
            return new MyChatHolder(view);
        }
        view=LayoutInflater.from(context).inflate(R.layout.their_messages,parent,false);
        return new TheirChatHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message=messageList.get(position);
if (getItemViewType(position)==0){
    MyChatHolder myChatHolder=(MyChatHolder)holder;
    myChatHolder.timeView.setText(message.getTimeSent());
    myChatHolder.messageView.setText(message.getMessageBody());
    return;
}
TheirChatHolder theirChatHolder=(TheirChatHolder)holder;
theirChatHolder.messageView.setText(message.getMessageBody());
theirChatHolder.timeView.setText(message.getTimeSent());
theirChatHolder.userNameView.setText(message.getFromName());
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getFromId().equals(new Authenticator().getCurrentUserID())){
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class MyChatHolder extends RecyclerView.ViewHolder{
     TextView messageView,timeView;
        public MyChatHolder(@NonNull View itemView) {
            super(itemView);
            messageView=itemView.findViewById(R.id.message);
            timeView=itemView.findViewById(R.id.time);

        }
    }

    static class TheirChatHolder extends RecyclerView.ViewHolder{
        TextView userNameView,messageView,timeView;

        public TheirChatHolder(@NonNull View itemView) {
            super(itemView);
            messageView=itemView.findViewById(R.id.message);
            timeView=itemView.findViewById(R.id.time);
            userNameView=itemView.findViewById(R.id.username);
        }
    }
}
