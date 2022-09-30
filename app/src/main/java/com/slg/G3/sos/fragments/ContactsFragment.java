package com.slg.G3.sos.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slg.G3.sos.CreateContactActivity;
import com.slg.G3.sos.MainActivity;
import com.slg.G3.sos.R;
//import com.slg.G3.sos.adapters.ContactAdapter;
import com.slg.G3.sos.adapters.ContactAdapter;
import com.slg.G3.sos.models.Contact;
//import com.slg.G3.sos.adapters.ContactAdapter;


import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;




public class ContactsFragment extends Fragment {

    public static final String TAG ="ContactsFragment";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    private RecyclerView rvContacts;
    private RecyclerView rvEmerServContacts;
    private GifImageView btnSOS;
    private RelativeLayout btnAddContact;
    private RelativeLayout relativeLayout;
    protected List<Contact> allcontact;
    protected ContactAdapter contactAdapter;
    private ImageButton btnDelete;



    // TODO: Rename and change types of parameters
    String phoneNo = "40770750";
    String message;
    FusedLocationProviderClient locationProviderClient;



    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvContacts = view.findViewById(R.id.rvContacts);

        //Create Data Source
        allcontact = new ArrayList<>();

        //Create ContactsAdapter
        contactAdapter = new ContactAdapter(this, allcontact);

        //Set adapter on recyclerview
        rvContacts.setAdapter(contactAdapter);

        //Set a Layout Manager
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));



        queryContacts();


  /*      rvEmerServContacts = view.findViewById(R.id.rvEmergServContacts);


        //Create EmergencyServicesAdapter
        //Set adapter on recyclerview
        //Set a Layout Manager*/

        btnAddContact = view.findViewById(R.id.btnAddContacts);
        btnSOS = view.findViewById(R.id.btnSOS);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateContactActivity.class);
                startActivity(intent);
            }
        });
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
                            String sos = "I NEED HELP join me at : \n" + "http://maps.google.com/?q=" + location.getLatitude()  + ","+ location.getLongitude();
                            //String phoneNo = "40770750";
                            //TODO: retrieve Emergency Contact PhoneNumber to String
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();
                                //Send the SOS
                                smsManager.sendTextMessage(phoneNo, null, sos, null, null);
                                Toast.makeText(getContext(), "Sending SOS", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "SOS Can not be Sent, access denied", Toast.LENGTH_SHORT).show();
                                requestPermission();
                            }

                        }else {
                            String sosMessage = "I need HELP. \n" + "GPS off, No Location provided ";
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();
                                //Send the SOS
                                smsManager.sendTextMessage(phoneNo, null, sosMessage, null, null);
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
                        String sosMessage = "I NEED HELP join me at : \n" + "GPS off, No Location Provided" ;
                        //String phoneNo = "40770750";
                        //TODO: retrieve Emergency Contact PhoneNumber to String
                        if (checkPermission()) {
                            //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();
                            //Send the SOS
                            smsManager.sendTextMessage(phoneNo, null, sosMessage, null, null);
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


    private void queryContacts() {
        // Specify which class to query
        ParseQuery<Contact> query = ParseQuery.getQuery("Contact");
        query.fromLocalDatastore();
        query.setLimit(5);
        query.whereEqualTo(Contact.KEY_USER, ParseUser.getCurrentUser());

        //Specify the object ID
        query.findInBackground(new FindCallback<Contact>() {
            @Override
            public void done(List<Contact> contacts, ParseException e) {
                if (e !=null){
                    Log.e(TAG, "Issues with getting Contacts", e);
                    return;
                } for(Contact contact : contacts){
                    Log.i(TAG, "Contact's name : "+ contact.getName() + "Phone Number : "+ contact.getNumber() + "photo: " + contact.getImage());
                    contact.saveInBackground();
                }

                // contacts.clear();
                allcontact.addAll(contacts);
                contactAdapter.notifyDataSetChanged();
            }
        });



    }

}