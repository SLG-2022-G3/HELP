package com.slg.G3.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    private TextView tvLogin;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // user logs in when the button is pressed
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Login button pressed");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);

            }
        });

        // user can sign up on button click
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Signup button pressed");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                SignUpUser(username,password);

            }
        });

    }



    private void loginUser(String username, String password) {
        Log.i(TAG, "attempt to login: " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "issue with login", e);
                    return;
                }
                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(LoginActivity.this, "Yay! You are sucessfully signed up.", Toast.LENGTH_SHORT).show();


            }
        });

    }


}