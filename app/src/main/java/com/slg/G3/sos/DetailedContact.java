package com.slg.G3.sos;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.slg.G3.sos.models.Contact;
import com.slg.G3.sos.models.User;

public class DetailedContact extends AppCompatActivity {

    TextView tvNameContact, tvRelasyon, tvAdres;
    ImageView ivPseudoProfile;
    ImageButton btnEfase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_detailed);

        //Find views
        tvNameContact = findViewById(R.id.tvNameContact);
        tvRelasyon = findViewById(R.id.tvRelasyon);
        tvAdres = findViewById(R.id.tvAdres);
        ivPseudoProfile = findViewById(R.id.ivPseudoProfile);
        btnEfase = findViewById(R.id.btnEfase);


        Contact contact = new Contact();


      tvNameContact.setText(contact.getName());














    }
}
