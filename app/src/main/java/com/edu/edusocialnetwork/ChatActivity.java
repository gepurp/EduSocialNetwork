package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView chatListView;
    private Button btnChatSend;
    private ArrayList<String> chatMessages;
    private ArrayAdapter arrayAdapter;
    private String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnChatSend.setOnClickListener(this);

        // Get value of the selected user
        selectedUser = getIntent().getStringExtra("selectedUser");
        // Show message about chosen chat
        FancyToast.makeText(this,
                "Chat with " + selectedUser + " was started",
                Toast.LENGTH_LONG,
                FancyToast.INFO,
                false).show();

        chatListView = findViewById(R.id.chatListView);
        chatMessages = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, chatMessages);
        chatListView.setAdapter(arrayAdapter);

        ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("Chat");
        ParseQuery<ParseObject> secondUserChatQuery = ParseQuery.getQuery("Chat");




    }

    @Override
    public void onClick(View v) {

    }
}
