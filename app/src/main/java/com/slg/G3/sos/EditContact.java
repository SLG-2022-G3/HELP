package com.slg.G3.sos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {

    public static String TAG = "EditContact";


    private TextView textView;
    // creating variables for our edit text
    private EditText contactName, contactPhone;
    // creating variable for button
    private Button btnSave, btnCancel;
    // creating a strings for storing our values from edittext fields.
    private  String nameContact, numberContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        //init ET and BTNs
        contactName = findViewById(R.id.etContactName);
        contactPhone = findViewById(R.id.etContactNumber);
        btnSave = findViewById(R.id.btnContactSend);
        btnCancel = findViewById(R.id.btnCancel);

        // setting data to our edit text field.
        contactName.setText(getIntent().getStringExtra("contactName"));
        contactPhone.setText(getIntent().getStringExtra("contactNumber"));




        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditContact.this, MainActivity.class);
                Toast.makeText(EditContact.this, "Modifikasyon yo anile", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });
    }
}