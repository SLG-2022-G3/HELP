package com.slg.G3.sos.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.slg.G3.sos.R;
//import com.slg.G3.sos.adapters.ContactAdapter;
import com.slg.G3.sos.adapters.ContactAdapter;
import com.slg.G3.sos.models.Contact;
//import com.slg.G3.sos.adapters.ContactAdapter;


import java.text.ParseException;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;




public class ContactsFragment extends Fragment {

    public static final String TAG ="Contacts Fragment";
    private RecyclerView rvContacts;
    private RecyclerView rvEmerServContacts;
    private GifImageView btnSOS;
    private RelativeLayout btnAddContact;
    protected List<Contact> contacts;
    protected ContactAdapter contactAdapter;



    // TODO: Rename and change types of parameters



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

        //Create ContactsAdapter
        contactAdapter = new ContactAdapter(this, contacts);

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
                Toast.makeText(getContext(), "Opsyon sa pako disponib", Toast.LENGTH_SHORT).show();
            }
        });
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Opsyon sa pako disponib", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void queryContacts() {
        // Specify which class to query



    }
}