package com.slg.G3.sos.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseUser;
import com.slg.G3.sos.LoginActivity;
import com.slg.G3.sos.R;
import com.slg.G3.sos.models.User;

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
    public static final String TAG ="AccountFragment";

    private ParseUser currentUser;
    private ImageView ivProfPic, btnLogout;
    private TextView tvName, tvDescription, tvMsg;
    private RelativeLayout btnEMsg,btnShare, btnSettings ;



    Context context;

    User user;

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


        tvName = view.findViewById(R.id.tvName);
        tvDescription = view.findViewById(R.id.tvInfo);

        ivProfPic = view.findViewById(R.id.ivProfilePic);


        tvMsg = view.findViewById(R.id.tvMsg);

        btnLogout = view.findViewById(R.id.btnLogout);

        tvName.setText(ParseUser.getCurrentUser().getUsername());
//        tvDescription.setText(ParseUser.getCurrentUser().getEmail());
//        ParseFile parseFile = user.getImage();
//        if (parseFile != null) {
//            Glide.with(context).load(parseFile.getUrl()).into(ivProfPic);
//        }




        tvName.setText(ParseUser.getCurrentUser().getUsername());
        //tvDescription.setText(userInfo.getInfo());
        //tvMsg.setText(userInfo.getSos());



        //User can log out when Dekonekte is clicked


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}