package com.slg.G3.sos.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Looper;
import android.provider.Settings;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
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
import com.parse.ParseUser;
import com.slg.G3.sos.ContactModel;
import com.slg.G3.sos.CreateContactActivity;
import com.slg.G3.sos.DbHelper;
import com.slg.G3.sos.MainActivity;
import com.slg.G3.sos.R;
import com.slg.G3.sos.Utils.TinyDB;
import com.slg.G3.sos.models.Contact;
import com.slg.G3.sos.models.User;

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
    TextView tvGreeting, tvGreetingName, tvGreetings2;
    ImageView ivIcon, ivGreetingsProfile;
    Button btnKontak;
    public static String sosPredefinedNoLocation, sosPredefinedLocation;


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
        sosPredefinedLocation = "Mwen genyen ijans. Tanpri kontakte m rapid, konnya menm. Men lokalizasyon mwen: ";
        sosPredefinedNoLocation = "Mwen genyen ijans. Tanpri kontakte m rapid, konnya menm. VIT! VIT!";

        tvGreeting = view.findViewById(R.id.tvGreeting);
        tvGreetingName = view.findViewById(R.id.tvGreetingName);
        tvGreetings2 = view.findViewById(R.id.tvGreetings2);
        ivIcon = view.findViewById(R.id.ivIcon);
//        btnKontak = view.findViewById(R.id.btnKontak);
        ivGreetingsProfile = view.findViewById(R.id.ivGreetingsProfile);

      User user = new User();
      tvGreetingName.setText(ParseUser.getCurrentUser().getUsername());

        Glide.with(getContext())
                .load(user.getImage())
                .override(300, 200)
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .transform(new RoundedCorners(30))
                .into(ivGreetingsProfile);


        DbHelper dbHelper = new DbHelper(getContext());



//        btnKontak.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if(dbHelper.count() != 3) {
//                    Intent intent = new Intent(getContext(), CreateContactActivity.class);
//                    startActivity(intent);
//
//                }
//                else {
//                    Toast.makeText(getContext(), "Dezole, ou pa kapab ajoute plis pase 3 kontak.", Toast.LENGTH_SHORT).show();
//                }
//
//
//
//
//
//
//            }
//        });

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
                                Toast.makeText(getContext(), "SOS la ale.\nChwazi yon kontak sou Whatsapp", Toast.LENGTH_LONG).show();




                            } else {
                                Toast.makeText(getContext(), "SOS la pa ale.\nBay pemisyon voye SMS.", Toast.LENGTH_LONG).show();
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

                                Toast.makeText(getContext(), "SOS la ale san lokalizasyon ou. Aktive l.", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(getContext(), "SOS la pa ale.\nBay pemisyon voye SMS.", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getContext(), "SOS la ale.\nChwazi yon kontak sou Whatsapp", Toast.LENGTH_LONG).show();



                        } else {
                            Toast.makeText(getContext(), "SOS la pa ale.\nBay pemisyon voye SMS.", Toast.LENGTH_LONG).show();
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, sosPredefinedNoLocation );
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);


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
                    Toast.makeText(getContext(), "Mesi paske w bay pemisyon an.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ou pa bay pemisyon an.\n", Toast.LENGTH_SHORT).show();
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
