package com.slg.G3.sos.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.slg.G3.sos.MainActivity;
import com.slg.G3.sos.R;
import com.slg.G3.sos.models.Contact;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "SOSFragment";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    // TODO: Rename and change types of parameters
    private GifImageView btnSOS;
    private List<Contact> contacts;
    String phoneNo;
    String message;
    FusedLocationProviderClient locationProviderClient;
    TextView tvLatitude, tvLongitude;

    public SosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SosFragment newInstance(String param1, String param2) {
        SosFragment fragment = new SosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sos, container, false);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        btnSOS = view.findViewById(R.id.btnSOS);


        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Check Condition to access Location
                if (ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .ACCESS_FINE_LOCATION)
                        != PackageManager
                        .PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .ACCESS_COARSE_LOCATION)
                        != PackageManager
                        .PERMISSION_GRANTED ){
                    // When permission is granted
                    // Call method
                    getCurrentLocation();

                } else {
                    // When permission is not granted
                    // Call method
                    requestPermissions(
                            new String[]{
                                    Manifest.permission
                                            .ACCESS_FINE_LOCATION,
                                    Manifest.permission
                                            .ACCESS_COARSE_LOCATION},
                            100);
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            // When permission are granted
            // Call  method
            getCurrentLocation();
        } else {
            // When permission are denied
            // Display toast
            Toast.makeText(getActivity(),
                            "Permission to access device location denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        // Initialize Location manager
        LocationManager locationManager = (LocationManager)getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        // Check condition
        if ((locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER))) {

                // When location service is enabled
                // Get last location
            locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Init location
                    Location location = task.getResult();
                    //Check Condition
                    if (location != null){
                        // When location result is not null set latitude
                        tvLatitude.setText(
                                String.valueOf(
                                        location
                                                .getLatitude()));
                        // set longitude
                        tvLongitude.setText(
                                String.valueOf(
                                        location
                                                .getLongitude()));
                    } else {
                    // When location result is null init location request
                        LocationRequest locationRequest
                                = new LocationRequest()
                                .setPriority(
                                        LocationRequest
                                                .PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(
                                        1000)
                                .setNumUpdates(1);

                        // Initialize location call back
                        LocationCallback
                                locationCallback
                                = new LocationCallback() {
                            @Override
                            public void
                            onLocationResult(
                                    LocationResult
                                            locationResult)
                            {
                                // Initialize
                                // location
                                Location location1
                                        = locationResult
                                        .getLastLocation();
                                // Set latitude
                                tvLatitude.setText(
                                        String.valueOf(
                                                location1
                                                        .getLatitude()));
                                // Set longitude
                                tvLongitude.setText(
                                        String.valueOf(
                                                location1
                                                        .getLongitude()));
                            }
                        };
                        // Request location updates
                        locationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper());

                    }
                }
            });
        }
        else {
            // When location service is not enable open location setting
            startActivity(
                    new Intent(
                            Settings
                                    .ACTION_LOCATION_SOURCE_SETTINGS)
                            .setFlags(
                                    Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }

    protected void sendSOS() {
        //TODO: Code that retrieve the Predefined SOSMessage, and Emergency Contacts' Number

        //Check Permissions

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }


    /* private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }

    }*/
}
