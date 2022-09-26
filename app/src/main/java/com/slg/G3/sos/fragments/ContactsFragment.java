package com.slg.G3.sos.fragments;

import android.content.Intent;
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
    private RecyclerView rvContacts;
    private RecyclerView rvEmerServContacts;
    private GifImageView btnSOS;
    private RelativeLayout btnAddContact;
    private RelativeLayout relativeLayout;
    protected List<Contact> allcontact;
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
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Opsyon sa pako disponib", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void queryContacts() {
        // Specify which class to query
        ParseQuery<Contact> query = ParseQuery.getQuery(Contact.class);
        query.orderByDescending("createdAt");
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
                    Log.i(TAG, "Contact's name : "+ contact.getName() + "Phone Number : "+ contact.getNumber());
                }

                // contacts.clear();
                allcontact.addAll(contacts);
                contactAdapter.notifyDataSetChanged();
            }
        });



    }

}