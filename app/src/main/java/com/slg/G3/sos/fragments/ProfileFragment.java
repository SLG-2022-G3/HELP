package com.slg.G3.sos.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.slg.G3.sos.EditProfile;
import com.slg.G3.sos.LoginActivity;
import com.slg.G3.sos.R;
import com.slg.G3.sos.models.User;
//import com.slg.G3.sos.models.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Best practice is to name TAG after the current class it's set to
    public static final String TAG ="ProfileFragment";

    private ParseUser currentUser;
    private ImageView ivProfPic, btnLogout;
    private TextView tvName, tvPhone, tvEmail, tvNewMessage, tvBloodType, tvPerso, tvMsgSOS;
    private Button btnEdit, btnSaveMessage;
    private RelativeLayout rl, btnEMsg,btnShare, btnSettings ;
    String description, sosmessage, number, sharedMessage;
    public SharedPreferences prefs;






    Context context;

    User user = new User();

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // mParam1 = getArguments().getString(ARG_PARAM1);
            // mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Find views by Id

        ivProfPic = view.findViewById(R.id.ivProfilePic);
        tvName = view.findViewById(R.id.tvName);
        tvPhone = view.findViewById(R.id.tvNumber);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnSaveMessage = view.findViewById(R.id.btnSaveMessage);
        EditText tvDescription = view.findViewById(R.id.etInfo);
        tvBloodType = view.findViewById(R.id.tvBloodType);
        tvPerso = view.findViewById(R.id.tvPerso);
        tvMsgSOS = view.findViewById(R.id.tvMsgSOS);
        btnLogout = view.findViewById(R.id.btnLogout);


        ivProfPic = view.findViewById(R.id.ivProfilePic);

        ParseUser currentUser = ParseUser.getCurrentUser();
            try {
                ParseFile userParseFile = (ParseFile) currentUser.getParseFile("profilePicture");
                Bitmap bmp = BitmapFactory.decodeStream(userParseFile.getDataStream());
                bmp.setDensity(Bitmap.DENSITY_NONE);
                //bmp = Bitmap.createBitmap(bmp, 0, 0, 100, 100);
                ivProfPic.setImageBitmap(bmp);
            } catch (Exception e){
                e.printStackTrace();
            }



        // Set user info

        tvName.setText(ParseUser.getCurrentUser().getUsername());
        tvEmail.setText(ParseUser.getCurrentUser().getEmail());

//        UserInfo userInfo = new UserInfo();
//        tvPhone.setText(userInfo.getTelephone());
//        tvBloodType.setText(userInfo.getBloodType());
//        tvMsgSOS.setText(userInfo.getSOS());



        ParseUser user = ParseUser.getCurrentUser();
//        description = user.get("persoInfo").toString();
//        tvDescription.setText(description);


        //queryUser();
        //tvMsg.setText(userInfo.getMessage());


        // method to edit info
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goEditProfileActivity();
            }
        });


      // Method to log out user

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });


        // Method to save predefined message in shared preferences

        // instantiate shared preferences
        prefs = getContext().getSharedPreferences("Message", Context.MODE_PRIVATE);


        //Save the message when btn is pressed

        btnSaveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedMessage = tvDescription.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("predefMessage", sharedMessage);
                editor.apply();

                Toast.makeText(getContext(), "Nouvo mesaj SOS ou a byen anrejistre.", Toast.LENGTH_SHORT).show();


            }
        });

    }


    // Retrieve the predefined message when the user comes back to the profile fragment
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);


        prefs = getContext().getSharedPreferences("Message",Context.MODE_PRIVATE);
        String newMessage = prefs.getString("predefMessage", null);
        tvMsgSOS.setText(newMessage);


    }


    private void goEditProfileActivity() {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
    }

    private void queryUser() {

            ParseQuery<ParseUser> query = ParseQuery.getQuery("User");

            // The query will search for a ParseObject, given its objectId.
            // When the query finishes running, it will invoke the GetCallback
            // with either the object, or the exception thrown
            query.getInBackground("<PARSE_OBJECT_ID>", (object, e) -> {
                if (e == null) {
                    //Object was successfully retrieved
                } else {
                    // something went wrong
                    //Toast.makeText(getContext(), user.getInfo(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}