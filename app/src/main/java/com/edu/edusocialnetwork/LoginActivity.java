package com.edu.edusocialnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView edtEmailLogin, edtPasswordLogin;
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
            ParseUser.getCurrentUser().logOut();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnLoginActivity:

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
                        } else {
                            // Making the error message if the exception was thrown
                            FancyToast.makeText(LoginActivity.this,
                                    "There was an error: " + e.getMessage(),
                                    Toast.LENGTH_LONG,
                                    FancyToast.ERROR,
                                    true).show();
                        }
                    }
                });
                break;

            case R.id.btnSingUpLoginActivity:

                // Switch back to the sing up activity when sing up button clicked
                Intent intent = new Intent(LoginActivity.this, SingUp.class);
                startActivity(intent);

                break;
        }
    }
}
