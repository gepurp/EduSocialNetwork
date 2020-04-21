package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLoginActivity, btnSingUpLoginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Title for name of current activity
        setTitle("Log In");

        // Define the UI components
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);

        btnLoginActivity = findViewById(R.id.btnLoginActivity);
        btnSingUpLoginActivity = findViewById(R.id.btnSingUpLoginActivity);

        // Setting the onClick listener on buttons
        btnLoginActivity.setOnClickListener(this);
        btnSingUpLoginActivity.setOnClickListener(this);

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

            case R.id.btnLoginActivity:
                // Condition for checking the values of credentials
                // Showing message to a user if some line has empty value
                if (edtEmailLogin.getText().toString().equals("") ||
                        edtPasswordLogin.getText().toString().equals("")) {

                    FancyToast.makeText(LoginActivity.this,
                            "Empty values are not allowed",
                            Toast.LENGTH_LONG,
                            FancyToast.INFO,
                            true).show();
                } else {

                    // Adding the progress dialog for indicating the log in process to the user
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging in the " + edtEmailLogin.getText().toString());
                    progressDialog.show();

                    // Logging in the user in new thread with callback message
                    ParseUser.logInInBackground(edtEmailLogin.getText().toString(),
                            edtPasswordLogin.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        // Making the success message if the exception didn't throw
                                        FancyToast.makeText(LoginActivity.this,
                                                user.getUsername() + " logged in successfully",
                                                Toast.LENGTH_LONG, FancyToast.SUCCESS,
                                                true).show();
                                        // Calling the method for switching on social media activity
                                        switchToSocialMediaActivity();
                                    } else {
                                        // Making the error message if the exception was thrown
                                        FancyToast.makeText(LoginActivity.this,
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

            case R.id.btnSingUpLoginActivity:

                // Switch back to the sing up activity when sing up button clicked
                Intent intent = new Intent(LoginActivity.this, SingUp.class);
                startActivity(intent);

                break;
        }
    }

    // When user tapped on the empty space keyboard will be hide
    public void loginRootLayoutTapped(View view) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method for switching on social media activity after user logged in
    private void switchToSocialMediaActivity() {
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
