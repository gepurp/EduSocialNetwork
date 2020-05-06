package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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

        // Trying to implement the users conversation process
        ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("Chat");
        ParseQuery<ParseObject> secondUserChatQuery = ParseQuery.getQuery("Chat");

        firstUserChatQuery.whereEqualTo("Sender", ParseUser.getCurrentUser().getUsername());
        firstUserChatQuery.whereEqualTo("Recipient", selectedUser);

        secondUserChatQuery.whereEqualTo("Sender", selectedUser);
        secondUserChatQuery.whereEqualTo("Recipient", ParseUser.getCurrentUser().getUsername());
    }

    @Override
    public void onClick(View v) {

        // Creating edit text component when onClick method will be called for reducing memory usage
        final EditText edtChatMessage = findViewById(R.id.edtChatMessage);

        // Processing the text which user putted into message edit text component
        ParseObject chat = new ParseObject("Chat");
        chat.put("Sender", ParseUser.getCurrentUser().getUsername());
        chat.put("Recipient", selectedUser);
        chat.put("Message", edtChatMessage.getText().toString());

        // Send information about chat to the server
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {

                    // Create line in the chat linear layout
                    chatMessages.add(ParseUser.getCurrentUser().getUsername() + ": " + edtChatMessage.getText().toString());

                    // Notify the user when message will be sent or received
                    arrayAdapter.notifyDataSetChanged();

                    // Clear the edit text for user's message after sending
                    edtChatMessage.setText("");
                }
            }
        });



    }
}
