package com.slg.G3.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.slg.G3.sos.fragments.ContactsFragment;
import com.slg.G3.sos.fragments.ProfileFragment;
import com.slg.G3.sos.fragments.SosFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION =0 ;


    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Begin the transaction
                    Fragment fragment = new Fragment();
                    switch (item.getItemId()) {
                        //default:
                        case R.id.action_home:
                            // Select SOS Fragment
                            //Toast.makeText(MainActivity.this, "SOS", Toast.LENGTH_SHORT).show();
                            fragment = new SosFragment();
                            break;

                        case R.id.action_contacts:
                            // Select Contacts Fragment
                            //Toast.makeText(MainActivity.this, "Kontak ijans", Toast.LENGTH_SHORT).show();
                            fragment = new ContactsFragment();
                            break;

                        case R.id.action_profile:
                            //Toast.makeText(MainActivity.this, "Pwofil mwen", Toast.LENGTH_SHORT).show();
                            fragment = new ProfileFragment();
                            break;
                    }
                    fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                    return true;
                }

        });
        // Set default Selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }


}