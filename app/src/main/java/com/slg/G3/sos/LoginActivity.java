package com.slg.G3.sos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

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

import com.google.android.material.tabs.TabLayout;
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
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView facebook, google;
    TextView forgetPassword,next;


    float v=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_login);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        facebook = findViewById(R.id.fabButtonfb);
        google = findViewById(R.id.fabButtonGoogle);
        next = findViewById(R.id.next);

        etUsername = findViewById(R.id.usernameLogin);
        etPassword = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        forgetPassword = findViewById(R.id.forgetPassword);


        facebook.setTranslationY(300);
        google.setTranslationY(300);


        facebook.setAlpha(v);
        google.setAlpha(v);


        facebook.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "fiti implemantasyon", Toast.LENGTH_SHORT).show();
            }
        });

        // here we want to go to sign activity when button is pressed
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "opsyon sa poko valid", Toast.LENGTH_SHORT).show();
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "opsyon sa poko valid", Toast.LENGTH_SHORT).show();
            }
        });

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
                Toast.makeText(LoginActivity.this, "ou konekte ak siksè !", Toast.LENGTH_SHORT).show();
                goMainActivity();


            }

        });

    }

    private void goMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}