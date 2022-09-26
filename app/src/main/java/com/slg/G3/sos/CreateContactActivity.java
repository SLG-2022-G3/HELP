package com.slg.G3.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.slg.G3.sos.fragments.ContactsFragment;
import com.slg.G3.sos.models.Contact;

public class CreateContactActivity extends AppCompatActivity {

    public static String TAG = "CreateContactActivity";

    private TextView textView;
    private EditText contactName;
    private EditText contactPhone;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        textView = findViewById(R.id.tvAddContact);
        contactName = findViewById(R.id.etContactName);
        contactPhone = findViewById(R.id.etContactNumber);
        btnAdd = findViewById(R.id.btnContactSend);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String name = contactName.getText().toString();
                String phone = contactPhone.getText().toString();

                saveContact(name, phone);


            }
        });

    }

    //method to save contact to parse
    private void saveContact(String name, String phone) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setNumber(phone);
        contact.setUser(ParseUser.getCurrentUser());
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


            }
        });

        Intent intent = new Intent(CreateContactActivity.this, MainActivity.class);
        startActivity(intent);

    }


}