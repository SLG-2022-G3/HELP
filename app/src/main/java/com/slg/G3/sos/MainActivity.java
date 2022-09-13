package com.slg.G3.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        // Select SOS Fragment
                        Toast.makeText(MainActivity.this, "SOS", Toast.LENGTH_SHORT).show();
                        return true;
                    default:return true;
                    case R.id.action_contacts:
                        // Select Contacts Fragment
                        Toast.makeText(MainActivity.this, "Kontak ijans", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_profile:
                        // Select Profile Fragment
                        Toast.makeText(MainActivity.this, "Pwofil", Toast.LENGTH_SHORT).show();
                        return true;
                }

            }
        }


        );
    }
}