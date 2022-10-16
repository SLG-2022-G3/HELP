package com.slg.G3.sos.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.telephony.gsm.SmsManager;
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
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
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


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


        // shared preferences to retrieve the number of the last
        //contact the user saved and their name as well

        SharedPreferences sp = getContext().getSharedPreferences("MyContacts", Context.MODE_PRIVATE);
        String nameContact = sp.getString("contactName", "");
        String phoneContact = sp.getString("contactPhone", "");


        // shared preferences to retrieve the predefined sos message from profile fragment
        SharedPreferences prefs = getContext().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String sosPredefined = prefs.getString("predefMessage", "");





        btnSOS = view.findViewById(R.id.btnSOS);


        btnSOS.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                FusedLocationProviderClient locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

                locationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
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
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            // Code to Send SOS MESSAGE
                            //TODO: retrieve predefined SOS Message to String
                            String sos = nameContact + sosPredefined + "http://maps.google.com/?q=" + location.getLatitude()  + ","+ location.getLongitude();
                            //String phoneNo = "40770750";
                            //TODO: retrieve Emergency Contact PhoneNumber to String
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();
                                //Send the SOS
                                smsManager.sendTextMessage(phoneContact, null, sos, null, null);
                                Toast.makeText(getContext(), "Sending SOS", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "SOS Can not be Sent, access denied", Toast.LENGTH_SHORT).show();
                                requestPermission();
                            }

                        }else {
                            String sosMessage = "HEY " + nameContact + sosPredefined + "GPS off, No Location provided ";
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();
                                //Send the SOS
                                smsManager.sendTextMessage(phoneContact, null, sosMessage, null, null);
                                Toast.makeText(getContext(), "Sending SOS, without Location", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "SOS Can not be Sent, access denied", Toast.LENGTH_SHORT).show();
                                requestPermission();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Code to Send SOS MESSAGE
                        //TODO: retrieve predefined SOS Message to String
                        String sosMessage = "HEY " + nameContact + sosPredefined + "GPS off, No Location Provided" ;
                        //String phoneNo = "40770750";
                        //TODO: retrieve Emergency Contact PhoneNumber to String
                        if (checkPermission()) {
                            //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();
                            //Send the SOS
                            smsManager.sendTextMessage(phoneContact, null, sosMessage, null, null);
                            Toast.makeText(getContext(), "Sending SOS", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "SOS Can not be Sent, access denied", Toast.LENGTH_SHORT).show();
                            requestPermission();
                        }
                    }
                });



            }

        });
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission access allowed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission access denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
    }
    /*  @Override
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
           /* locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
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

  /*  protected void sendSOS() {
        //TODO: Code that retrieve the Predefined SOSMessage, and Emergency Contacts' Number

        //Check Permissions

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) getContext(), Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }


     private boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }

    */
}
