package com.slg.G3.sos;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
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

public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignupActivity";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private Button btnSend;
    TextView before;
    ImageView facebook, google;
    boolean passwordVisible2;





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

        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>= etPassword.getRight()-etPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = etPassword.getSelectionEnd();
                        if (passwordVisible2){
                            // set drawable image here
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off,0);
                            // to hide the password
                            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible2 = false;
                        }else {
                            // set drawable image here
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_,0);
                            // to show the password
                            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible2 = true;
                        }
                        etPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        etConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right=2;
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (event.getRawX()>= etConfirmPassword.getRight()-etConfirmPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = etConfirmPassword.getSelectionEnd();
                        if (passwordVisible2){
                            // set drawable image here
                            etConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off,0);
                            // to hide the password
                            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible2 = false;
                        }else {
                            // set drawable image here
                            etConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_,0);
                            // to show the password
                            etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible2 = true;
                        }
                        etConfirmPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });


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

            }
        });
    }

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
        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}