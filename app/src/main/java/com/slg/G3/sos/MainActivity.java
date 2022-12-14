package com.slg.G3.sos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.slg.G3.sos.Sensors.ReactivateService;
import com.slg.G3.sos.Sensors.SensorService;
import com.slg.G3.sos.fragments.ContactsFragment;
import com.slg.G3.sos.fragments.ProfileFragment;
import com.slg.G3.sos.fragments.SosFragment;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_LOCATION =0 ;
    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;


    FusedLocationProviderClient locationProviderClient;
    int PERMISSION_ID = 44;

    final FragmentManager fragmentManager = getSupportFragmentManager();


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_READ_LOCATION
            );
        }



        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        getLastLocation();


       /* locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {

            @Override
            public boolean isCancellationRequested() {
                return false;
            }
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Location>() {
           /* @Override
            public void onSuccess(Location location) {
                if (location != null){
                    //Fetch Location to String

                } else {

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/

        //check for BatteryOptimization,
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
                askIgnoreOptimization();
            }
        }

        //start the service
        SensorService sensorService = new SensorService();
        Intent intent = new Intent(this, sensorService.getClass());
        if (!isMyServiceRunning(sensorService.getClass())) {
            startService(intent);
        }






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


    //method to check if the service is running
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    @Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, ReactivateService.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    //this method prompts the user to remove any battery optimisation constraints from the App
    private void askIgnoreOptimization() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST);
        }

    }

    public void getLastLocation() {
        // check if permissions are given
        if (checkPermission()) {
            //if permission enabled
            if (isLocationEnabled()){
                // getting last location from FusedLocationClient object
                locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            //tvLocation.setText("https://maps.google.com/?q="+location.getLatitude() +","+ location.getLongitude() + "/");
                        }
                    }
                });

            } else {
                Toast.makeText(this, "Tanpri aktive" + " lokalizasyon ou...", Toast.LENGTH_LONG).show();
                //TODO : Implement Dialog Fragment for approve get User's
                //Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //startActivity(intent);
            }
        } else {
            // if permissions aren't available, request for permissions
            requestPermissions();
        }

    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        //Setting request on locationProviderClient
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

    }
    public LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();
        }
    };

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission()){
            getLastLocation();
        }
    }
}