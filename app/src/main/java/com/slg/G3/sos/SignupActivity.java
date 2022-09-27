package com.slg.G3.sos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;

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
    private EditText etConfirmPassword;
    private EditText etEmail;
    private Button btnSend;
    TextView before;
    ImageView facebook, google;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.emailSignup);
        etPassword = findViewById(R.id.Passwords);
        etConfirmPassword = findViewById(R.id.confirmPassword);
        btnSend = findViewById(R.id.buttonSignup);
        before = findViewById(R.id.before);
        facebook = findViewById(R.id.Buttonfb);
        google = findViewById(R.id.ButtonGgle);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "opsyon sa poko ajoute", Toast.LENGTH_SHORT).show();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "opsyon sa poko ajoute", Toast.LENGTH_SHORT).show();
            }
        });

        // here the user can go back to the loginActivity
        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));

            }
        });


        // user can sign up on button click
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Submit button pressed");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                String email = etEmail.getText().toString();

                SignUpUser(username,password,confirmPassword,email);

            }
        });
    }

    private void SignUpUser(String username, String password, String confirmPassword, String email) {

        Log.i(TAG, "attempt to Signup: " + username);
        //create parse user
        ParseUser user = new ParseUser();
        //core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setPassword(confirmPassword);
        user.setEmail(email);
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

}