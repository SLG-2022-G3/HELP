package com.slg.G3.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    private TextView tvLogin;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

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
//TODO: Navigate to feed activity if the user is signed in properly
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
            }

    });


}
    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}