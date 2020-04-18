package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SingUp extends AppCompatActivity {

    // UI Components
    private TextView edtFirstName, edtSecondName, edtAge, edtGender, txtGetData;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define UI components
        edtFirstName = findViewById(R.id.edtFirstName);
        edtSecondName = findViewById(R.id.edtSecondName);
        edtAge = findViewById(R.id.edtAge);
        edtGender = findViewById(R.id.edtGender);

        txtGetData = findViewById(R.id.txtGetData);

        btnSubmit = findViewById(R.id.btnSubmit);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("UserSave");
                parseQuery.getInBackground("moqgJjputF", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (object != null && e == null) {
                            txtGetData.setText(String.format("%s%s%s", object.get("FirstName"), " ", object.get("SecondName")));
                        }
                    }
                });
            }
        });
    }

    public void submitClicked(View v) {
        try {
            final ParseObject userSave = new ParseObject("UserSave");
            userSave.put("FirstName", edtFirstName.getText().toString());
            userSave.put("SecondName", edtSecondName.getText().toString());
            userSave.put("Age", edtAge.getText().toString());
            userSave.put("Gender", edtGender.getText().toString());
            userSave.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SingUp.this,
                                userSave.get("FirstName") + " saved successfully",
                                FancyToast.LENGTH_LONG,
                                FancyToast.SUCCESS, true).show();
                    } else {
                        FancyToast.makeText(SingUp.this,
                                e.getMessage(),
                                FancyToast.LENGTH_LONG,
                                FancyToast.ERROR, true).show();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            FancyToast.makeText(SingUp.this,
                    e.getMessage(),
                    FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, true).show();
        }
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
