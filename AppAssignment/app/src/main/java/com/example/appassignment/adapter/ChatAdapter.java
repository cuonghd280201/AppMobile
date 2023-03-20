package com.example.appassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appassignment.R;
import com.example.appassignment.model.ChatMessage;

import org.w3c.dom.Text;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ChatMessage> chatMessageList;
    private String sendid;
    private static final int TYPE_SEND = 1;
    private static final int TYPE_RECEIVE = 2;

    public ChatAdapter(Context context, List<ChatMessage> chatMessageList, String sendid) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.sendid = sendid;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == TYPE_SEND){
            view = LayoutInflater.from(context).inflate(R.layout.item_sen_message, parent, false);
            return new SendMessViewHolder(view);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.item_received_mess, parent, false);
            return new ReceivedViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_SEND){
            ((SendMessViewHolder) holder).s_message.setText(chatMessageList.get(position).mess);
            ((SendMessViewHolder) holder).s_time.setText(chatMessageList.get(position).datetime);
        }else{
            ((ReceivedViewHolder) holder).re_message.setText(chatMessageList.get(position).mess);
            ((ReceivedViewHolder) holder).re_time.setText(chatMessageList.get(position).datetime);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessageList.get(position).sendid.equals(sendid)){
            return TYPE_SEND;
        }else{
            return TYPE_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    class SendMessViewHolder extends RecyclerView.ViewHolder{
        TextView s_message, s_time;

        public SendMessViewHolder(@NonNull View itemView) {
            super(itemView);
            s_message = itemView.findViewById(R.id.txt_message);
            s_time = itemView.findViewById(R.id.txt_time);

        }

    }
    class ReceivedViewHolder extends RecyclerView.ViewHolder{
        TextView re_message, re_time;

        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            re_message = itemView.findViewById(R.id.received_message);
            re_time = itemView.findViewById(R.id.received_time);
        }
    }
}
