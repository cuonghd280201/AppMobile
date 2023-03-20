package com.example.appassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;

import com.example.appassignment.R;
import com.example.appassignment.adapter.MessageAdapter;
import com.example.appassignment.model.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    EditText editMessage;
    Button btnSend;
    RecyclerView recyclerView_message;
    MessageAdapter messageAdapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editMessage = findViewById(R.id.edit_message);
        btnSend = findViewById(R.id.btnSend);
        recyclerView_message = findViewById(R.id.rcv_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_message.setLayoutManager(linearLayoutManager);
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter();
        messageAdapter.setData(messageList);
        recyclerView_message.setAdapter(messageAdapter);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }

        });
        editMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkKeyBoard();
            }
        });
    }
    private void sendMessage() {
        String strMessage = editMessage.getText().toString().trim();
        if(TextUtils.isEmpty(strMessage)){
            return;
        }
        messageList.add(new Message(strMessage));
        messageAdapter.notifyDataSetChanged();
        recyclerView_message.scrollToPosition(messageList.size() - 1);

        editMessage.setText("");
    }
    private void checkKeyBoard(){
        final View activityRootView = findViewById(R.id.activityBoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - r.height();
                if(heightDiff > 0.25 * activityRootView.getRootView().getHeight()){
                    if(messageList.size() > 0){
                        recyclerView_message.scrollToPosition(messageList.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }
}