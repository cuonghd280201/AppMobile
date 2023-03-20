package com.example.appassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appassignment.R;
import com.example.appassignment.model.Message;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<Message> messageList;
    public void setData(List<Message> list){
        this.messageList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message == null){
            return;
        }
        holder.txt_message.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        if(messageList != null){
            return messageList.size();
        }
        return 0;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_message;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_message = itemView.findViewById(R.id.txt_message);
        }
    }
}
