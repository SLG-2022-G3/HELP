package com.slg.G3.sos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSend;
    private TextView btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSend = findViewById(R.id.btnSend);
        btnSignIn = findViewById(R.id.btnSignIn);


        //User can go back to login by clicking on KONEKTE TV
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Login link clicked");
                goLoginActivity();

            }
        });


        // user can sign up on button click
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Submit button pressed");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                SignUpUser(username,password);

            }
        });



    }

    private void SignUpUser(String username, String password) {

        Log.i(TAG, "attempt to Signup: " + username);

        //create parse user
        ParseUser user = new ParseUser();
        //core properties
        user.setUsername(username);
        user.setPassword(password);
        //sign up in background
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(SignupActivity.this, "Yay! You are successfully signed up.", Toast.LENGTH_SHORT).show();
                    goMainActivity();

                    // Hooray! Let them use the app now.
                } else {
                    Log.e(TAG, "issue with Signup", e);
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void goLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}