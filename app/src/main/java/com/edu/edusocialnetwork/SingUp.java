package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SingUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void helloClicked(View v) {
        ParseObject testSave = new ParseObject("TestSave");
        testSave.put("UserName", "user");
        testSave.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SingUp.this, "Object 'TestSave' saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        });

        ParseObject testSave1 = new ParseObject("TestSave1");
        testSave1.put("UserName", "user1");
        testSave1.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SingUp.this, "Object 'TestSave1' saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
