package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
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
        /*
        Maybe I should add the onKeyListener for edtEnterPassword... or maybe not...
        Then user can sing up by clicking 'return' key on the phone keyboard.
         */

        btnSingUp = findViewById(R.id.btnSingUp);
        btnLogin = findViewById(R.id.btnLogin);

        // Setting the onClick listener on buttons
        btnSingUp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        // Log out the current user ???
        // Have to read more about token session !!!
        if (ParseUser.getCurrentUser() != null) {
            //ParseUser.getCurrentUser().logOut();
            switchToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnSingUp:
                // Condition for checking the values of credentials
                // Showing message to a user if some line has empty value
                if (edtEnterEmail.getText().toString().equals("") ||
                        edtEnterUserName.getText().toString().equals("") ||
                        edtEnterPassword.getText().toString().equals("")) {

                    FancyToast.makeText(SingUp.this,
                            "Empty values are not allowed",
                            Toast.LENGTH_LONG,
                            FancyToast.INFO,
                            true).show();
                } else {

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
                                        appUser.getUsername() + " is signed up",
                                        Toast.LENGTH_LONG,
                                        FancyToast.SUCCESS,
                                        true).show();
                                // Calling the method for switching on social media activity
                                switchToSocialMediaActivity();
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
                }
                break;

            case R.id.btnLogin:

                // Switch to the login activity after login button clicked
                Intent intent = new Intent(SingUp.this, LoginActivity.class);
                startActivity(intent);

                break;

        }
    }

    // When user tapped on the empty space keyboard will be hide
    // Need to use InputMethodManager for it... I have no idea how to do it... yet...
    public void singUpRootLayoutTapped(View view) {
        // Something here works not really fine... But I don't know what exactly... yet...
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method for switching on social media activity after user singed up
    private void switchToSocialMediaActivity() {
        Intent intent = new Intent(SingUp.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
