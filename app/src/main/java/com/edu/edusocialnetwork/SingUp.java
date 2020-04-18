package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SingUp extends AppCompatActivity  implements View.OnClickListener {

    private TextView edtFirstName, edtSecondName, edtAge, edtGender;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFirstName = findViewById(R.id.edtFirstName);
        edtSecondName = findViewById(R.id.edtSecondName);
        edtAge = findViewById(R.id.edtAge);
        edtGender = findViewById(R.id.edtGender);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ParseObject userSave = new ParseObject("UserSave");
        userSave.put("FirstName", edtFirstName.getText().toString());
        userSave.put("SecondName", edtSecondName.getText().toString());
        userSave.put("Age", edtAge.getText().toString());
        userSave.put("Gender", edtGender.getText().toString());
        userSave.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SingUp.this, "UserSave saved successfully", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

/*
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
*/
}
