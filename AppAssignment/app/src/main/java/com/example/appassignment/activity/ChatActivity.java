package com.example.appassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.appassignment.R;
import com.example.appassignment.adapter.ChatAdapter;
import com.example.appassignment.model.ChatMessage;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText chat;
    ImageView img_send;
    RecyclerView recyclerView_chat;

    ChatAdapter chatAdapter;
    List<ChatMessage> chatMessageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        viewById();
    }

    private void viewById() {
        chatMessageList = new ArrayList<>();
        chat = findViewById(R.id.chat);
        img_send = findViewById(R.id.image_chat);
        recyclerView_chat = findViewById(R.id.recycle_chat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_chat.setLayoutManager(layoutManager);
        recyclerView_chat.setHasFixedSize(true);
    }
}