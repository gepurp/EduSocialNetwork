package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView chatListView;
    private ImageButton btnChatSend;
    private ArrayList<String> chatMessages;
    private ArrayAdapter arrayAdapter;
    private String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnChatSend = findViewById(R.id.btnChatSend);
        btnChatSend.setOnClickListener(this);

        // Get value of the selected user
        selectedUser = getIntent().getStringExtra("username");
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

        try {
            // Trying to implement the users conversation process
            ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secondUserChatQuery = ParseQuery.getQuery("Chat");

            firstUserChatQuery.whereEqualTo("Sender", ParseUser.getCurrentUser().getUsername());
            firstUserChatQuery.whereEqualTo("Recipient", selectedUser);

            secondUserChatQuery.whereEqualTo("Sender", selectedUser);
            secondUserChatQuery.whereEqualTo("Recipient", ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQueries = new ArrayList<>();
            allQueries.add(firstUserChatQuery);
            allQueries.add(secondUserChatQuery);

            ParseQuery<ParseObject> myQuery = ParseQuery.or(allQueries); // ???
            // Specifying the order for messages in chat view
            myQuery.orderByAscending("createdAt");

            myQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if (objects.size() > 0 && e == null) {

                        for (ParseObject chatObject : objects) {
                            String message = chatObject.get("Message") + "";

                            if (chatObject.get("Sender").equals(ParseUser.getCurrentUser().getUsername())) {
                                message = ParseUser.getCurrentUser().getUsername() + ": " + message;
                            }

                            if (chatObject.get("Sender").equals(selectedUser)) {
                                message = selectedUser + ": " + message;
                            }

                            chatMessages.add(message);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
