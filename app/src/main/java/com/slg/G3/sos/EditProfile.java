package com.slg.G3.sos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.ParseUser;

public class EditProfile extends AppCompatActivity {
    public final String TAG = "EditProfile";
    Toolbar toolbar;
    EditText etTelephone, etEmail, etInfo, etMsg;
    Button btnSaveProfile;
    ImageButton btnCam;
    ImageView ivCam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
//        toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //Find views by ID
        etTelephone = findViewById(R.id.etTelephone);
        etEmail = findViewById(R.id.etEmail);
        etInfo = findViewById(R.id.etInfo);
        etMsg = findViewById(R.id.etMsg);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnCam = findViewById(R.id.btnCam);
        ivCam = findViewById(R.id.ivCam);



        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String telephone = etTelephone.getText().toString();
                String bloodtype = etInfo.getText().toString();
                String sos = etMsg.getText().toString();

                ParseUser currentUser = ParseUser.getCurrentUser();

//                saveProfile(telephone, bloodtype, sos, currentUser);




            }
        });
    }


    //Method to save edited profile

//    private void saveProfile(String telephone, String bloodtype, String sos, ParseUser currentUser) {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setTelephone(telephone);
//        userInfo.setBloodType(bloodtype);
//        userInfo.setSOS(sos);
//        userInfo.setUser(currentUser);
//        userInfo.pinInBackground();
//
//        userInfo.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e != null) {
//                    Log.e (TAG, "unable to update");
//                }
//            }
//        });
//
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu, this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
//        return true;
//    }



}