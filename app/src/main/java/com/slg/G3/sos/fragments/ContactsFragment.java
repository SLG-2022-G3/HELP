package com.slg.G3.sos.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.slg.G3.sos.ContactModel;
import com.slg.G3.sos.CreateContactActivity;
import com.slg.G3.sos.DbHelper;
import com.slg.G3.sos.R;
import com.slg.G3.sos.adapters.DatabaseAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;




public class ContactsFragment extends Fragment {

    public static final String TAG ="ContactsFragment";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    private List<ContactModel> contactModelList;
    private DbHelper dbHelper;
    private DatabaseAdapter databaseAdapter;
    public static String sosPredefinedNoLocation, sosPredefinedLocation;
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
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        sosPredefinedLocation = "Mwen genyen ijans. Tanpri kontakte m rapid konnya menm. Men lokalizasyon mwen: ";
        sosPredefinedNoLocation = "Mwen genyen ijans. Tanpri kontakte m rapid konnya menm. VIT! VIT!";


        dbHelper = new DbHelper(getContext());



        RecyclerView rvContacts = view.findViewById(R.id.rvContacts);

        //Create Data Source
        contactModelList = new ArrayList<>();
        contactModelList = dbHelper.getAllContacts();


        //Create ContactsAdapter

        databaseAdapter = new DatabaseAdapter(getContext(), (ArrayList<ContactModel>) contactModelList);


        //Set layout manager on recyclerview
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));


        //Set adapter
        rvContacts.setAdapter(databaseAdapter);



//        queryContacts();


  /*      rvEmerServContacts = view.findViewById(R.id.rvEmergServContacts);


        //Create EmergencyServicesAdapter
        //Set adapter on recyclerview
        //Set a Layout Manager*/

        RelativeLayout btnAddContact = view.findViewById(R.id.btnAddContacts);
        GifImageView btnSOS = view.findViewById(R.id.btnSOS);



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

                            sendWhatsapp();

                            //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();

                            // get the list of all the contacts in Database
                            DbHelper dbHelper = new DbHelper(getContext());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();


                            // Code to Send SOS MESSAGE
                            String sosMessage = sosPredefinedLocation + "http://maps.google.com/?q=" + location.getLatitude()  + ","+ location.getLongitude();
                            if (checkPermission()) {
                                Cursor cursor = db.rawQuery("select * from SOSContact", null);

                                while (cursor.moveToNext()) {
                                    String num = cursor.getString(2);
                                    //Send the SOS
                                    smsManager.sendTextMessage(num, null, sosMessage, null, null);

                                }

                                cursor.close();
                                Toast.makeText(getContext(), "SOS la ale. Tanpri pran swen ou annatandan.", Toast.LENGTH_SHORT).show();




                            } else {
                                Toast.makeText(getContext(), "SOS la pa ale.\n + Tanpri bay aplikasyon an pemisyon voye SMS.", Toast.LENGTH_SHORT).show();
                                requestPermission();
                            }

                        }else {
                            String sosMessage = sosPredefinedNoLocation;
                            if (checkPermission()) {
                                //Get the default SmsManager//
                                SmsManager smsManager = SmsManager.getDefault();


                                // get the list of all the contacts in Database
                                DbHelper dbHelper = new DbHelper(getContext());
                                List<ContactModel> list = dbHelper.getAllContacts();
                                SQLiteDatabase db = dbHelper.getWritableDatabase();


                                Cursor cursor = db.rawQuery("select * from SOSContact", null);

                                while (cursor.moveToNext()) {
                                    String num = cursor.getString(2);
                                    String name = cursor.getString(1);
                                    //Send the SOS
                                    smsManager.sendTextMessage("Alo " + name + ", " + num, null, sosMessage, null, null);
                                }
                                cursor.close();

                                Toast.makeText(getContext(), "SOS la ale san lokalizasyon ou. Aktive Lokalizasyon, tanpri.", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getContext(), "SOS la pa ale.\n + Tanpri bay aplikasyon an pemisyon voye SMS.", Toast.LENGTH_SHORT).show();
                                requestPermission();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Code to Send SOS MESSAGE
                        String sosMessage = sosPredefinedNoLocation;

                        if (checkPermission()) {

                            //Get the default SmsManager//
                            SmsManager smsManager = SmsManager.getDefault();
                            //Send the SOS
                            DbHelper dbHelper = new DbHelper(getContext());
                            List<ContactModel> list = dbHelper.getAllContacts();
                            SQLiteDatabase db = dbHelper.getWritableDatabase();



                            Cursor cursor = db.rawQuery("select * from SOSContact", null);
                            while (cursor.moveToNext()) {
                                String num = cursor.getString(2);
                                String name = cursor.getString(1);
                                //Send the SOS
                                smsManager.sendTextMessage("Alo " + name + ", " + num, null, sosMessage, null, null);

                            }
                            cursor.close();
                            Toast.makeText(getContext(), "SOS la ale. Tanpri pran swen ou annatandan.", Toast.LENGTH_SHORT).show();



                        } else {
                            Toast.makeText(getContext(), "SOS la pa ale.\n + Tanpri bay aplikasyon an pemisyon voye SMS.", Toast.LENGTH_SHORT).show();
                            requestPermission();
                        }
                    }
                });



            }

        });
    }



    private void sendWhatsapp() {

        Intent sendIntent = new Intent("android.intent.action.MAIN");
        sendIntent.putExtra("", "" + "@s.whatsapp.net");
        sendIntent.putExtra(Intent.EXTRA_TEXT, sosPredefinedNoLocation);
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);


    }


//    private boolean checkPermission() {
//        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS);
//        if (result == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getContext(), "Permission access allowed", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "Permission access denied", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    private void requestPermission(){
//        ActivityCompat.requestPermissions(getActivity(),
//                new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
//    }

//
//    private void queryContacts() {
//        // Specify which class to query
//        ParseQuery<Contact> query = ParseQuery.getQuery("Contact");
//        query.fromLocalDatastore();
//        query.orderByAscending("createdAt");
//        query.setLimit(1);
//        query.whereEqualTo(Contact.KEY_USER, ParseUser.getCurrentUser());
//
//        //Specify the object ID
//        query.findInBackground(new FindCallback<Contact>() {
//            @Override
//            public void done(List<Contact> contacts, ParseException e) {
//                if (e !=null){
//                    Log.e(TAG, "Issues with getting Contacts", e);
//                    return;
//                } for(Contact contact : contacts){
//                    Log.i(TAG, "Contact's name : "+ contact.getName() + "Phone Number : "+ contact.getNumber() + "photo: " + contact.getImage());
//                    contact.saveInBackground();
//                }
//
//                // contacts.clear();
//
//            }
//        });
//
//
//
//    }

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
                    Toast.makeText(getContext(), "Mesi paske w bay pemisyon an.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ou pa bay pemisyon an.\n + Tanpri bay aplikasyon an pemisyon an.", Toast.LENGTH_SHORT).show();
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