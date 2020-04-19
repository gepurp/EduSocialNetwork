package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SingUp extends AppCompatActivity implements View.OnClickListener {

    // UI Components
    private TextView edtEnterEmail, edtEnterUserName, edtEnterPassword;
    private Button btnSingUp, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Title for name of current activity
        setTitle("Sing Up");

        // Define the UI components
        edtEnterEmail = findViewById(R.id.edtEnterEmail);
        edtEnterUserName = findViewById(R.id.edtEnterUserName);
        edtEnterPassword = findViewById(R.id.edtEnterPassword);

        btnSingUp = findViewById(R.id.btnSingUp);
        btnLogin = findViewById(R.id.btnLogin);

        // Setting the onClick listener on buttons
        btnSingUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // Log out the current user ???
        // Have to read more about token session !!!
        if (ParseUser.getCurrentUser() != null) {
            ParseUser.getCurrentUser().logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSingUp:

                // Set the user credentials after sing up button clicked
                final ParseUser appUser = new ParseUser();
                appUser.setEmail(edtEnterEmail.getText().toString());
                appUser.setUsername(edtEnterUserName.getText().toString());
                appUser.setPassword(edtEnterPassword.getText().toString());

                // Adding the progress dialog for indicating the sing up process to the user
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Singing up the " + edtEnterUserName.getText().toString());
                progressDialog.show(); // Have to dismiss progress dialog after sign up

                // Signing up the user in new thread with callback message
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Making the success message if the exception didn't throw
                            FancyToast.makeText(SingUp.this,
                                    appUser.getUsername() + "is signed up",
                                    Toast.LENGTH_LONG,
                                    FancyToast.SUCCESS,
                                    true).show();
                        } else {
                            // Making the error message if the exception was thrown
                            FancyToast.makeText(SingUp.this,
                                    "There was an error: " + e.getMessage(),
                                    Toast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    true).show();
                        }

                        // Dismissing the progress dialog after sing up process
                        progressDialog.dismiss();
                    }
                });
                break;

            case R.id.btnLogin:

                // Switch to the login activity after login button clicked
                Intent intent = new Intent(SingUp.this, LoginActivity.class);
                startActivity(intent);

                break;

        }
    }
}
