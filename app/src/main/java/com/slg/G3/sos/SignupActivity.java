package com.slg.G3.sos;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private Button btnSend;
    TextView before;
    ImageView facebook, google;
    Context context;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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
                Toast.makeText(SignupActivity.this, "ou poko ka anrejistre ak facebook, opsyon sa poko ajoute !", Toast.LENGTH_SHORT).show();
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "ou poko ka anrejistre ak google,opsyon sa poko ajoute !", Toast.LENGTH_SHORT).show();
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

                if (username.isEmpty() || username.length()<7)
                {
                    showError(etUsername, "non itilizatè ou an pa valid, li trò kout !");
                }
                else if (email.isEmpty() || !email.contains("@"))
                {
                    showError(etEmail, "imel ou an pa valid !");
                }
                else if (password.isEmpty() || password.length()<8)
                {
                    showError(etPassword, "modpas lan dwe gen pou pi piti 8 karaktè");
                }
                else if (confirmPassword.isEmpty() || !confirmPassword.equals(password))
                {
                    showError(etConfirmPassword, "modpas yo pa menm !");
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "call registration method", Toast.LENGTH_SHORT).show();
                }

                SignUpUser(username,password,confirmPassword,email);
//                deleteAlldataBase();

            }
        });
    }

//    private void deleteAlldataBase() {
//        this.deleteDatabase("/data/data/com.slg.G3.sos/databases/MyContacts2.db");
//
//    }


    private void showError(EditText user, String s) {
        user.setError(s);
        user.requestFocus();
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
                    Toast.makeText(SignupActivity.this, "Yay! ou anrejistre ak siksè!", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(SignupActivity.this, Welcome.class);
        startActivity(intent);
        finish();
    }

}