package com.slg.G3.sos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.slg.G3.sos.fragments.ContactsFragment;
import com.slg.G3.sos.models.Contact;

import java.io.File;

public class CreateContactActivity extends AppCompatActivity {

    public static String TAG = "CreateContactActivity";

    // in order to access gallery, and save image
    public static final int IMAGE_PICK_CODE = 1;
    public static final int PERMISSION_CODE = 2;



    private TextView textView;
    private EditText contactName;
    private EditText contactPhone;
    private Button btnAdd;
    private Button btnCancel;
    private ImageView ivContactPhoto;
    private File profilePhoto ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        textView = findViewById(R.id.tvAddContact);
        contactName = findViewById(R.id.etContactName);
        contactPhone = findViewById(R.id.etContactNumber);
        btnAdd = findViewById(R.id.btnContactSend);
        btnCancel = findViewById(R.id.btnCancel);
        ivContactPhoto = findViewById(R.id.ivContactPhoto);


        // logic to add a profile photo to iv contact photo

        ivContactPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateContactActivity.this, "Opsyon sa poko ajoute", Toast.LENGTH_SHORT).show();

                // checking runtime permission
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                // Permission is not granted. Requesting it

                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                // here we show the pop up for runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else {

                    // permission is granted already
                    PickImageFromGallery();

                }


            }
        });


        // code to add contact when button is pressed

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = contactName.getText().toString();
                String phone = contactPhone.getText().toString();
                ParseUser currentUser = ParseUser.getCurrentUser();



                if(name.isEmpty()) {
                    Toast.makeText(CreateContactActivity.this, "Tanpri ajoute yon non pou kontak la!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(phone.isEmpty()) {
                    Toast.makeText(CreateContactActivity.this, "Tanpri ajoute yon nimewo pou kontak la!", Toast.LENGTH_LONG).show();
                    return;
                }


                saveContact(name, phone, currentUser, profilePhoto);


            }
        });



        //code to cancel adding contact when button Anile is pressed

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
                Toast.makeText(CreateContactActivity.this, "Kontak la pa anrejistre.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });

    }


        // handling result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            //set image to profile
            ivContactPhoto.setImageURI(data.getData());
        }
    }

    // handling result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted
                    PickImageFromGallery();
                }
                else {
                    // permission was denied
                    Toast.makeText(this, "Ou pa bay pemisyon an...", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

// method to pick photo
    private void PickImageFromGallery() {
        // intent to pick image from gallery
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }



    //method to save contact to parse
    private void saveContact(String name, String phone, ParseUser currentUser, File profilePhoto) {
        Contact contact = new Contact();
        contact.setName(name);
       // contact.setImage(new ParseFile(profilePhoto));
        contact.setNumber(phone);
        contact.setUser(currentUser);
        contact.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e != null) {
                    Log.e(TAG, "error while saving");
                    Toast.makeText(CreateContactActivity.this, "Kontak la pa anrejistre. Tanpri eseye anko!", Toast.LENGTH_LONG).show();

                }
                Log.i(TAG, "Contact saved successfully");
                Toast.makeText(CreateContactActivity.this, "Kontak la anrejistre!", Toast.LENGTH_LONG).show();

                contactName.setText("");
                contactPhone.setText("");
                ivContactPhoto.setImageResource(0);


            }
        });

        Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
        startActivity(intent);

    }




}