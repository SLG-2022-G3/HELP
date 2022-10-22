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
import com.slg.G3.sos.Utils.TinyDB;
import com.slg.G3.sos.models.Contact;

import java.util.ArrayList;
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
    FusedLocationProviderClient locationProviderClient;
    TextView tvLatitude, tvLongitude;
    ArrayList<String> phoneContact;


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


       // shared preferences to retrieve the predefined sos message from profile fragment
        SharedPreferences prefs = getContext().getSharedPreferences("Message", Context.MODE_PRIVATE);
        String sosPredefined = prefs.getString("predefMessage", "");

       // using tinyDb class to retrieve list of strings in shared preferences
        TinyDB tinyDB = new TinyDB(getContext());
        phoneContact = tinyDB.getListString("Contacts");



        // click to send SOS

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
                            String sos = sosPredefined + "http://maps.google.com/?q=" + location.getLatitude()  + ","+ location.getLongitude();
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();
                                //Send the SOS
                                smsManager.sendTextMessage(String.valueOf(phoneContact), null, sos, null, null);
                                Toast.makeText(getContext(), "Sending SOS", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "SOS Can not be Sent, access denied", Toast.LENGTH_SHORT).show();
                                requestPermission();
                            }

                        }else {
                            String sosMessage = sosPredefined + "GPS off, No Location provided ";
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();
                                //Send the SOS
                                smsManager.sendTextMessage(String.valueOf(phoneContact), null, sosMessage, null, null);
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
                        String sosMessage = sosPredefined + "GPS off, No Location Provided" ;
                        if (checkPermission()) {
                            //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();
                            //Send the SOS
                            smsManager.sendTextMessage(String.valueOf(phoneContact), null, sosMessage, null, null);
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

}
