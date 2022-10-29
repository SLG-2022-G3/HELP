package com.slg.G3.sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CreateContactActivity extends AppCompatActivity {

    public static String TAG = "CreateContactActivity";

    // in order to access gallery, and save image
    public static final int IMAGE_PICK_CODE = 1;
    public static final int PERMISSION_CODE = 2;



    private TextView textView;
    private EditText contactName, contactPhone, etRelation, etAddress;
    private Button btnAdd;
    private Button btnCancel;
    DbHelper dbHelper;
    List<ContactModel> contactModelList;

//    private ImageView ivContactPhoto;
//    private File profilePhoto ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        textView = findViewById(R.id.tvAddContact);
        contactName = findViewById(R.id.etContactName);
        contactPhone = findViewById(R.id.etContactNumber);
              btnAdd = findViewById(R.id.btnContactSend);
        btnCancel = findViewById(R.id.btnCancel);

        dbHelper = new DbHelper(this);

//        ivContactPhoto = findViewById(R.id.ivContactPhoto);


        // logic to add a profile photo to iv contact photo

//        ivContactPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(CreateContactActivity.this, "Opsyon sa poko ajoute", Toast.LENGTH_SHORT).show();
//
//                // checking runtime permission
//                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//
//                // Permission is not granted. Requesting it
//
//                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                // here we show the pop up for runtime permission
//                    requestPermissions(permissions, PERMISSION_CODE);
//                }
//                else {
//
//                    // permission is granted already
//                    PickImageFromGallery();
//
//                }
//
//
//            }
//        });

        // instantiate shared preferences
//        sp = getSharedPreferences("MyContacts", Context.MODE_PRIVATE);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                saveToArray();





//                sharedName = contactName.getText().toString();
//                sharedPhone = contactPhone.getText().toString();
////                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("contactName", sharedName);
//                editor.putString("contactPhone", sharedPhone);
//                editor.apply();




                String name = contactName.getText().toString();
                String phone = contactPhone.getText().toString();


                //save to database
                dbHelper.addcontact(name, phone);
                Toast.makeText(CreateContactActivity.this, "Kontak la anrejistre.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
                startActivity(intent);
                finish();











//                ParseUser currentUser = ParseUser.getCurrentUser();
//
//                if(name.isEmpty()) {
//                    Toast.makeText(CreateContactActivity.this, "Tanpri ajoute yon non pou kontak la!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//                if(phone.isEmpty()) {
//                    Toast.makeText(CreateContactActivity.this, "Tanpri ajoute yon nimewo pou kontak la!", Toast.LENGTH_LONG).show();
//                    return;
//                }
//
//
//                saveContact(name, phone, relationship, address, currentUser);



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



//    private void saveToArray() {
//        contactList.add(contactPhone.getText().toString());
//
//        TinyDB tinydb = new TinyDB(this);
//        tinydb.putListString("Contacts", contactList);
//
//
//    }


    // handling result of picked image

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//
//            //set image to profile
//            ivContactPhoto.setImageURI(data.getData());
//        }
//    }

//    // handling result of runtime permission
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_CODE: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // permission is granted
//                    PickImageFromGallery();
//                }
//                else {
//                    // permission was denied
//                    Toast.makeText(this, "Ou pa bay pemisyon an...", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//        }
//
//    }

//// method to pick photo
//    private void PickImageFromGallery() {
//        // intent to pick image from gallery
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, IMAGE_PICK_CODE);
//    }



//    //method to save contact to parse
//    private void saveContact(String name, String phone, String relationship, String address, ParseUser currentUser) {
//        Contact contact = new Contact();
//        contact.setName(name);
//        contact.setNumber(phone);
//        contact.setUser(currentUser);
//        contact.setAddress(address);
//        contact.setRelationship(relationship);
//        contact.pinInBackground();
//
//
//        contact.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    Log.e (TAG, "error while saving");
//                    Toast.makeText(CreateContactActivity.this, "Kontak la pa anrejistre", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(CreateContactActivity.this, "Kontak la anrejistre", Toast.LENGTH_SHORT).show();
//            }
//        });



//        Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
//        startActivity(intent);
//        finish();

    }




